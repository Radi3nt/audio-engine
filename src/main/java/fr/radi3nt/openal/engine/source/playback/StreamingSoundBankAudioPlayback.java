package fr.radi3nt.openal.engine.source.playback;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.high.bank.streaming.StreamingSoundBank;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;
import org.lwjgl.BufferUtils;

import java.nio.ShortBuffer;
import java.util.Arrays;

public class StreamingSoundBankAudioPlayback implements AudioPlayback {

    private static final int MAX_CHANNELS = 16;

    private final StreamingSoundBank soundBank;
    private final ShortBuffer pcm;
    private final int streamingSampleLength;
    private final int bufferAmount;

    private AlSoundSource source;
    private SoundClip soundClip;

    private AlSoundBuffer[] initialBuffers;

    private boolean playing = false;
    private boolean reachedEnd = false;
    private boolean looping = false;

    public StreamingSoundBankAudioPlayback(StreamingSoundBank soundBank, int streamingSampleLength, int bufferAmount) {
        this.soundBank = soundBank;
        this.streamingSampleLength = streamingSampleLength;
        this.bufferAmount = bufferAmount;
        pcm = BufferUtils.createShortBuffer(MAX_CHANNELS * streamingSampleLength);
    }

    @Override
    public void set(SoundClip soundClip, AlSoundSource source) {
        if (this.source != null)
            stop();
        this.source = source;
        this.soundClip = soundClip;

        if (initialBuffers == null) {
            initialBuffers = new AlSoundBuffer[bufferAmount];
            for (int i = 0; i < initialBuffers.length; i++) {
                initialBuffers[i] = new AlSoundBuffer();
            }
        }

        looping = false;
        source.setLooping(false);
    }

    @Override
    public PercentModifier getGainMultiplier() {
        return SetPercentModifier.NO_MODIFIER;
    }

    @Override
    public PercentModifier getPitchMultiplier() {
        return SetPercentModifier.NO_MODIFIER;
    }

    @Override
    public void play() {
        playing = true;
        reachedEnd = false;

        source.setBuffer(0);
        int[] buffers = new int[initialBuffers.length];

        for (int i = 0; i < initialBuffers.length; i++) {
            AlSoundBuffer buffer = initialBuffers[i];
            buffers[i] = buffer.bufferId;
            if (fillBuffer(buffer)) {
                break;
            }
        }

        source.queueBuffer(buffers);
        source.play();
    }

    @Override
    public void stop() {
        playing = false;
        reachedEnd = false;
        source.stop();
    }

    @Override
    public boolean isPlaying() {
        return source!=null && playing && (!reachedEnd || source.isPlaying());
    }

    @Override
    public void pause() {
        playing = false;
        source.pause();
    }

    @Override
    public void resume() {
        playing = true;
        source.play();
    }

    @Override
    public void update() {
        if (!playing || (reachedEnd && !source.isPlaying() && !looping))
            return;

        int endingBuffer = 0;

        if (!reachedEnd) {
            int[] buffers = source.unQueueBuffers();
            for (int buffer : buffers) {
                if (fillBuffer(AlSoundBuffer.from(buffer)))
                    break;
                endingBuffer++;
            }
            if (endingBuffer != buffers.length) {
                reachedEnd = true;
            }

            if (!source.isPlaying() && !reachedEnd) {
                source.play();
            }

            if (endingBuffer!=buffers.length && endingBuffer>0) {
                source.queueBuffer(Arrays.copyOf(buffers, endingBuffer));
            } else {
                source.queueBuffer(buffers);
            }
        }
    }

    private boolean fillBuffer(AlSoundBuffer buffer) {
        boolean done = soundBank.fillBuffer(soundClip, buffer, pcm, streamingSampleLength);
        if (done && looping) {
            soundBank.seekBegin(soundClip);
            soundBank.fillBuffer(soundClip, buffer, pcm, streamingSampleLength);
            return false;
        }
        return done;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}

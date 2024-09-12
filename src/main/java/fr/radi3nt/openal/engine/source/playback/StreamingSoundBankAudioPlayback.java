package fr.radi3nt.openal.engine.source.playback;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.high.bank.streaming.StreamingSoundBank;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

public class StreamingSoundBankAudioPlayback implements AudioPlayback {

    private final StreamingSoundBank soundBank;
    private final int streamingSampleLength;
    private final int bufferAmount;

    private AlSoundSource source;
    private SoundClip soundClip;
    private int streamingCurrent;

    private AlSoundBuffer[] initialBuffers;

    private boolean playing = false;
    private boolean reachedEnd = false;
    private boolean looping = false;

    public StreamingSoundBankAudioPlayback(StreamingSoundBank soundBank, int streamingSampleLength, int bufferAmount) {
        this.soundBank = soundBank;
        this.streamingSampleLength = streamingSampleLength;
        this.bufferAmount = bufferAmount;
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
        streamingCurrent = 0;
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
        streamingCurrent = 0;
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

        int[] buffers = source.unQueueBuffers();
        boolean done = false;
        for (int i = 0; i < buffers.length; i++) {
            int buffer = buffers[i];
            if (buffer==0)
                continue;
            if (done) {
                buffers[i] = 0;
                continue;
            }
            done = fillBuffer(AlSoundBuffer.from(buffer));
        }
        if (done) {
            reachedEnd = true;
        }

        if (!source.isPlaying() && !reachedEnd) {
            source.play();
        }

        source.queueBuffer(buffers);
    }

    private boolean fillBuffer(AlSoundBuffer buffer) {
        boolean done = soundBank.fillBuffer(soundClip, buffer, streamingCurrent * streamingSampleLength, streamingSampleLength);
        if (done && looping) {
            soundBank.seekBegin(soundClip);
            streamingCurrent = 0;
            return false;
        } else
            streamingCurrent++;
        return done;
    }

    @Override
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}

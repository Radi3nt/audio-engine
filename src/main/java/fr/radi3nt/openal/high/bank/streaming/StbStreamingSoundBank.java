package fr.radi3nt.openal.high.bank.streaming;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.clip.stb.StbSound;
import fr.radi3nt.openal.engine.format.SoundChannel;
import fr.radi3nt.openal.engine.format.SoundFormat;
import fr.radi3nt.openal.engine.format.SoundResolution;
import fr.radi3nt.openal.high.bank.stb.StbLoadInSoundBank;
import fr.radi3nt.openal.high.bank.stb.StbSoundBank;
import org.lwjgl.BufferUtils;

import java.nio.ShortBuffer;
import java.util.HashMap;
import java.util.Map;

public class StbStreamingSoundBank implements StreamingSoundBank {

    private final Map<SoundClip, StbSound> stbMap = new HashMap<>();
    private final StbSoundBank stbSoundBank;

    public StbStreamingSoundBank(StbSoundBank stbSoundBank) {
        this.stbSoundBank = stbSoundBank;
    }

    @Override
    public boolean fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer, int samplePos, int sampleLength) {
        StbSound sound = stbMap.computeIfAbsent(soundClip, stbSoundBank::getSound);
        ShortBuffer pcm = BufferUtils.createShortBuffer(sound.channels * sampleLength);

        boolean hitEnd = StbLoadInSoundBank.fillSamples(pcm, sound);

        SoundFormat soundFormat = new SoundFormat(SoundChannel.from(sound.channels), SoundResolution.BIT_16);
        soundBuffer.fill(pcm, sound.sampleRate, soundFormat.getAlId());

        return hitEnd;
    }

    @Override
    public void seekBegin(SoundClip soundClip) {
        StbSound sound = stbMap.computeIfAbsent(soundClip, stbSoundBank::getSound);
        sound.seek(0);
    }
}

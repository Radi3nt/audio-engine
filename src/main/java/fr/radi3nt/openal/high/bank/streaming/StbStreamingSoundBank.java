package fr.radi3nt.openal.high.bank.streaming;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.clip.stb.StbSound;
import fr.radi3nt.openal.engine.format.SoundChannel;
import fr.radi3nt.openal.engine.format.SoundFormat;
import fr.radi3nt.openal.engine.format.SoundResolution;
import fr.radi3nt.openal.high.bank.stb.StbLoadInSoundBank;
import fr.radi3nt.openal.high.bank.stb.StbSoundBank;

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
    public boolean fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer, ShortBuffer pcm, int streamingSampleLength) {
        StbSound sound = stbMap.computeIfAbsent(soundClip, stbSoundBank::getSound);

        pcm.clear();
        pcm.limit(streamingSampleLength);
        boolean hitEnd = StbLoadInSoundBank.fillSamples(pcm, sound);
        pcm.flip();

        if (hitEnd) {
            return true;
        }

        SoundFormat soundFormat = new SoundFormat(SoundChannel.from(sound.channels), SoundResolution.BIT_16);
        soundBuffer.fill(pcm, sound.sampleRate, soundFormat.getAlId());

        return false;
    }

    @Override
    public void seekBegin(SoundClip soundClip) {
        StbSound sound = stbMap.get(soundClip);
        if (sound==null)
            return;
        sound.seekStart();
    }
}

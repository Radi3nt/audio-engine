package fr.radi3nt.openal.high.bank.stb;

import fr.radi3nt.file.impl.ResourceFile;
import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.FileSoundClip;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.clip.stb.StbSound;
import fr.radi3nt.openal.engine.format.SoundChannel;
import fr.radi3nt.openal.engine.format.SoundFormat;
import fr.radi3nt.openal.engine.format.SoundResolution;
import fr.radi3nt.openal.high.bank.load.LoadInSoundBank;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.ShortBuffer;

public class StbLoadInSoundBank implements LoadInSoundBank, StbSoundBank {

    public static boolean fillSamples(ShortBuffer pcm, StbSound sound) {
        int samples = 0;
        while (samples < pcm.capacity()) {
            pcm.position(samples);
            int samplesPerChannel = sound.getSamples(pcm);
            if (samplesPerChannel == 0) {
                pcm.limit(samples);
                return true;
            }

            samples += samplesPerChannel * sound.channels;
        }
        return false;
    }

    @Override
    public void fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer) {
        StbSound sound = getSound(soundClip);

        ShortBuffer pcm = BufferUtils.createShortBuffer(sound.channels * sound.sampleLength);

        fillSamples(pcm, sound);

        sound.delete();

        SoundFormat soundFormat = new SoundFormat(SoundChannel.from(sound.channels), SoundResolution.BIT_16);
        soundBuffer.fill(pcm, sound.sampleRate, soundFormat.getAlId());
    }

    @Override
    public StbSound getSound(SoundClip soundClip) {
        if (!(soundClip instanceof FileSoundClip))
            throw new IllegalArgumentException("Sound clip doesn't point to a file");

        ResourceFile resourceFile = ((FileSoundClip) soundClip).resourceFile;
        StbSound sound;
        try {
            sound = StbSound.from(resourceFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sound;
    }
}

package fr.radi3nt.openal.high.bank.streaming;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;

public interface StreamingSoundBank {
    boolean fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer, int samplePos, int sampleLength);

    void seekBegin(SoundClip soundClip);
}

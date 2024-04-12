package fr.radi3nt.openal.high.bank.load;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;

public interface LoadInSoundBank {
    void fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer);
}

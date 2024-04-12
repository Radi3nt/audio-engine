package fr.radi3nt.openal.high.bank.keep;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;

public interface KeepSoundBank {

    AlSoundBuffer fillBuffer(SoundClip soundClip);

}

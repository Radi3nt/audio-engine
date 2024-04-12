package fr.radi3nt.openal.high.bank.stb;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.clip.stb.StbSound;

public interface StbSoundBank {

    StbSound getSound(SoundClip sound);

}

package fr.radi3nt.openal.engine.source.attenuation;

import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.high.gain.PercentModifier;

public interface AudioAttenuation {

    void set(AudioSource audioSource, AlSoundSource source);

    void update(AudioSource audioSource, AlSoundSource source);

    PercentModifier getAttenuationModifier();

    PercentModifier getDopplerModifier();

}

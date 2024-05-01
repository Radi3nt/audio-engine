package fr.radi3nt.openal.engine.source.attenuation;

import fr.radi3nt.openal.engine.source.sources.AlSoundSourceHolder;
import fr.radi3nt.openal.high.gain.PercentModifier;

import java.util.Collection;

public interface AudioAttenuation {

    void added(AlSoundSourceHolder source);
    void update(Collection<? extends AlSoundSourceHolder> source);

    PercentModifier getAttenuationModifier();

    PercentModifier getDopplerModifier();

}

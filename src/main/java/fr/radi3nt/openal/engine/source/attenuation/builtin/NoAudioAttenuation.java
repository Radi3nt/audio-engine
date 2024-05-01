package fr.radi3nt.openal.engine.source.attenuation.builtin;

import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;
import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.SimpleAttenuation;
import fr.radi3nt.openal.engine.source.sources.sound.AlSoundSourceHolder;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

import java.util.Collection;

public class NoAudioAttenuation extends SimpleAttenuation implements AudioAttenuation {

    public static final NoAudioAttenuation INSTANCE = new NoAudioAttenuation();

    protected NoAudioAttenuation() {
    }

    @Override
    public void added(AlSoundSourceHolder sourceHolder) {
        AlSoundSource source = sourceHolder.getSource();
        source.setRelativeToListener(true);
        source.setPosition(new SimpleVector3f());
        source.setVelocity(new SimpleVector3f());
    }

    @Override
    public void update(Collection<? extends AlSoundSourceHolder> source) {

    }

    @Override
    public PercentModifier getAttenuationModifier() {
        return SetPercentModifier.NO_MODIFIER;
    }

    @Override
    public PercentModifier getDopplerModifier() {
        return SetPercentModifier.NO_MODIFIER;
    }
}

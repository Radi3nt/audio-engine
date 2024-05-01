package fr.radi3nt.openal.engine.source.attenuation.manual;

import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;
import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.source.attenuation.SimpleAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.manual.formula.AttenuationFormula;
import fr.radi3nt.openal.engine.source.attenuation.manual.listener.AudioListener;
import fr.radi3nt.openal.engine.source.attenuation.manual.spatialization.SpatializationFormula;
import fr.radi3nt.openal.engine.source.sources.sound.AlSoundSourceHolder;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

import java.util.Collection;
import java.util.function.Supplier;

public class ManualPositionalAudioAttenuation extends SimpleAttenuation {

    private final Supplier<Vector3f> position;
    private final Supplier<Vector3f> velocity;
    private final AudioListener audioListener;

    private final SetPercentModifier gain = new SetPercentModifier(1f);
    private final SetPercentModifier pitch = new SetPercentModifier(1f);

    private AttenuationFormula attenuationFormula;
    private SpatializationFormula spatializationFormula;

    public ManualPositionalAudioAttenuation(Supplier<Vector3f> position, Supplier<Vector3f> velocity, AudioListener audioListener, AttenuationFormula attenuationFormula, SpatializationFormula spatializationFormula) {
        this.position = position;
        this.velocity = velocity;
        this.audioListener = audioListener;
        this.attenuationFormula = attenuationFormula;
        this.spatializationFormula = spatializationFormula;
    }

    public ManualPositionalAudioAttenuation(Vector3f position, Vector3f velocity, AudioListener audioListener, AttenuationFormula attenuationFormula, SpatializationFormula spatializationFormula) {
        this.position = () -> position;
        this.velocity = () -> velocity;
        this.audioListener = audioListener;
        this.attenuationFormula = attenuationFormula;
        this.spatializationFormula = spatializationFormula;
    }

    public void setAttenuationFormula(AttenuationFormula attenuationFormula) {
        this.attenuationFormula = attenuationFormula;
    }

    public void setSpatializationFormula(SpatializationFormula spatializationFormula) {
        this.spatializationFormula = spatializationFormula;
    }

    @Override
    public void added(AlSoundSourceHolder sourceHolder) {
        AlSoundSource source = sourceHolder.getSource();
        source.setRelativeToListener(true);
        source.setVelocity(new SimpleVector3f());
        source.setReferenceDist(5);
        source.setMaximumDist(5);
    }

    @Override
    public void update(Collection<? extends AlSoundSourceHolder> sources) {
        Quaternion inverseOrientation = audioListener.getInverseOrientation();

        Vector3f position = this.position.get();
        Vector3f relativePos = spatializationFormula.spatialize(inverseOrientation, audioListener.getPosition(), position);

        for (AlSoundSourceHolder source : sources) {
            source.getSource().setPosition(relativePos);
        }

        gain.setTransform(attenuationFormula.attenuate(audioListener.getPosition(), position));
        pitch.setTransform(spatializationFormula.dopplerEffect(audioListener.getPosition(), position, audioListener.getVelocity(), velocity.get()));
    }

    @Override
    public PercentModifier getAttenuationModifier() {
        return gain;
    }

    @Override
    public PercentModifier getDopplerModifier() {
        return pitch;
    }
}

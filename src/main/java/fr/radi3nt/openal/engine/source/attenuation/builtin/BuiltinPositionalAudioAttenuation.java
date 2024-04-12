package fr.radi3nt.openal.engine.source.attenuation.builtin;

import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.PositionalAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.SimpleAttenuation;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

import java.util.function.Supplier;

public class BuiltinPositionalAudioAttenuation extends SimpleAttenuation implements AudioAttenuation, PositionalAttenuation {

    private final float referenceDist;
    private final float maxDist;
    private Supplier<Vector3f> position;
    private Supplier<Vector3f> velocity;

    public BuiltinPositionalAudioAttenuation(Supplier<Vector3f> position, Supplier<Vector3f> velocity, float referenceDist, float maxDist) {
        this.position = position;
        this.velocity = velocity;
        this.referenceDist = referenceDist;
        this.maxDist = maxDist;
    }

    public void setPosition(Supplier<Vector3f> position) {
        this.position = position;
    }

    public void setVelocity(Supplier<Vector3f> velocity) {
        this.velocity = velocity;
    }

    @Override
    public void set(AudioSource audioSource, AlSoundSource source) {
        source.setRelativeToListener(false);
        source.setMaximumDist(maxDist);
        source.setReferenceDist(referenceDist);
        update(audioSource, source);
    }

    @Override
    public void update(AudioSource audioSource, AlSoundSource source) {
        source.setPosition(position.get());
        source.setVelocity(velocity.get());
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

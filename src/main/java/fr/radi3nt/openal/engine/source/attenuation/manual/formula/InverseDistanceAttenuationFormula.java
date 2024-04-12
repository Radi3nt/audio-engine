package fr.radi3nt.openal.engine.source.attenuation.manual.formula;

import fr.radi3nt.maths.components.vectors.Vector3f;

public class InverseDistanceAttenuationFormula implements AttenuationFormula {

    private final float referenceDistance;
    private final float rollOffFactor;

    public InverseDistanceAttenuationFormula(float referenceDistance, float rollOffFactor) {
        this.referenceDistance = referenceDistance;
        this.rollOffFactor = rollOffFactor;
    }

    @Override
    public float attenuate(Vector3f listenerPosition, Vector3f currentPosition) {
        Vector3f distance = currentPosition.duplicate().sub(listenerPosition);
        float distSquared = distance.lengthSquared();
        float referenceDistanceSquared = referenceDistance * referenceDistance;

        return referenceDistanceSquared / (referenceDistanceSquared + rollOffFactor * (distSquared - referenceDistanceSquared));
    }
}

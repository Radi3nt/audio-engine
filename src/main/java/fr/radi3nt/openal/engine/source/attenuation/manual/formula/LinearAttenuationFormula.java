package fr.radi3nt.openal.engine.source.attenuation.manual.formula;

import fr.radi3nt.maths.components.vectors.Vector3f;

public class LinearAttenuationFormula implements AttenuationFormula {

    private final float startDecreaseDistance;
    private final float endDecreaseDistance;
    private final float endGain;

    public LinearAttenuationFormula(float startDecreaseDistance, float endDecreaseDistance, float endGain) {
        this.startDecreaseDistance = startDecreaseDistance;
        this.endDecreaseDistance = endDecreaseDistance;
        this.endGain = endGain;
    }


    @Override
    public float attenuate(Vector3f listenerPosition, Vector3f currentPosition) {
        Vector3f distance = currentPosition.duplicate().sub(listenerPosition);
        float distSquared = distance.lengthSquared();

        float startDecreaseDistSquared = startDecreaseDistance * startDecreaseDistance;
        float endDecreaseDistSquared = endDecreaseDistance * endDecreaseDistance;

        if (distSquared < startDecreaseDistSquared)
            return 1f;
        if (distSquared >= endDecreaseDistSquared)
            return endGain;

        return (1 - (distSquared - startDecreaseDistSquared) / (endDecreaseDistSquared - startDecreaseDistSquared)) * (1 - endGain) + endGain;
    }
}

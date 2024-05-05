package fr.radi3nt.openal.engine.source.attenuation.manual.spatialization;

import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;

import static java.lang.Math.min;

public class QuaternionSpatializationFormula implements SpatializationFormula {

    private final float speedOfSound;
    private final float dopplerFactor;

    public QuaternionSpatializationFormula(float speedOfSound, float dopplerFactor) {
        this.speedOfSound = speedOfSound;
        this.dopplerFactor = dopplerFactor;
    }

    @Override
    public Vector3f spatialize(Quaternion inverseOrientation, Vector3f listenerPos, Vector3f currentPos, Vector3f result) {
        result.copy(currentPos);

        Vector3f dist = result.sub(listenerPos);
        Vector3f forward = dist.normalizeSafely();
        inverseOrientation.transform(forward);

        return forward;
    }

    @Override
    public float dopplerEffect(Vector3f listenerPos, Vector3f currentPos, Vector3f listenerVelocity, Vector3f sourceVelocity) {
        Vector3f relativeVelocity = sourceVelocity.duplicate();
        relativeVelocity.sub(listenerVelocity);

        Vector3f sourceToListenerVector = listenerPos.duplicate().sub(currentPos);

        float magSl = sourceToListenerVector.length();
        float vls = sourceToListenerVector.dot(listenerVelocity) / magSl;
        float vss = sourceToListenerVector.dot(sourceVelocity) / magSl;


        vss = min(vss, speedOfSound / dopplerFactor);
        vls = min(vls, speedOfSound / dopplerFactor);

        return (speedOfSound - dopplerFactor * vls) / (speedOfSound - dopplerFactor * vss);
    }
}

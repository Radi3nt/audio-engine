package fr.radi3nt.openal.engine.source.attenuation.manual.spatialization;

import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;

public interface SpatializationFormula {
    Vector3f spatialize(Quaternion inverseOrientation, Vector3f listenerPos, Vector3f currentPos, Vector3f result);

    float dopplerEffect(Vector3f listenerPos, Vector3f currentPos, Vector3f listenerVelocity, Vector3f sourceVelocity);
}

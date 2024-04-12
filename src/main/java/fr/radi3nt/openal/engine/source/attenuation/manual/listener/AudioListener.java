package fr.radi3nt.openal.engine.source.attenuation.manual.listener;

import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;

public interface AudioListener {

    Vector3f getPosition();

    Vector3f getVelocity();

    Quaternion getInverseOrientation();

}

package fr.radi3nt.openal.engine.source.attenuation.manual.listener;

import fr.radi3nt.maths.components.advanced.quaternions.ComponentsQuaternion;
import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;

public class InstanceAudioListener implements AudioListener {

    public static final InstanceAudioListener INSTANCE = new InstanceAudioListener();

    private final Vector3f position = new SimpleVector3f();
    private final Vector3f velocity = new SimpleVector3f();
    private final Quaternion inverseOrientation = ComponentsQuaternion.zero();

    public InstanceAudioListener() {

    }

    public InstanceAudioListener(Vector3f position, Vector3f velocity) {
        setPosition(position);
        setVelocity(velocity);
    }

    public InstanceAudioListener(Vector3f position) {
        setPosition(position);
    }

    public void setOrientation(Quaternion orientation) {
        Quaternion inverse = orientation.duplicate();
        inverse.inverse();
        this.inverseOrientation.copy(inverse);
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    @Override
    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity.set(velocity);
    }

    @Override
    public Quaternion getInverseOrientation() {
        return inverseOrientation;
    }

}

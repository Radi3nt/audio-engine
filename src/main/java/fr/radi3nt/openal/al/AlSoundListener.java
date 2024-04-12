package fr.radi3nt.openal.al;

import fr.radi3nt.maths.components.advanced.quaternions.ComponentsQuaternion;
import fr.radi3nt.maths.components.advanced.quaternions.Quaternion;
import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.openal.AL10.*;

public class AlSoundListener {

    public static final AlSoundListener INSTANCE = new AlSoundListener();

    private final Quaternion orientation = ComponentsQuaternion.zero();

    private AlSoundListener() {
        this(new SimpleVector3f(0, 0, 0));
    }

    private AlSoundListener(Vector3f position) {
        alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }

    public void setSpeed(Vector3f speed) {
        alListener3f(AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
    }


    public void setPosition(Vector3f position) {
        alListener3f(AL_POSITION, position.getX(), position.getY(), position.getZ());
    }

    private void setOrientation(Vector3f at, Vector3f up) {
        FloatBuffer floatBuffer = MemoryUtil.memAllocFloat(6);

        floatBuffer.put(at.getX());
        floatBuffer.put(at.getY());
        floatBuffer.put(at.getZ());
        floatBuffer.put(up.getX());
        floatBuffer.put(up.getY());
        floatBuffer.put(up.getZ());
        floatBuffer.flip();

        alListenerfv(AL_ORIENTATION, floatBuffer);

        MemoryUtil.memFree(floatBuffer);
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public void setOrientation(Quaternion quaternion) {
        Vector3f up = new SimpleVector3f(0, -1, 0);
        quaternion.transform(up);
        Vector3f forward = new SimpleVector3f(0, 0, 1);
        quaternion.transform(forward);
        setOrientation(forward, up);

        orientation.copy(quaternion);
    }
}

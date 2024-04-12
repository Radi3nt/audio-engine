package fr.radi3nt.openal.high;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.AL_INVERSE_DISTANCE;
import static org.lwjgl.openal.AL10.alDistanceModel;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.ALC_DEFAULT_ALL_DEVICES_SPECIFIER;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioContext {

    private long device;
    private long context;

    public void start() {
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) {
            throw new RuntimeException("Could not get OpenAL device");
        }

        System.out.println(alcGetString(device, ALC_DEFAULT_ALL_DEVICES_SPECIFIER));

        context = alcCreateContext(device, (IntBuffer) null);
        if (!alcMakeContextCurrent(context)) {
            throw new RuntimeException("Could not set context");
        }
    }

    public void startAl() {
        AL.createCapabilities(ALC.createCapabilities(device));
        alDistanceModel(AL_INVERSE_DISTANCE);
    }

    public void stop() {
        ALC.destroy();
        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

}

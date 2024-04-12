package fr.radi3nt.openal.al;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.openal.AL10.*;

public class AlSoundBuffer {

    public final int bufferId;

    private AlSoundBuffer(int bufferId) {
        this.bufferId = bufferId;
    }

    public AlSoundBuffer() {
        this.bufferId = alGenBuffers();
    }

    public static AlSoundBuffer from(int bufferId) {
        return new AlSoundBuffer(bufferId);
    }

    public void fill(ByteBuffer buffer, int sampleRate, int format) {
        alBufferData(bufferId, format, buffer, sampleRate);
    }

    public void fill(ShortBuffer buffer, int sampleRate, int format) {
        alBufferData(bufferId, format, buffer, sampleRate);
    }

    public void delete() {
        alDeleteBuffers(bufferId);
    }
}

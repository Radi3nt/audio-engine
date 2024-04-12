package fr.radi3nt.openal.al;

import fr.radi3nt.maths.components.vectors.Vector3f;

import static org.lwjgl.openal.AL10.*;

public class AlSoundSource {

    private final int sourceId;

    public AlSoundSource() {
        this.sourceId = alGenSources();
    }

    public void setPosition(Vector3f position) {
        alSource3f(sourceId, AL_POSITION, position.getX(), position.getY(), position.getZ());
    }


    public void setVelocity(Vector3f speed) {
        alSource3f(sourceId, AL_VELOCITY, speed.getX(), speed.getY(), speed.getZ());
    }

    public void setReferenceDist(float dist) {
        alSourcef(sourceId, AL_REFERENCE_DISTANCE, dist);
    }


    public void setMaximumDist(float dist) {
        alSourcef(sourceId, AL_MAX_DISTANCE, dist);
    }

    public void setGain(float gain) {
        alSourcef(sourceId, AL_GAIN, gain);
    }

    public void setPitch(float pitch) {
        alSourcef(sourceId, AL_PITCH, pitch);
    }

    public void setRelativeToListener(boolean relative) {
        alSourcei(sourceId, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
    }

    public void setLooping(boolean looping) {
        alSourcei(sourceId, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
    }


    public void setBuffer(int bufferId) {
        alSourcei(sourceId, AL_BUFFER, bufferId);
    }

    public void clearBuffer() {
        alSourcei(sourceId, AL_BUFFER, 0);
    }


    public void queueBuffer(int[] buffers) {
        alSourceQueueBuffers(sourceId, buffers);
    }


    public void queueBuffer(int buffer) {
        alSourceQueueBuffers(sourceId, buffer);
    }

    public int[] unQueueBuffers() {
        int bufferAmount = alGetSourcei(sourceId, AL_BUFFERS_PROCESSED);
        int[] toDequeueBuffers = new int[bufferAmount];
        if (bufferAmount != 0)
            alSourceUnqueueBuffers(sourceId, toDequeueBuffers);
        return toDequeueBuffers;
    }


    public void delete() {
        stop();
        alDeleteSources(sourceId);
    }

    public void play() {
        alSourcePlay(sourceId);
    }

    public boolean isPlaying() {
        return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
    }

    public void pause() {
        alSourcePause(sourceId);
    }

    public void stop() {
        alSourceStop(sourceId);
    }

    @Override
    public String toString() {
        return "AlSoundSource{" +
                "sourceId=" + sourceId +
                '}';
    }
}

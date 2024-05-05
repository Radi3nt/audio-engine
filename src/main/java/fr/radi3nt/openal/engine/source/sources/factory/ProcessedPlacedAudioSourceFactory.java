package fr.radi3nt.openal.engine.source.sources.factory;

import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.high.task.AudioProcess;

public abstract class ProcessedPlacedAudioSourceFactory implements PlacedAudioSourceFactory {

    protected final AudioProcess audioProcess;

    protected ProcessedPlacedAudioSourceFactory(AudioProcess audioProcess) {
        this.audioProcess = audioProcess;
    }

    @Override
    public AudioSource create(Vector3f pos) {
        AudioSource source = createSource(pos);
        audioProcess.add(source);
        return source;
    }

    protected abstract AudioSource createSource(Vector3f pos);
}

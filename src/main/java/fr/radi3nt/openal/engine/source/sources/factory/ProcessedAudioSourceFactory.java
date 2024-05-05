package fr.radi3nt.openal.engine.source.sources.factory;

import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.high.task.AudioProcess;

public abstract class ProcessedAudioSourceFactory implements AudioSourceFactory {

    protected final AudioProcess audioProcess;

    protected ProcessedAudioSourceFactory(AudioProcess audioProcess) {
        this.audioProcess = audioProcess;
    }

    @Override
    public AudioSource create() {
        AudioSource source = createSource();
        audioProcess.add(source);
        return source;
    }

    protected abstract AudioSource createSource();
}

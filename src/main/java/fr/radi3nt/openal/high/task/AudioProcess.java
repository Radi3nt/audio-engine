package fr.radi3nt.openal.high.task;

import fr.radi3nt.openal.engine.source.AudioSource;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class AudioProcess {

    private final Collection<AudioSource> sources = new CopyOnWriteArrayList<>();

    public AudioSource add(AudioSource audioSource) {
        sources.add(audioSource);
        return audioSource;
    }

    public void remove(AudioSource audioSource) {
        sources.remove(audioSource);
    }

    public void update() {
        for (AudioSource source : sources) {
            source.update();
        }
    }
}

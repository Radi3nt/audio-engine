package fr.radi3nt.openal.engine.source.sources.factory;

import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.source.InstantAudioSource;
import fr.radi3nt.openal.engine.source.sources.unit.pool.UnitSoundSourcePool;
import fr.radi3nt.openal.high.task.AudioProcess;

import java.util.function.Supplier;

public class InstantAudioSourceFactory extends ProcessedAudioSourceFactory {

    private final AudioAttenuation audioAttenuation;
    private final Supplier<AudioPlayback> playbackSupplier;
    private final UnitSoundSourcePool pool;

    public InstantAudioSourceFactory(AudioProcess audioProcess, AudioAttenuation audioAttenuation, Supplier<AudioPlayback> playbackSupplier, UnitSoundSourcePool pool) {
        super(audioProcess);
        this.audioAttenuation = audioAttenuation;
        this.playbackSupplier = playbackSupplier;
        this.pool = pool;
    }

    @Override
    protected AudioSource createSource() {
        return new InstantAudioSource(audioAttenuation, playbackSupplier.get(), pool, audioProcess);
    }
}

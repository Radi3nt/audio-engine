package fr.radi3nt.openal.engine.source.sources.factory;

import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.source.InstantAudioSource;
import fr.radi3nt.openal.engine.source.sources.unit.pool.UnitSoundSourcePool;
import fr.radi3nt.openal.high.task.AudioProcess;

import java.util.function.Function;
import java.util.function.Supplier;

public class InstantPlacedAudioSourceFactory extends ProcessedPlacedAudioSourceFactory {

    private final Function<Vector3f, AudioAttenuation> audioAttenuation;
    private final Supplier<AudioPlayback> playbackSupplier;
    private final UnitSoundSourcePool pool;

    public InstantPlacedAudioSourceFactory(AudioProcess audioProcess, Function<Vector3f, AudioAttenuation> audioAttenuation, Supplier<AudioPlayback> playbackSupplier, UnitSoundSourcePool pool) {
        super(audioProcess);
        this.audioAttenuation = audioAttenuation;
        this.playbackSupplier = playbackSupplier;
        this.pool = pool;
    }

    @Override
    protected AudioSource createSource(Vector3f pos) {
        return new InstantAudioSource(audioAttenuation.apply(pos), playbackSupplier.get(), pool, audioProcess);
    }
}

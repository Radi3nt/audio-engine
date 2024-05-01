package fr.radi3nt.openal.engine.source.sources.unit.pool;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.HandledSoundSource;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReusingUnitSoundSourcePool implements UnitSoundSourcePool {

    private final Queue<HandledSoundSource> freeSources = new ConcurrentLinkedQueue<>();

    @Override
    public UnitSoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback) {
        HandledSoundSource poll = freeSources.poll();
        if (poll!=null)
            return new UnitSoundSource(attenuation, playback, poll);
        return new UnitSoundSource(attenuation, playback);
    }

    @Override
    public void free(UnitSoundSource source) {
        freeSources.add(source.getHandledSource());
    }
}

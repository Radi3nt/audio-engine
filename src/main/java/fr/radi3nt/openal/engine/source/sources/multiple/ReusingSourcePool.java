package fr.radi3nt.openal.engine.source.sources.multiple;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.sound.HandledSource;
import fr.radi3nt.openal.engine.source.sources.sound.SoundSource;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReusingSourcePool implements SourcePool {

    private final Queue<HandledSource> freeSources = new ConcurrentLinkedQueue<>();

    @Override
    public SoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback) {
        HandledSource poll = freeSources.poll();
        if (poll!=null)
            return new SoundSource(attenuation, playback, poll);
        return new SoundSource(attenuation, playback);
    }

    @Override
    public void free(SoundSource source) {
        freeSources.add(source.getHandledSource());
    }
}

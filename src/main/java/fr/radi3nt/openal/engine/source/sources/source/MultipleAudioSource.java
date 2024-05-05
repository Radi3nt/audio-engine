package fr.radi3nt.openal.engine.source.sources.source;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;
import fr.radi3nt.openal.engine.source.sources.unit.pool.UnitSoundSourcePool;
import fr.radi3nt.openal.high.gain.PercentModifier;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class MultipleAudioSource implements AudioSource {

    private final Collection<UnitSoundSource> playingSources = ConcurrentHashMap.newKeySet();
    private final UnitSoundSourcePool sourcePool;

    private final AudioAttenuation audioAttenuation;
    private final Function<SoundClip, AudioPlayback> playbackFactory;

    public MultipleAudioSource(UnitSoundSourcePool sourcePool, AudioAttenuation audioAttenuation, Function<SoundClip, AudioPlayback> playbackFactory) {
        this.sourcePool = sourcePool;
        this.audioAttenuation = audioAttenuation;
        this.playbackFactory = playbackFactory;
    }

    @Override
    public synchronized SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch) {
        AudioPlayback playback = playbackFactory.apply(clip);
        UnitSoundSource source = sourcePool.borrow(audioAttenuation, playback);
        audioAttenuation.added(source);

        SoundHandle play = source.play(clip, gain, pitch);
        playingSources.add(source);
        return play;
    }

    @Override
    public void update() {
        for (UnitSoundSource playingSource : playingSources) {
            if (!playingSource.isCompleted()) {
                playingSource.update();
                continue;
            }
            playingSources.remove(playingSource);
            sourcePool.free(playingSource);
        }

        audioAttenuation.update(playingSources);
    }

}

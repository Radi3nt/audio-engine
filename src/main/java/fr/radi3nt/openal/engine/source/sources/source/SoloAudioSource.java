package fr.radi3nt.openal.engine.source.sources.source;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;
import fr.radi3nt.openal.engine.source.sources.unit.pool.CleaningUnitSoundSourcePool;
import fr.radi3nt.openal.engine.source.sources.unit.pool.UnitSoundSourcePool;
import fr.radi3nt.openal.high.gain.PercentModifier;

import java.util.Collections;

public class SoloAudioSource implements AudioSource {

    private final AudioAttenuation audioAttenuation;
    private final UnitSoundSourcePool pool;
    protected final UnitSoundSource source;

    public SoloAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playback) {
        this(audioAttenuation, playback, CleaningUnitSoundSourcePool.INSTANCE);
    }

    public SoloAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playback, UnitSoundSourcePool sourcePool) {
        this.audioAttenuation = audioAttenuation;
        this.pool = sourcePool;
        this.source = sourcePool.borrow(audioAttenuation, playback);

        audioAttenuation.added(source);
    }

    @Override
    public synchronized SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch) {
        audioAttenuation.update(Collections.singleton(source));
        return source.play(clip, gain, pitch);
    }

    @Override
    public void update() {
        audioAttenuation.update(Collections.singleton(source));
        source.update();
    }

    public void destroy() {
        pool.free(source);
    }

}

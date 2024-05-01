package fr.radi3nt.openal.engine.source.sources.unit.pool;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;

public class CleaningUnitSoundSourcePool implements UnitSoundSourcePool {

    @Override
    public UnitSoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback) {
        return new UnitSoundSource(attenuation, playback);
    }

    @Override
    public void free(UnitSoundSource source) {
        source.getHandledSource().destroy();
    }
}

package fr.radi3nt.openal.engine.source.sources.unit.pool;

import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;

public class CleaningUnitSoundSourcePool implements UnitSoundSourcePool {

    public static final CleaningUnitSoundSourcePool INSTANCE = new CleaningUnitSoundSourcePool();

    @Override
    public UnitSoundSource borrow(AudioAttenuation attenuation, AudioPlayback playback) {
        return new UnitSoundSource(attenuation, playback);
    }

    @Override
    public void free(UnitSoundSource source) {
        source.getHandledSource().destroy();
    }
}

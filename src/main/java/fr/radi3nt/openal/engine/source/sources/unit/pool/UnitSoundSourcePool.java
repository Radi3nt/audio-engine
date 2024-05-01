package fr.radi3nt.openal.engine.source.sources.unit.pool;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;

public interface UnitSoundSourcePool {

    UnitSoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback);
    void free(UnitSoundSource source);

}

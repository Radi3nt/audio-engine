package fr.radi3nt.openal.engine.source.sources.multiple;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.sound.SoundSource;

public interface SourcePool {

    SoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback);
    void free(SoundSource source);

}

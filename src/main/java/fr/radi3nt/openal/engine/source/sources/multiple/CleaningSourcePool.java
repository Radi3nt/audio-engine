package fr.radi3nt.openal.engine.source.sources.multiple;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.sound.SoundSource;

public class CleaningSourcePool implements SourcePool {

    @Override
    public SoundSource borrow(SoundClip clip, AudioAttenuation attenuation, AudioPlayback playback) {
        return new SoundSource(attenuation, playback);
    }

    @Override
    public void free(SoundSource source) {
        source.getHandledSource().destroy();
    }
}

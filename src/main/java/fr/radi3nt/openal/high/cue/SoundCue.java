package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;

/**
 * Interface representing a set of sound clips. Can be reused, and doesn't contain any audio data, only how {@link SoundClip} should be queued;
 */
public interface SoundCue {

    default SoundHandle queue(AudioSource source) {
        return queue(source, 1f, 1f);
    }
    SoundHandle queue(AudioSource source, float gain, float pitch);

}

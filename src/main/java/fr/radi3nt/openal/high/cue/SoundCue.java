package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;

/**
 * Interface representing a set of sound clips. Can be reused, and doesn't contain any audio data, only how {@link SoundClip} should be queued;
 */
public interface SoundCue {

    void queue(AudioSource source);

}

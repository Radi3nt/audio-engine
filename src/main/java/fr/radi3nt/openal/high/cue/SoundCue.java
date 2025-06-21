package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

/**
 * Interface representing a set of sound clips. Can be reused, and doesn't contain any audio data, only how {@link SoundClip} should be queued;
 */
public interface SoundCue {

    default SoundHandle queue(AudioSource source) {
        return queue(source, SetPercentModifier.NO_MODIFIER);
    }
    default SoundHandle queue(AudioSource source, float gain, float pitch) {
        return queue(source, new SetPercentModifier(gain), new SetPercentModifier(pitch));
    }
    default SoundHandle queue(AudioSource audioSource, PercentModifier gain) {
        return queue(audioSource, gain, SetPercentModifier.NO_MODIFIER);
    }
    SoundHandle queue(AudioSource audioSource, PercentModifier gain, PercentModifier pitch);

}

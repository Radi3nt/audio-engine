package fr.radi3nt.openal.engine.source;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

public interface AudioSource {

    SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch);

    default SoundHandle play(SoundClip clip, float gain, float pitch) {
        return play(clip, new SetPercentModifier(gain), new SetPercentModifier(pitch));
    }

    default SoundHandle play(SoundClip clip) {
        return play(clip, 1f, 1f);
    }

    void update();

}

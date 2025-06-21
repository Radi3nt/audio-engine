package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.high.gain.PercentModifier;

public class ClipSoundCue implements SoundCue {

    private final SoundClip soundClip;

    public ClipSoundCue(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    @Override
    public SoundHandle queue(AudioSource source, PercentModifier gain, PercentModifier pitch) {
        return source.play(soundClip, gain, pitch);
    }
}

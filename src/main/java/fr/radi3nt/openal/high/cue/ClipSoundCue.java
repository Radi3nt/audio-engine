package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;

public class ClipSoundCue implements SoundCue {

    private final SoundClip soundClip;

    public ClipSoundCue(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    @Override
    public SoundHandle queue(AudioSource source, float gain, float pitch) {
        return source.play(soundClip, gain, pitch);
    }
}

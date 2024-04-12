package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;

public class ClipSoundCue implements SoundCue {

    private final SoundClip soundClip;

    public ClipSoundCue(SoundClip soundClip) {
        this.soundClip = soundClip;
    }

    @Override
    public void queue(AudioSource source) {
        source.play(soundClip);
    }
}

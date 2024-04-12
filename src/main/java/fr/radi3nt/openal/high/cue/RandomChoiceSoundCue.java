package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;

import java.security.SecureRandom;

public class RandomChoiceSoundCue implements SoundCue {

    private final SoundClip[] soundClips;
    private final SecureRandom random;

    public RandomChoiceSoundCue(SoundClip[] soundClips, SecureRandom random) {
        this.soundClips = soundClips;
        this.random = random;
    }

    public RandomChoiceSoundCue(SoundClip[] soundClips) {
        this(soundClips, new SecureRandom());
    }

    @Override
    public void queue(AudioSource source) {
        source.play(soundClips[random.nextInt(soundClips.length)]);
    }
}

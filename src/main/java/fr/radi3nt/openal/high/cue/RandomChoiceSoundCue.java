package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;

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
    public SoundHandle queue(AudioSource source, float gain, float pitch) {
        return source.play(soundClips[random.nextInt(soundClips.length)], gain, pitch);
    }
}

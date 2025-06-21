package fr.radi3nt.openal.high.cue;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.high.gain.ParentPercentModifier;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

import java.security.SecureRandom;
import java.util.function.Supplier;

public class RandomChoiceSoundCue implements SoundCue {

    private final SoundClip[] soundClips;
    private final Supplier<PercentModifier> pitchModifier;
    private final SecureRandom random;

    public RandomChoiceSoundCue(SoundClip[] soundClips, SecureRandom random) {
        this(soundClips, () -> SetPercentModifier.NO_MODIFIER, random);
    }

    public RandomChoiceSoundCue(SoundClip[] soundClips) {
        this(soundClips, new SecureRandom());
    }

    public RandomChoiceSoundCue(SoundClip[] soundClips, Supplier<PercentModifier> pitchModifier) {
        this(soundClips, pitchModifier, new SecureRandom());
    }

    public RandomChoiceSoundCue(SoundClip[] soundClips, Supplier<PercentModifier> pitchModifier, SecureRandom random) {
        this.soundClips = soundClips;
        this.pitchModifier = pitchModifier;
        this.random = random;
    }



    @Override
    public SoundHandle queue(AudioSource source, PercentModifier gain, PercentModifier pitch) {
        return source.play(soundClips[random.nextInt(soundClips.length)], gain, new ParentPercentModifier(pitchModifier.get(), pitch));
    }
}

package fr.radi3nt.openal.engine.source.playback;

import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.high.gain.PercentModifier;

public interface AudioPlayback {

    void set(SoundClip soundClip, AlSoundSource source);

    PercentModifier getGainMultiplier();

    PercentModifier getPitchMultiplier();

    void play();

    void stop();

    void pause();

    void resume();

    void update();

    boolean isPlaying();

    void setLooping(boolean looping);


}

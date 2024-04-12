package fr.radi3nt.openal.engine.source.handle;

import fr.radi3nt.openal.high.gain.PercentModifier;

public interface SoundHandle {

    void stop();

    boolean isDone();

    void setLooping(boolean looping);

    PercentModifier getGain();

    PercentModifier getPitch();

}

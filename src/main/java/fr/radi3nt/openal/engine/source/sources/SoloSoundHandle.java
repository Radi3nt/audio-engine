package fr.radi3nt.openal.engine.source.sources;

import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.high.gain.PercentModifier;

public class SoloSoundHandle implements SoundHandle {

    private final PercentModifier gain;
    private final PercentModifier pitch;
    private final AudioPlayback playbackModule;

    private boolean done;

    public SoloSoundHandle(PercentModifier gain, PercentModifier pitch, AudioPlayback playbackModule) {
        this.gain = gain;
        this.pitch = pitch;
        this.playbackModule = playbackModule;
    }

    @Override
    public void stop() {
        playbackModule.stop();
    }

    @Override
    public boolean isDone() {
        return done;
    }

    public void setDone() {
        this.done = true;
    }

    @Override
    public void setLooping(boolean looping) {
        playbackModule.setLooping(looping);
    }

    @Override
    public PercentModifier getGain() {
        return gain;
    }

    @Override
    public PercentModifier getPitch() {
        return pitch;
    }
}

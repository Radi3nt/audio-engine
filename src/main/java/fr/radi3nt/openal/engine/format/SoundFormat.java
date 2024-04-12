package fr.radi3nt.openal.engine.format;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO8;

public class SoundFormat {

    public final SoundChannel soundChannel;
    public final SoundResolution soundResolution;

    public SoundFormat(SoundChannel soundChannel, SoundResolution soundResolution) {
        this.soundChannel = soundChannel;
        this.soundResolution = soundResolution;
    }

    public int getAlId() {
        return AL_FORMAT_MONO8 + soundResolution.ordinal() + soundChannel.ordinal() * 2;
    }
}

package fr.radi3nt.openal.engine.format;

public enum SoundChannel {

    MONO(1),
    STEREO(2),
    ;

    private final int channelsAmount;

    SoundChannel(int channelsAmount) {
        this.channelsAmount = channelsAmount;
    }

    public static SoundChannel from(int channels) {
        for (SoundChannel value : values()) {
            if (value.getAmount() == channels)
                return value;
        }

        throw new IllegalArgumentException("Channel amount :" + channels + " is unsupported");
    }

    public int getAmount() {
        return channelsAmount;
    }
}

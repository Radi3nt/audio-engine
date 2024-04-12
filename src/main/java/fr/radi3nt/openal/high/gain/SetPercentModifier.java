package fr.radi3nt.openal.high.gain;

public class SetPercentModifier implements PercentModifier {

    public static final PercentModifier NO_MODIFIER = () -> 1f;

    private float percent;

    public SetPercentModifier(float percent) {
        this.percent = percent;
    }

    public void setTransform(float gain) {
        this.percent = gain;
    }

    @Override
    public float percentModifier() {
        return this.percent;
    }
}

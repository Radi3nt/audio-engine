package fr.radi3nt.openal.high.gain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ParentPercentModifier implements PercentModifier {

    private final Collection<PercentModifier> gain;

    public ParentPercentModifier(Collection<PercentModifier> gain) {
        this.gain = gain;
    }

    public ParentPercentModifier(PercentModifier... gain) {
        this.gain = new ArrayList<>(Arrays.asList(gain));
    }

    public Collection<PercentModifier> getModifiers() {
        return gain;
    }

    @Override
    public float percentModifier() {
        float value = 1f;
        for (PercentModifier sourceGain : gain) {
            value *= sourceGain.percentModifier();
        }
        return value;
    }
}

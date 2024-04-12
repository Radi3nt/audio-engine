package fr.radi3nt.openal.engine.source.attenuation.manual.formula;

import fr.radi3nt.maths.components.vectors.Vector3f;

public interface AttenuationFormula {
    float attenuate(Vector3f listenerPosition, Vector3f currentPosition);
}

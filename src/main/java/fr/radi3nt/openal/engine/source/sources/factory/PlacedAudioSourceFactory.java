package fr.radi3nt.openal.engine.source.sources.factory;

import fr.radi3nt.maths.components.vectors.Vector3f;
import fr.radi3nt.openal.engine.source.AudioSource;

public interface PlacedAudioSourceFactory {

    AudioSource create(Vector3f pos);

}

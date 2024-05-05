package fr.radi3nt.openal.engine.source.sources.source;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.UnitSoundSource;
import fr.radi3nt.openal.high.gain.PercentModifier;

import java.util.Collections;

public class SoloAudioSource implements AudioSource {

    private final AudioAttenuation audioAttenuation;
    private final UnitSoundSource source;

    public SoloAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playbackModule) {
        this.audioAttenuation = audioAttenuation;
        source = new UnitSoundSource(audioAttenuation, playbackModule);

        audioAttenuation.added(source);
    }


    @Override
    public synchronized SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch) {
        return source.play(clip, gain, pitch);
    }

    @Override
    public void update() {
        audioAttenuation.update(Collections.singleton(source));
        source.update();
    }


}
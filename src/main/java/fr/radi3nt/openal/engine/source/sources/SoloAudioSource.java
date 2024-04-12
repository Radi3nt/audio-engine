package fr.radi3nt.openal.engine.source.sources;

import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.high.gain.ParentPercentModifier;
import fr.radi3nt.openal.high.gain.PercentModifier;

import java.util.ArrayList;

public class SoloAudioSource implements AudioSource {

    private final AudioAttenuation audioAttenuation;
    private final AudioPlayback playbackModule;

    private final ParentPercentModifier globalSourceGain = new ParentPercentModifier(new ArrayList<>());
    private final ParentPercentModifier globalSourcePitch = new ParentPercentModifier(new ArrayList<>());

    private final ParentPercentModifier currentSoundGain = new ParentPercentModifier(new ArrayList<>());
    private final ParentPercentModifier currentSoundPitch = new ParentPercentModifier(new ArrayList<>());

    private final AlSoundSource alSoundSource = new AlSoundSource();

    private SoloSoundHandle currentHandle;

    public SoloAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playbackModule) {
        this.audioAttenuation = audioAttenuation;
        this.playbackModule = playbackModule;

        audioAttenuation.set(this, alSoundSource);

        globalSourceGain.getModifiers().add(audioAttenuation.getAttenuationModifier());
        globalSourceGain.getModifiers().add(playbackModule.getGainMultiplier());

        globalSourcePitch.getModifiers().add(audioAttenuation.getDopplerModifier());
        globalSourcePitch.getModifiers().add(playbackModule.getPitchMultiplier());

        globalSourceGain.getModifiers().add(currentSoundGain);
        globalSourcePitch.getModifiers().add(currentSoundPitch);
    }


    @Override
    public synchronized SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch) {
        playbackModule.set(clip, alSoundSource);

        currentSoundGain.getModifiers().clear();
        currentSoundGain.getModifiers().add(gain);

        currentSoundPitch.getModifiers().clear();
        currentSoundPitch.getModifiers().add(pitch);

        playbackModule.play();

        audioAttenuation.update(this, alSoundSource);

        if (currentHandle != null) {
            currentHandle.setDone();
        }

        return currentHandle = new SoloSoundHandle(gain, pitch, playbackModule);
    }

    @Override
    public void update() {
        audioAttenuation.update(this, alSoundSource);
        playbackModule.update();

        alSoundSource.setGain(globalSourceGain.percentModifier());
        alSoundSource.setPitch(globalSourcePitch.percentModifier());

        if (currentHandle != null && !playbackModule.isPlaying()) {
            currentHandle.setDone();
        }
    }


}

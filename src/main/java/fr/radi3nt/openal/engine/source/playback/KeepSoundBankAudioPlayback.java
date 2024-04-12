package fr.radi3nt.openal.engine.source.playback;

import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.high.bank.keep.KeepSoundBank;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.gain.SetPercentModifier;

public class KeepSoundBankAudioPlayback implements AudioPlayback {

    private final KeepSoundBank soundBank;
    private AlSoundSource source;

    public KeepSoundBankAudioPlayback(KeepSoundBank soundBank) {
        this.soundBank = soundBank;
    }

    @Override
    public void set(SoundClip soundClip, AlSoundSource source) {
        if (this.source != null)
            stop();
        this.source = source;

        source.setBuffer(soundBank.fillBuffer(soundClip).bufferId);
    }

    @Override
    public PercentModifier getGainMultiplier() {
        return SetPercentModifier.NO_MODIFIER;
    }

    @Override
    public PercentModifier getPitchMultiplier() {
        return SetPercentModifier.NO_MODIFIER;
    }

    @Override
    public void play() {
        source.play();
    }

    @Override
    public void stop() {
        source.stop();
    }

    @Override
    public boolean isPlaying() {
        return source.isPlaying();
    }

    @Override
    public void pause() {
        source.pause();
    }

    @Override
    public void resume() {
        source.play();
    }

    @Override
    public void update() {

    }

    @Override
    public void setLooping(boolean looping) {
        source.setLooping(looping);
    }
}

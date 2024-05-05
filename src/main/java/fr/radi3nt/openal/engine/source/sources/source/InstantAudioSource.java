package fr.radi3nt.openal.engine.source.sources.source;

import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.AudioAttenuation;
import fr.radi3nt.openal.engine.source.handle.SoloSoundHandle;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.unit.pool.UnitSoundSourcePool;
import fr.radi3nt.openal.high.gain.PercentModifier;
import fr.radi3nt.openal.high.task.AudioProcess;

public class InstantAudioSource extends SoloAudioSource {

    private final AudioProcess audioProcess;
    private boolean played = false;

    public InstantAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playback, UnitSoundSourcePool sourcePool, AudioProcess audioProcess) {
        super(audioAttenuation, playback, sourcePool);
        this.audioProcess = audioProcess;
    }

    public InstantAudioSource(AudioAttenuation audioAttenuation, AudioPlayback playback, AudioProcess audioProcess) {
        super(audioAttenuation, playback);
        this.audioProcess = audioProcess;
    }

    @Override
    public synchronized SoundHandle play(SoundClip clip, PercentModifier gain, PercentModifier pitch) {
        SoundHandle handle = super.play(clip, gain, pitch);
        played = true;
        return handle;
    }

    @Override
    public void update() {
        super.update();

        SoloSoundHandle handle = this.source.getHandledSource().getCurrentHandle();
        if (played && (handle==null || handle.isDone())) {
            audioProcess.remove(this);
            destroy();
        }
    }
}

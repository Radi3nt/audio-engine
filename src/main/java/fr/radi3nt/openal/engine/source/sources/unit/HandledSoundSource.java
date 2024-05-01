package fr.radi3nt.openal.engine.source.sources.unit;

import fr.radi3nt.openal.al.AlSoundSource;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.sources.SoloSoundHandle;
import fr.radi3nt.openal.engine.source.sources.AlSoundSourceHolder;
import fr.radi3nt.openal.high.gain.PercentModifier;

public class HandledSoundSource implements AlSoundSourceHolder {

    private final AlSoundSource alSoundSource;
    private SoloSoundHandle currentHandle;

    public HandledSoundSource() {
        this(new AlSoundSource());
    }

    public HandledSoundSource(AlSoundSource source) {
        this.alSoundSource = source;
    }

    public SoundHandle setHandle(AudioPlayback playback, PercentModifier gain, PercentModifier pitch) {
        if (currentHandle != null) {
            currentHandle.stop();
            currentHandle.setDone();
        }

        return currentHandle = new SoloSoundHandle(gain, pitch, playback);
    }

    public void update(float gain, float pitch, boolean invalidate) {
        alSoundSource.setGain(gain);
        alSoundSource.setPitch(pitch);

        if (currentHandle != null && invalidate) {
            currentHandle.stop();
            currentHandle.setDone();
            currentHandle = null;
        }
    }

    @Override
    public AlSoundSource getSource() {
        return alSoundSource;
    }

    public boolean isDone() {
        return currentHandle==null || currentHandle.isDone();
    }

    public void destroy() {
        alSoundSource.delete();
    }
}

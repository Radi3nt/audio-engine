package fr.radi3nt.openal.high.bank.keep;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.high.bank.load.LoadInSoundBank;

import java.util.HashMap;
import java.util.Map;

public class EncapsulatingKeepSoundBank implements KeepSoundBank {

    private final Map<SoundClip, AlSoundBuffer> bufferMap = new HashMap<>();
    private final LoadInSoundBank soundBank;

    public EncapsulatingKeepSoundBank(LoadInSoundBank soundBank) {
        this.soundBank = soundBank;
    }

    @Override
    public AlSoundBuffer fillBuffer(SoundClip soundClip) {
        return bufferMap.computeIfAbsent(soundClip, soundClip1 -> {
            AlSoundBuffer soundBuffer = new AlSoundBuffer();
            soundBank.fillBuffer(soundClip1, soundBuffer);
            return soundBuffer;
        });
    }
}

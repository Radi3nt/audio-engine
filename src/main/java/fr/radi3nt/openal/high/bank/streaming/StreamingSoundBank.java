package fr.radi3nt.openal.high.bank.streaming;

import fr.radi3nt.openal.al.AlSoundBuffer;
import fr.radi3nt.openal.engine.clip.SoundClip;

import java.nio.ShortBuffer;

public interface StreamingSoundBank {
    boolean fillBuffer(SoundClip soundClip, AlSoundBuffer soundBuffer, ShortBuffer pcm, int streamingSampleLength);

    void seekBegin(SoundClip soundClip);
}

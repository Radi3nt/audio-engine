package fr.radi3nt.openal;

import fr.radi3nt.file.impl.ResourceFile;
import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;
import fr.radi3nt.openal.engine.clip.FileSoundClip;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.AudioSource;
import fr.radi3nt.openal.engine.source.attenuation.builtin.NoAudioAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.manual.ManualPositionalAudioAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.manual.formula.InverseDistanceAttenuationFormula;
import fr.radi3nt.openal.engine.source.attenuation.manual.listener.AudioListener;
import fr.radi3nt.openal.engine.source.attenuation.manual.listener.InstanceAudioListener;
import fr.radi3nt.openal.engine.source.attenuation.manual.spatialization.QuaternionSpatializationFormula;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.AudioPlayback;
import fr.radi3nt.openal.engine.source.playback.KeepSoundBankAudioPlayback;
import fr.radi3nt.openal.engine.source.playback.StreamingSoundBankAudioPlayback;
import fr.radi3nt.openal.engine.source.sources.source.MultipleAudioSource;
import fr.radi3nt.openal.engine.source.sources.unit.pool.ReusingUnitSoundSourcePool;
import fr.radi3nt.openal.high.AudioEngine;
import fr.radi3nt.openal.high.bank.keep.EncapsulatingKeepSoundBank;
import fr.radi3nt.openal.high.bank.keep.KeepSoundBank;
import fr.radi3nt.openal.high.bank.stb.StbLoadInSoundBank;
import fr.radi3nt.openal.high.bank.streaming.StbStreamingSoundBank;
import fr.radi3nt.timing.TimingUtil;

import java.util.function.Function;

public class MainOpenAL {


    public static void main(String[] args) {
        AudioEngine engine = new AudioEngine();
        engine.prepare();


        Thread audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                engine.start();
            }
        });
        audioThread.start();


        SoundClip birds = new FileSoundClip(new ResourceFile("/assets/sounds/imported/birds.ogg"));
        SoundClip lightRainAmbient = new FileSoundClip(new ResourceFile("/assets/sounds/imported/light_rain_ambient.ogg"));
        SoundClip edenMusic = new FileSoundClip(new ResourceFile("/assets/sounds/27.ogg"));
        SoundClip mono = new FileSoundClip(new ResourceFile("/assets/sounds/1.ogg"));

        StbLoadInSoundBank bank = new StbLoadInSoundBank();
        KeepSoundBank keepSoundBank = new EncapsulatingKeepSoundBank(bank);
        StbStreamingSoundBank stbStreamingSoundBank = new StbStreamingSoundBank(bank);

        float[] t = new float[]{0};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioListener audioListener = InstanceAudioListener.INSTANCE;

                AudioSource music = new MultipleAudioSource(new ReusingUnitSoundSourcePool(), NoAudioAttenuation.INSTANCE, soundClip -> new StreamingSoundBankAudioPlayback(stbStreamingSoundBank, 2048 * 8, 3));
                AudioSource audioSource = new MultipleAudioSource(new ReusingUnitSoundSourcePool(), new ManualPositionalAudioAttenuation(() -> {
                    return new SimpleVector3f(Math.cos(t[0]) * 10, 0, Math.sin(t[0]) * 10);
                }, () -> {
                    return new SimpleVector3f(-Math.sin(t[0]) * 5 * 1, 0, Math.cos(t[0]) * 5 * 1);
                }, audioListener, new InverseDistanceAttenuationFormula(2, 1f), new QuaternionSpatializationFormula(343, 1f)),
                        new Function<SoundClip, AudioPlayback>() {
                            @Override
                            public AudioPlayback apply(SoundClip soundClip) {
                                return new KeepSoundBankAudioPlayback(keepSoundBank);
                            }
                        });
                engine.getAudioProcess().add(audioSource);
                engine.getAudioProcess().add(music);

                InstanceAudioListener.INSTANCE.setPosition(new SimpleVector3f(0, 0, -10));

                SoundHandle birdsHandle = music.play(birds);
                SoundHandle lightRainAmbientHandle = music.play(lightRainAmbient);
                SoundHandle edenMusicHandle = music.play(edenMusic);
                SoundHandle monoHandle = audioSource.play(mono);

                birdsHandle.setLooping(true);

                while (!monoHandle.isDone()) {
                    TimingUtil.waitMillis(1);
                    t[0]+=1E-3f;
                }

                birdsHandle.stop();
                lightRainAmbientHandle.stop();
                edenMusicHandle.stop();
                monoHandle.stop();

                System.out.println("done");
                engine.stop();
            }
        });
        thread.setDaemon(false);
        thread.start();

        TimingUtil.waitMillis(10_000);


    }


}


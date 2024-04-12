package fr.radi3nt.openal;

import fr.radi3nt.file.impl.ResourceFile;
import fr.radi3nt.maths.components.vectors.implementations.SimpleVector3f;
import fr.radi3nt.openal.engine.clip.FileSoundClip;
import fr.radi3nt.openal.engine.clip.SoundClip;
import fr.radi3nt.openal.engine.source.attenuation.manual.ManualPositionalAudioAttenuation;
import fr.radi3nt.openal.engine.source.attenuation.manual.formula.InverseDistanceAttenuationFormula;
import fr.radi3nt.openal.engine.source.attenuation.manual.listener.AudioListener;
import fr.radi3nt.openal.engine.source.attenuation.manual.listener.InstanceAudioListener;
import fr.radi3nt.openal.engine.source.attenuation.manual.spatialization.QuaternionSpatializationFormula;
import fr.radi3nt.openal.engine.source.handle.SoundHandle;
import fr.radi3nt.openal.engine.source.playback.StreamingSoundBankAudioPlayback;
import fr.radi3nt.openal.engine.source.sources.SoloAudioSource;
import fr.radi3nt.openal.high.AudioEngine;
import fr.radi3nt.openal.high.bank.stb.StbLoadInSoundBank;
import fr.radi3nt.openal.high.bank.streaming.StbStreamingSoundBank;
import fr.radi3nt.timing.TimingUtil;

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


        SoundClip clip1 = new FileSoundClip(new ResourceFile("/assets/sounds/imported/birds.ogg"));
        SoundClip clip2 = new FileSoundClip(new ResourceFile("/assets/sounds/imported/light_rain_ambient.ogg"));
        SoundClip clip27 = new FileSoundClip(new ResourceFile("/assets/sounds/27.ogg"));

        StbLoadInSoundBank bank = new StbLoadInSoundBank();
        StbStreamingSoundBank stbStreamingSoundBank = new StbStreamingSoundBank(bank);

        float[] t = new float[]{0};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final SoloAudioSource audioSource;

                AudioListener audioListener = InstanceAudioListener.INSTANCE;
                audioSource = new SoloAudioSource(new ManualPositionalAudioAttenuation(() -> {
                    return new SimpleVector3f(Math.cos(t[0]) * 10 * 0, 0, Math.sin(t[0]) * 10 * 0);
                }, () -> {
                    return new SimpleVector3f(-Math.sin(t[0]) * 5 * 0, 0, Math.cos(t[0]) * 5 * 0);
                }, audioListener, new InverseDistanceAttenuationFormula(2, 1f), new QuaternionSpatializationFormula(343, 1f)), new StreamingSoundBankAudioPlayback(stbStreamingSoundBank, 2048 * 8, 3));
                engine.getAudioProcess().add(audioSource);

                SoundHandle play = audioSource.play(clip1);

                play.setLooping(true);

                while (!play.isDone()) {
                    TimingUtil.waitMillis(1);
                }
                System.out.println("done");
                engine.stop();
            }
        });
        thread.setDaemon(false);
        thread.start();

        TimingUtil.waitMillis(10_000);


    }


}


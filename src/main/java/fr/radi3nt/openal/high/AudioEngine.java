package fr.radi3nt.openal.high;

import fr.radi3nt.openal.high.task.AudioProcess;
import fr.radi3nt.timing.TimingUtil;

public class AudioEngine {

    private final AudioContext audioContext = new AudioContext();
    private final AudioProcess audioProcess = new AudioProcess();
    private boolean stopped;

    public void prepare() {
        audioContext.start();
        audioContext.startAl();
    }

    public void start() {
        while (!stopped) {
            audioProcess.update();
            TimingUtil.waitMillis(1);
        }

        audioContext.stop();
    }

    public void stop() {
        stopped = true;
    }

    public AudioProcess getAudioProcess() {
        return audioProcess;
    }
}

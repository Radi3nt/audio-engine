package fr.radi3nt.openal.engine.clip.stb;

import fr.radi3nt.file.files.ReadableFile;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class StbSound {

    public final int channels;
    public final int sampleRate;
    public final int sampleLength;
    private final long memoryHandle;

    @SuppressWarnings("All")
    private final ByteBuffer encoded; //This needs to be kept in memory so that vorbis can use it

    public StbSound(long memoryHandle, int channels, int sampleRate, int sampleLength, ByteBuffer encoded) {
        this.memoryHandle = memoryHandle;
        this.channels = channels;
        this.sampleRate = sampleRate;
        this.sampleLength = sampleLength;
        this.encoded = encoded;
    }

    public static StbSound from(ReadableFile readableFile) throws IOException {
        ByteBuffer encoded = ioResourceToByteBuffer(readableFile);

        long handle;
        int channels;
        int sampleRate;

        try (MemoryStack stack = stackPush()) {
            IntBuffer error = stack.mallocInt(1);
            handle = stb_vorbis_open_memory(encoded, error, null);

            if (handle == NULL) {
                throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
            }

            STBVorbisInfo info = STBVorbisInfo.malloc(stack);
            stb_vorbis_get_info(handle, info);
            channels = info.channels();
            sampleRate = info.sample_rate();
        }

        int samplesLength = stb_vorbis_stream_length_in_samples(handle);
        return new StbSound(handle, channels, sampleRate, samplesLength, encoded);
    }

    public static ByteBuffer ioResourceToByteBuffer(ReadableFile resource) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[0x4000];

        try (InputStream is = resource.getInputStream()) {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        }

        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(buffer.size());
        byteBuffer.put(buffer.toByteArray());
        byteBuffer.flip();
        return byteBuffer;
    }

    public synchronized int getSamples(ShortBuffer pcm) {
        return stb_vorbis_get_samples_short_interleaved(memoryHandle, channels, pcm);
    }

    public synchronized void seek(int sampleIndex) {
        int currentError;
        try (MemoryStack stack = stackPush()) {
            IntBuffer error = stack.mallocInt(1);
            stb_vorbis_seek(memoryHandle, sampleIndex);
            currentError = error.get(0);
        }
        if (currentError==VORBIS__no_error || currentError==VORBIS_need_more_data)
            return;
        System.out.println("Vorbis error: " + currentError);
    }

    public synchronized void delete() {
        stb_vorbis_close(memoryHandle);
    }


}

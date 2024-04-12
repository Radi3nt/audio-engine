package fr.radi3nt.openal.engine.clip;

import fr.radi3nt.file.impl.ResourceFile;

import java.util.Objects;

public class FileSoundClip implements SoundClip {

    public final ResourceFile resourceFile;

    public FileSoundClip(ResourceFile resourceFile) {
        this.resourceFile = resourceFile;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileSoundClip)) return false;

        FileSoundClip that = (FileSoundClip) o;
        return Objects.equals(resourceFile, that.resourceFile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resourceFile);
    }
}

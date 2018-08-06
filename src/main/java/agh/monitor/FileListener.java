package agh.monitor;

import java.io.File;
import java.io.IOException;

public interface FileListener {

    /**
     * Invoked when a file changes.
     *
     * @param fileName
     *            name of changed file.
     */
    public void fileChanged(File fileName) throws IOException;
}

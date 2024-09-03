package com.spectra.asr.businessobject.watch;

import java.io.IOException;
import java.nio.file.Path;

public interface DirectoryWatcher {
	void watch(Path dirPath) throws IOException, InterruptedException;
	boolean handleEvent(Path filePath);
}

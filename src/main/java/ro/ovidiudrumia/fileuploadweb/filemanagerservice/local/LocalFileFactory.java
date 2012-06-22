package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import org.springframework.stereotype.Component;

import ro.ovidiudrumia.fileuploadweb.model.File;
import ro.ovidiudrumia.fileuploadweb.model.FileFactory;

/**
 * {@link FileFactory} implementation for {@link File}
 * 
 * @author ovidiu
 *
 */
@Component
public final class LocalFileFactory implements FileFactory<LocalFile> {

	/**
	 * Builds a new {@link #File} instance. 
	 * See {@link FileFactory#newInstance(String, String, boolean)}.
	 */
	public LocalFile newInstance(String name, String path, boolean isDirectory) {
		return LocalFile.newInstance().addName(name).addPath(path)
				.setDirectory(isDirectory).build();
	}

	/**
	 * Builds a new {@link File} instance. 
	 * See {@link FileFactory#newInstance()}.
	 */
	public LocalFile newInstance() {
		return LocalFile.newInstance();
	}

}

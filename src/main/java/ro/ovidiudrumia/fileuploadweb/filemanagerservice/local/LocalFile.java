package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import java.io.File;

/**
 * A java.io.File wrapper. Used to create a local system file structure.
 * 
 * @see ro.ovidiudrumia.fileuploadweb.model.File
 * 
 * @author ovidiu
 * 
 */
public final class LocalFile implements ro.ovidiudrumia.fileuploadweb.model.File {

	private String name;
	private String path;
	private File file;
	private boolean directory;

	private LocalFile() {
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#getName()}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#getPath()}
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#getFile()}
	 * @param currentPath 
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Implements
	 * {@link ro.ovidiudrumia.fileuploadweb.model.File#addName(String)}
	 */
	public LocalFile addName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Implements
	 * {@link ro.ovidiudrumia.fileuploadweb.model.File#addPath(String)}
	 */
	public LocalFile addPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Builds the current <code>LocalFile</code> instance.
	 * 
	 * @throws IllegalArgumentException
	 *             if name is not set
	 */
	public LocalFile build() throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("File name can't be null!");
		}
		if(path == null) {
			file = new File(File.separator + name);
			return this;
		}
		file = new File(path + File.separator + name);
		return this;
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#newInstance()}
	 */
	static LocalFile newInstance() {
		return new LocalFile();
	}

	/**
	 * Implements
	 * {@link ro.ovidiudrumia.fileuploadweb.model.File#setDirectory(boolean)}
	 */
	public LocalFile setDirectory(boolean directory) {
		this.directory = directory;
		return this;
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#isDirectory()}
	 */
	public boolean isDirectory() {
		return directory;
	}

	/**
	 * Implements {@link ro.ovidiudrumia.fileuploadweb.model.File#size()}
	 */
	@Override
	public long getSize() {
		if(null == file) {
			return 0;
		}
		return file.length();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getCompletePath() {
		return path + File.separator + name;
	}
	
	
}

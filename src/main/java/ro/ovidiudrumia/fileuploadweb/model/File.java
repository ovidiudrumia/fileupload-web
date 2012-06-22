package ro.ovidiudrumia.fileuploadweb.model;

/**
 * A <code>File</code> is the building block of the file management system. It
 * must have a name. Can have a path. If no path is specified the file should be
 * created in the root directory. Also any given file can be a directory. The
 * default value for isDirectory should be false.
 * 
 * Files should be created either by using the builder pattern or the adequate
 * {@link FileFactory}.
 * 
 * @author ovidiu
 * 
 */
public interface File {

	/**
	 * Adds a name to the <code>File</code>.
	 * 
	 * @param name
	 *            - the file name
	 * @return current <code>File</code> instance
	 */
	File addName(String name);

	/**
	 * Adds a path to the <code>File</code>.
	 * 
	 * @param path
	 *            - the path in which the file will be created
	 * @return current <code>File</code> instance
	 */
	File addPath(String path);

	/**
	 * Sets the directory flag. True means the file is a directory. False
	 * otherwise.
	 * 
	 * @param directory
	 *            - directory flag
	 * @return cur
	 * rent <code>File</code> instance
	 */
	File setDirectory(boolean directory);

	/**
	 * @return the instance's filename
	 */
	String getName();

	/**
	 * @return the parent directory's relative path
	 */
	String getPath();

	/**
	 * @return the instance's directory flag
	 */
	boolean isDirectory();

	/**
	 * Builds the current <code>File</code> instance.
	 * 
	 * @return current <code>File</code> instance
	 * @throws Exception
	 */
	File build() throws Exception;
	
	/**
	 * Returns the document dimension.
	 * @return
	 */
	long getSize();
}

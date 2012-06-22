package ro.ovidiudrumia.fileuploadweb.model;

/**
 * Used for building {@link File} instances.
 * 
 * @author ovidiu
 * 
 */
public interface FileFactory<T extends File> {
	/**
	 * Returns a new {@link File} instance which is already built.
	 * 
	 * @param name
	 *            - the <code>File</code>'s name
	 * @param path
	 *            - the <code>File</code>'s path
	 * @param isDirectory
	 * 			  - the <code>File</code>'s directory flag
	 * @return {@link File}
	 */
	T newInstance(String name, String path, boolean isDirectory);

	/**
	 * Returns a new {@link File} instance. The instance is not already built
	 * due to lack of parameters. 
	 * 
	 * @see File
	 * 
	 * @return {@link File}
	 */
	T newInstance();
}

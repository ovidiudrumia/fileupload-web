package ro.ovidiudrumia.fileuploadweb.filemanagerservice;

import java.io.IOException;
import java.io.InputStream;

import ro.ovidiudrumia.fileuploadweb.model.File;

/**
 * Handles the file system administration. Provides basic file manipulation
 * methods.
 * 
 * @author ovidiu
 * 
 */
public interface FileManager<T extends File> {

	/**
	 * Creates a file using the name and path of the {@link File} instance.
	 * 
	 * @param file
	 *            - {@link File} which contains name and path
	 * @param input
	 *            - the stream containing the content
	 * @throws Exception if the {@link File} cannot be created
	 */
	void createFile(T file, InputStream input) throws Exception;

	/**
	 * Renames the given file.
	 * 
	 * @param file
	 *            - {@link File} to be renamed
	 * @param newFile
	 *            - {@link File} destination
	 */
	void renameFile(T file, T newFile) throws Exception;

	/**
	 * Deletes a file.
	 * 
	 * @param file
	 *            - {@link File} file to be deleted
	 * @throws Exception 
	 */
	void deleteFile(T file) throws Exception;

	/**
	 * Gets the content of a specific {@link File}. The {@link File} should not
	 * be a directory.
	 * 
	 * @param file
	 *            - {@link File} which should be returned
	 * @return - the {@link File}'s contents
	 * @throws IOException
	 *             - if the {@link File} parameter is a directory or if the
	 *             {@link File} could not be opened
	 */
	InputStream getFileInputStream(T file) throws Exception;

}

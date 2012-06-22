package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import ro.ovidiudrumia.fileuploadweb.filemanagerservice.FileManager;

/**
 * Provides a {@link FileManager} implementation based on the
 * <code>java.io</code> package.
 * 
 * @author ovidiu
 * 
 */
@Component
public final class LocalFileManager implements FileManager<LocalFile> {

	/**
	 * Creates a local disk file based on the {@link LocalFile} instance and
	 * input.
	 * 
	 * @param file
	 *            - {@link LocalFile} which gives the name and destination
	 * @param input
	 *            - provides the {@link LocalFile}'s content
	 * @throws Exception
	 *             if the {@link java.io.File} cannot be created
	 */
	public void createFile(LocalFile file, InputStream input) throws Exception {

		copyFile(file, input);
	}

	/**
	 * Renames a file which exists on the file system.
	 * 
	 * @param file
	 *            - {@link LocalFile} which should be renamed
	 * @param newFile
	 *            - {@link LocalFile} into which it should be renamed
	 * @throws Exception
	 *             if {@link java.io.File} cannot be renamed
	 */
	public void renameFile(LocalFile file, LocalFile newFile) throws Exception {

		changeFileName(file, newFile);
	}

	/**
	 * Removes the file from the disk.
	 * 
	 * @param file - {@link LocalFile} to be deleted
	 * @throws Exception 
	 */
	public void deleteFile(LocalFile file) throws Exception {
		removeFile(file);
	}

	/**
	 * Returns a chosen's {@link LocalFile} content.
	 * 
	 * @param file - the {@link LocalFile} which should be returned
	 */
	public InputStream getFileInputStream(LocalFile file)
			throws FileNotFoundException {

		return getContent(file);
	}

	private void copyFile(LocalFile file, InputStream in) throws IOException {
		OutputStream out = new FileOutputStream(file.getFile());
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = in.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		in.close();
		out.flush();
		out.close();
	}

	private void changeFileName(LocalFile file, LocalFile newFile) {
		file.getFile().renameTo(newFile.getFile());
	}

	private void removeFile(LocalFile file) throws Exception {
		if(file.isDirectory()) {
			FileUtils.deleteDirectory(file.getFile());
		} else {
			file.getFile().delete();
		}
	}

	private InputStream getContent(LocalFile file) throws FileNotFoundException {
		return new FileInputStream(file.getFile());
	}
}

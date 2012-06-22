package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/fileupload-test-context.xml" })
public class LocalFileManagerTest {

	@Autowired
	private LocalFileManager localFileManager;
	@Autowired
	private LocalFileFactory localFileFactory;

	private String fileName = "Arboreal_ballet_by_Bob_Farrell.jpg";
	private String newFileName = "test";
	private String filePathPlayground = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "playground";
	private String filePathResourceFiles = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "resourcefiles";
	private String filePathRoot = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources";

	@After
	public void clean() {
		new File(filePathRoot + File.separator + fileName).delete();
		new File(filePathPlayground + File.separator + fileName).delete();
		new File(filePathPlayground + File.separator + newFileName).delete();
	}

	@Test
	public void testCreateFileInRoot() throws Exception {
		LocalFile file = localFileFactory
				.newInstance(fileName, filePathRoot, false);
		File source = new File(filePathResourceFiles + File.separator + fileName);
		localFileManager.createFile(file, new FileInputStream(source));
		assertEquals(source.length(), file.getFile().length());
	}

	@Test
	public void testCreateFile() throws Exception {

		LocalFile file = localFileFactory
				.newInstance(fileName, filePathPlayground, false);
		File source = new File(filePathResourceFiles + File.separator + fileName);
		localFileManager.createFile(file, new FileInputStream(source));
		assertEquals(source.length(), file.getFile().length());
	}
	
	@Test
	public void testRenameFile() throws Exception {
		
		LocalFile file = localFileFactory
				.newInstance(fileName, filePathPlayground, false);
		File source = new File(filePathResourceFiles + File.separator + fileName);
		localFileManager.createFile(file, new FileInputStream(source));
		LocalFile fileDestination = localFileFactory
				.newInstance(newFileName, filePathPlayground, false);
		localFileManager.renameFile(file, fileDestination);
		assertFalse(file.getFile().exists());
		assertTrue(fileDestination.getFile().exists());
	}
	
	@Test
	public void testDeleteFile() throws Exception {
		
		LocalFile file = localFileFactory
				.newInstance(fileName, filePathPlayground, false);
		File source = new File(filePathResourceFiles + File.separator + fileName);
		localFileManager.createFile(file, new FileInputStream(source));
		localFileManager.deleteFile(file);
		assertFalse(file.getFile().exists());
	}
}

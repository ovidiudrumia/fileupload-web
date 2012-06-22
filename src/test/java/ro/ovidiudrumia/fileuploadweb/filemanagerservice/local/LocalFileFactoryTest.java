package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/fileupload-test-context.xml"})
public class LocalFileFactoryTest {
	
	@Autowired private LocalFileFactory localFileFactory;
	
	@Test
	public void testCreateLocalFileWithParameters() throws Exception {
		String name = "testFile";
		String path = "testPath";
		boolean isDirectory = true;
		LocalFile file = localFileFactory.newInstance(name, path, isDirectory);
		
		assertEquals(name, file.getName());
		assertEquals(path, file.getPath());
		assertEquals(isDirectory, file.isDirectory());
		assertNotNull(file.getFile());
	}
	
	@Test
	public void testCreateLocalFileWithoutParameters() throws Exception {
		LocalFile file = localFileFactory.newInstance();
		
		assertEquals(null, file.getName());
		assertEquals(null, file.getPath());
		assertEquals(false, file.isDirectory());
		assertEquals(null, file.getFile());
	}
}

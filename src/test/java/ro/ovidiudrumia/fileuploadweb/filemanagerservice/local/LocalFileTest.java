package ro.ovidiudrumia.fileuploadweb.filemanagerservice.local;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.ovidiudrumia.fileuploadweb.model.FileFactory;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/fileupload-test-context.xml"})
public class LocalFileTest {
	
	@Autowired
	private FileFactory<LocalFile> localFileFactory;
	
	@Test
	public void testFileBuidWithName() throws Exception {
		LocalFile file = localFileFactory.newInstance().addName("test").build();
		assertNotNull(file);
	}
	
	@Test
	@ExpectedException(IllegalArgumentException.class)
	public void testFileBuidWithoutName() throws Exception {
		localFileFactory.newInstance().build();
	}
}

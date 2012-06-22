package ro.ovidiudrumia.fileuploadweb.service;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

public interface FileService {
	
	DefaultTreeNode initFileStructure() throws Exception;

	void upload(String fileName, InputStream inputstream, TreeNode selectedNode) throws Exception;

	InputStream getFile(TreeNode selectedNode) throws Exception;

	void delete(TreeNode selectedNode) throws Exception;

	void makeNewDir(TreeNode selectedNode, String directoryName) throws Exception;

	void rename(TreeNode selectedNode, String newName) throws Exception;

	StreamedContent getStreamedContent(TreeNode selectedNode) throws Exception;
}

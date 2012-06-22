package ro.ovidiudrumia.fileuploadweb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ro.ovidiudrumia.fileuploadweb.backingbean.DefaultTreeNodeWrapper;
import ro.ovidiudrumia.fileuploadweb.filemanagerservice.local.LocalFile;
import ro.ovidiudrumia.fileuploadweb.filemanagerservice.local.LocalFileFactory;
import ro.ovidiudrumia.fileuploadweb.filemanagerservice.local.LocalFileManager;

@Service
public class LocalFileService implements FileService {

	@Autowired
	private LocalFileFactory localFileFactory;

	@Autowired
	private LocalFileManager localFileManager;

	private String defaultPath = System.getProperty("user.dir")
			+ File.separator + "localstorage";
	private String currentPath;

	DefaultTreeNodeWrapper root = null;

	@Override
	public DefaultTreeNode initFileStructure() throws Exception {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return root;
		}
		if (root != null) {
			return root;
		}
		root = initRoot();
		initCurrentPath();
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.exists()) {
			currentPathFile.mkdirs();
		}

		createFileMapping(currentPathFile.listFiles(), root, currentPath);

		return root;
	}

	private void initCurrentPath() {
		currentPath = defaultPath
				+ File.separator
				+ SecurityContextHolder.getContext().getAuthentication()
						.getName();
		root.setFile(localFileFactory.newInstance(SecurityContextHolder
				.getContext().getAuthentication().getName(), defaultPath, true));
	}

	private DefaultTreeNodeWrapper initRoot() {
		return new DefaultTreeNodeWrapper("root", null);
	}

	@Override
	public void upload(String fileName, InputStream inputstream,
			TreeNode selectedNode) throws Exception {
		String path = getCurrentSelectedPath(selectedNode);
		LocalFile file = localFileFactory.newInstance(fileName, path, false);
		localFileManager.createFile(file, inputstream);
		new DefaultTreeNodeWrapper(file, root).setFile(file);
	}

	private String getPathForFile(TreeNode selectedNode, String file) {
		return getCurrentSelectedPath(selectedNode) + File.separator + file;
	}

	private String getCurrentSelectedPath(TreeNode selectedNode) {
		if (selectedNode == null) {
			selectedNode = root;
		}
		return ((LocalFile) ((DefaultTreeNodeWrapper) selectedNode).getFile())
				.getCompletePath();
	}

	@Override
	public InputStream getFile(TreeNode selectedNode)
			throws FileNotFoundException {
		return new FileInputStream(new File(
				currentPath
						+ File.separator
						+ ((LocalFile) ((DefaultTreeNodeWrapper) selectedNode)
								.getFile()).getName()));
	}

	@Override
	public void delete(TreeNode selectedNode) throws Exception {
		if (null == selectedNode) {
			return;
		}
		LocalFile file = (LocalFile) ((DefaultTreeNodeWrapper) selectedNode)
				.getFile();
		localFileManager
				.deleteFile(file.addPath(currentPath).build());
	}

	@Override
	public void makeNewDir(TreeNode selectedNode, String directoryName)
			throws IOException {
		if (selectedNode == null) {
			selectedNode = root;
		}
		String path = getPathForFile(selectedNode, directoryName);
		LocalFile file = localFileFactory
				.newInstance(directoryName, path, true);
		file.getFile().mkdirs();
		new DefaultTreeNodeWrapper(localFileFactory.newInstance(file.getName(),
				getRelativePath(currentPath, file.getFile()),
				file.isDirectory()), selectedNode).setFile(file);
	}

	@Override
	public void rename(TreeNode selectedNode, String newName) throws Exception {
		if (null == selectedNode) {
			return;
		}
		LocalFile file = (LocalFile) selectedNode.getData();
		LocalFile newFile = localFileFactory.newInstance(newName,
				file.getPath(), file.isDirectory());
		localFileManager.renameFile(file, newFile);
		((DefaultTreeNodeWrapper) selectedNode).setFile(newFile).setData(
				newFile);
	}

	private void createFileMapping(File[] files, DefaultTreeNode root,
			String relativePath) throws IOException, InterruptedException {
		for (File file : files) {
			LocalFile localFile = localFileFactory.newInstance(file.getName(),
					getRelativePath(relativePath, file), file.isDirectory());
			DefaultTreeNodeWrapper currentNode = null;
			currentNode = new DefaultTreeNodeWrapper(localFile, root)
					.setFile(localFile);
			if (file.isDirectory()) {
				System.out.println("Directory: " + file.getName());
				createFileMapping(file.listFiles(), currentNode, relativePath);
			} else {
				System.out.println("File: " + file.getName());
			}
		}
	}

	private String getRelativePath(String path, File file) throws IOException {
		return file.getCanonicalPath().replace(path, "");
	}

	@Override
	public StreamedContent getStreamedContent(TreeNode selectedNode)
			throws FileNotFoundException {
		if (null == selectedNode) {
			selectedNode = root;
		}
		return new DefaultStreamedContent(getFile(selectedNode),
				"application/octect-stream",
				((LocalFile) ((DefaultTreeNodeWrapper) selectedNode).getFile())
						.getName());
	}

}

package ro.ovidiudrumia.fileuploadweb.backingbean;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import ro.ovidiudrumia.fileuploadweb.filemanagerservice.local.LocalFile;
import ro.ovidiudrumia.fileuploadweb.model.File;

public final class DefaultTreeNodeWrapper extends DefaultTreeNode {

	private static final long serialVersionUID = 1L;
	
	private File file;

	public DefaultTreeNodeWrapper(LocalFile newInstance, TreeNode selectedNode) {
		super(newInstance, selectedNode);
	}

	public DefaultTreeNodeWrapper(String string, TreeNode selectedNode) {
		super(string, selectedNode);
	}

	public File getFile() {
		return file;
	}

	public DefaultTreeNodeWrapper setFile(File file) {
		this.file = file;
		return this;
	}
}

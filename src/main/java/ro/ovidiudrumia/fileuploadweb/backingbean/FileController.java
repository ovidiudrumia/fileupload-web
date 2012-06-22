package ro.ovidiudrumia.fileuploadweb.backingbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ro.ovidiudrumia.fileuploadweb.service.FileService;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FileController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private FileService fileService;
	
	private String directoryName;
	
	private String newName;

	private TreeNode selectedNode;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public TreeNode getRoot() throws Exception {
		return fileService.initFileStructure();
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void deleteNode() throws Exception {
		if(selectedNode == null) {
			return;
		}
		selectedNode.getChildren().clear();
		selectedNode.getParent().getChildren().remove(selectedNode);
		selectedNode.setParent(null);

		fileService.delete(selectedNode);

		selectedNode = null;
	}
	
	public void onNodeSelect(NodeSelectEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());  
          
        FacesContext.getCurrentInstance().addMessage(null, message);  
        
        selectedNode = event.getTreeNode();
    }  

	public void upload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Success! ", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		try {
			fileService.upload(event.getFile().getFileName(), event
					.getFile().getInputstream(), selectedNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StreamedContent getDownloadSelectedFile() throws Exception {
		return fileService.getStreamedContent(selectedNode);
	}
	
	public void makeNewDir() throws Exception {
		fileService.makeNewDir(selectedNode, directoryName);
	}
	
	public void rename() throws Exception {
		fileService.rename(selectedNode, newName);
	}
}
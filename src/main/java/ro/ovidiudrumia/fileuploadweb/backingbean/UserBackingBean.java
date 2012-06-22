package ro.ovidiudrumia.fileuploadweb.backingbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import ro.ovidiudrumia.fileuploadweb.model.User;
import ro.ovidiudrumia.fileuploadweb.service.UserService;

@Component("userBkB")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class UserBackingBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static String SUCCESS = "success";
	private static String FAILURE = "failure";

	@Autowired
	private UserService userService;
	
	private List<User> userList;

	private int id;
	private String login;
	private String name;
	private String password;

	public void addUser() {
		try {
			userService.addUser(new User(login, password, null));
			authenticate();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void authenticateUser() {
		authenticate();
	}
	
	private void authenticate() {
		
		boolean loggedIn = false;
		try {
			loggedIn = userService.authenticate(login, password);
			
		} catch (DataAccessException e) {
			FacesMessage message = new FacesMessage("Cannot access the database!");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			FacesContext.getCurrentInstance().addMessage("Authentication", message);
			return;
		} catch (BadCredentialsException e) {
			FacesMessage message = new FacesMessage("Username and password do not match!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage("Authentication", message);
			return;
		} 
		RequestContext.getCurrentInstance().addCallbackParam("loggedIn", loggedIn);
	}

	public void reset() {
		this.name = "";
		this.login = "";
		this.password = "";
	}

	public void deleteAll() {
		userService.deleteAllUsers();
	}

	public List<User> getUserList() {
		userList = userService.findAllUsers();
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void validateCanRegisterLogin(FacesContext context, UIComponent toValidate,
			Object value) {
		String login = (String)value;
		if(userService.findByLogin(login) == null) {
			return;
		}
		((UIInput)toValidate).setValid(false);
		FacesMessage message = new FacesMessage("User already exists!");
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(message);
	}
	
	public void validateLoginExists(FacesContext context, UIComponent toValidate,
			Object value) {
		String login = (String)value;
		if(userService.findByLogin(login) != null) {
			return;
		}
		((UIInput)toValidate).setValid(false);
		FacesMessage message = new FacesMessage("User does not exist!");
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(message);
	}

	public boolean isAuthenticated() {
		return userService.isAuthenticated();
	}
	
	public void logout() {
		try{
			userService.logout();
		} catch (Exception e) {
		}
	}
	
	public String getUser() {
		if(null == SecurityContextHolder.getContext().getAuthentication()) {
			return null;
		}
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}

package ro.ovidiudrumia.fileuploadweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ro.ovidiudrumia.fileuploadweb.model.User;
import ro.ovidiudrumia.fileuploadweb.repository.UserRepository;

@Service
public final class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void addUser(User person) {
		userRepository.save(person);
	}

	public List<User> findAllUsers() {
		List<User> users = new ArrayList<User>();
		for (User u : userRepository.findAll()) {
			users.add(u);
		}
		return users;
	}

	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public boolean isAuthenticated() {
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			return false;
		}
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	public boolean authenticate(String login, String password) {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (null == authentication) {
			Authentication request = new UsernamePasswordAuthenticationToken(
					login, password);
			authentication = authenticationManager.authenticate(request);
		}
		context.setAuthentication(authentication);
		return authentication.isAuthenticated();
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}	
}

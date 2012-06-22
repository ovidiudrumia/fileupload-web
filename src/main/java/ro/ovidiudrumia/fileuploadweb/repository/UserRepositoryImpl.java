package ro.ovidiudrumia.fileuploadweb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import ro.ovidiudrumia.fileuploadweb.model.UserDetailsAdapter;
import ro.ovidiudrumia.fileuploadweb.model.User;
import ro.ovidiudrumia.fileuploadweb.model.UserRoles;
import ro.ovidiudrumia.fileuploadweb.service.FileUploadUserDetailsService;

/**
 * @author Ovidiu Drumia
 */
public class UserRepositoryImpl implements FileUploadUserDetailsService {

    @Autowired
    private Neo4jOperations template;

    @Override
    public UserDetailsAdapter loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException {
        final User user = findByLogin(login);
        if (user==null) throw new UsernameNotFoundException("Username not found: " + login);
        return new UserDetailsAdapter(user);
    }

    private User findByLogin(String login) {
        return template.lookup(User.class,"login",login).to(User.class).single();
    }

    @Override
    public User getUserFromSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication == null) {
        	return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsAdapter) {
            UserDetailsAdapter userDetails = (UserDetailsAdapter) principal;
            return userDetails.getUser();
        }
        return null;
    }

    @Override
    @Transactional
    public User register(String login, String name, String password) {
        User found = findByLogin(login);
        if (found!=null) throw new RuntimeException("Login already taken: "+login);
        if (name==null || name.isEmpty()) throw new RuntimeException("No name provided.");
        if (password==null || password.isEmpty()) throw new RuntimeException("No password provided.");
        User user=template.save(new User(login,name,password, UserRoles.ROLE_USER));
        setUserInSession(user);
        return user;
    }
    
    private void setUserInSession(User user) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsAdapter userDetails = new UserDetailsAdapter(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(),userDetails.getAuthorities());
        context.setAuthentication(authentication);
    }

}

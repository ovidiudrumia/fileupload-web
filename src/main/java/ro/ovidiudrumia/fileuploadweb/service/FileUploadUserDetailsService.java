package ro.ovidiudrumia.fileuploadweb.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import ro.ovidiudrumia.fileuploadweb.model.User;

/**
 * @author Ovidiu Drumia
 */
public interface FileUploadUserDetailsService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException, DataAccessException;

    User getUserFromSession();

    @Transactional
    User register(String login, String name, String password);

}

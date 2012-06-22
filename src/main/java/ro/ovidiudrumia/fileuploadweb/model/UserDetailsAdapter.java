package ro.ovidiudrumia.fileuploadweb.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ro.ovidiudrumia.fileuploadweb.model.User;
import ro.ovidiudrumia.fileuploadweb.model.UserRoles;

/**
* @author ovidiu
*/
public final class UserDetailsAdapter implements UserDetails {
	private static final long serialVersionUID = 1L;
	private final User user;

    public UserDetailsAdapter(User user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        UserRoles role = user.getRole();
        if (role == null) return Collections.emptyList();
        return Arrays.<GrantedAuthority>asList(role);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }
}

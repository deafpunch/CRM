package pl.deafpunch.crm.service;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.deafpunch.crm.controller.MainController;
import pl.deafpunch.crm.entity.Role;
import pl.deafpunch.crm.entity.User;


public class SpringDataUserDetailsService implements UserDetailsService {

	private UserService userService;

	@Autowired
	public void setUserRepository(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	MainController mainController;
	
	@Autowired
	HttpSession sess;

	/**
	 * Standard Spring Security method to check for user roles.
	 * Method is equipped with extra mechanism to check if user is deactivated. If so, it is moving to login page.
	 * Also adding to session both = logged in user model and username as separated value.
	 */
	
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}else if (user.getEnabled() == false) {
			mainController.getLandingPage();
		}else {
			
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Role role : user.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}			
			mainController.setLoggedInUserIntoSession(sess, user);
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					grantedAuthorities);
		}
		return null;
	}
}

package pl.amelco.crm.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.amelco.crm.controller.MainController;
import pl.amelco.crm.entity.Role;
import pl.amelco.crm.entity.User;


public class SpringDataUserDetailsService implements UserDetailsService {

	private UserService userService;

	@Autowired
	public void setUserRepository(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	MainController mainController;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userService.findByUsername(username);
//		System.out.println("USER DATA WHILE LOGGING IN: " + user);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}else if (user.getEnabled() == false) {
			mainController.getLandingPage();
		}else {
			
			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Role role : user.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					grantedAuthorities);
		}
		return null;
	}
}

package pl.amelco.crm.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.amelco.crm.entity.Role;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.RoleRepository;
import pl.amelco.crm.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	
	@Override
	public void updateUser(User user, HttpSession sess) {
		if (user.getId() == null) {
			User userToCompare = findByUsername(user.getUsername());
			if (user.getPassword()
					.equals(userToCompare.getPassword()) 
				|| user.getPassword().equals("Password")) {
				
				user.setPassword(userToCompare.getPassword());
				user.setId(userToCompare.getId());
				checkForRolesChange(user, userToCompare);
				userRepository.save(user);
			}else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setId(userToCompare.getId());
				checkForRolesChange(user, userToCompare);
				userRepository.save(user);
			}
		}else {
			User userToCompare = userRepository.findById(user.getId());
			if (user.getPassword()
					.equals(userToCompare.getPassword()) 
				|| user.getPassword().equals("Password")) {
				
				user.setPassword(userToCompare.getPassword());
				user.setId(userToCompare.getId());
				checkForRolesChange(user, userToCompare);
				userRepository.save(user);
			}else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setId(userToCompare.getId());
				checkForRolesChange(user, userToCompare);
				userRepository.save(user);
			}
		}
	}
	
	public User getLoggedInUser(Principal principal) {
		String name = principal.getName();
		User result = userRepository.findByUsername(name);
		return result;
	}
	
	public void checkForRolesChange(User user, User userToCompare){
		if (user.getRoles().equals(userToCompare.getRoles())) {
			user.setRoles(userToCompare.getRoles());
		}
	}

}
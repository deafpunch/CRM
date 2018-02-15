package pl.amelco.crm.service;

import java.util.Arrays;
import java.util.HashSet;

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

	/**
	 * Saving newly created user to database 
	 */
	
	@Override
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	/**
	 *  This method is comparing two object of the same ID (user from database and from the form). 
	 *  It checks for changes and if there are any - changes are saved instantly to database.
	 */
	
	@Override
	public void updateUser(User user, HttpSession sess) {
		Long sessionUserID = (Long) sess.getAttribute("userID");
		User userToSave;

		if (!user.getId()
				.equals(sessionUserID)) {
			userToSave = userRepository.findById(user.getId());
		} else {
			userToSave = userRepository.findById(sessionUserID);
		}

		if (!user.getEnabled().equals(userToSave.getEnabled())) {
			userToSave.setEnabled(user.getEnabled());
		} else if (!user.getUsername().equals(userToSave.getUsername())) {
			userToSave.setUsername(user.getUsername());
		}

		else if (!user.getPassword().equals("Password")) {
			String newPassword = passwordEncoder.encode(user.getPassword());
			userToSave.setPassword(newPassword);

		} else if (!user.getEmail().equals(userToSave.getEmail())) {
			userToSave.setEmail(user.getEmail());
		}
		else if (!user.getRoles().toString().equals(userToSave.getRoles().toString())) {
			userToSave.setRoles(user.getRoles());
		}
		userRepository.saveAndFlush(userToSave);
	}
}
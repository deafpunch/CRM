package pl.deafpunch.crm.service;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
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
			userToSave = userRepository.findOneById(user.getId());
		} else {
			userToSave = userRepository.findOneById(sessionUserID);
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

	@Override
	public User findById(Long id) {
		return userRepository.findOneById(id);
	}
}
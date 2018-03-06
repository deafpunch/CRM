package pl.deafpunch.crm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;	
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Passing user model filled with data 
	 * from database for user with ID (taken from session)
	 * 
	 * @param sess (used for download user ID from session)
	 * @param model (user model)
	 * @return view (form for editing user data)
	 */
	
	@GetMapping(path="/settings")
	public String userSettings(HttpSession sess, Model model) {
		User user = userRepository.findOneById((Long)sess.getAttribute("userID"));
		model.addAttribute("user", user);
		return "user/userSettings";
	}
	
	/**
	 * Saving changes for user data in database
	 * 
	 * @param user (model given by form)
	 * @param model (used for passing message again to the view)
	 * @param sess (taking user ID from session for filling model before saving in database)
	 * @return view (form for editing user data) with additional message
	 */
	
	@PostMapping(path="/settings")
	public String saveChangesForUser(User user, Model model, HttpSession sess) {		
		user.setId((Long)sess.getAttribute("userID"));
		model.addAttribute("message", new String("Saved!"));
		model.addAttribute("user", user);
		userServiceImpl.updateUser(user, sess);
		return "user/userSettings";
	}
}

package pl.amelco.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;	
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path="/settings")
	public String userSettings(HttpSession sess, Model model) {
		User user = (User) sess.getAttribute("loggedInUser");
		model.addAttribute("user", user);
		return "user/userSettings";
	}
	
	@PostMapping(path="/settings")
	public String saveChangesForUser(User user, Model model, HttpSession sess) {		
		model.addAttribute("message", new String("Saved!"));
		model.addAttribute("user", user);
		userServiceImpl.updateUser(user, sess);
		return "user/userSettings";
	}
	
	@GetMapping(path="/username")
	@ResponseBody
	public String showLoggedinUser(Principal principal, HttpSession sess) {
		User user = (User) sess.getAttribute("loggedInUser");
//		User user = userServiceImpl.findByUsername("manager");
		return "Logged in user: " + user.getUsername() + ", user id: " + user.getId() + ", Role:" + user.getRoles();
//		return principal.getName();
	}
	
	
}

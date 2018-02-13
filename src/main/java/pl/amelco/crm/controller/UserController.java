package pl.amelco.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
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

	@GetMapping(path="/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(path="/")
	public String getLandingPage(Principal principal) {
		try {
			String name = principal.getName();
			if (name != null) {
				return "dashboard";
			}
		} catch (NullPointerException e) {}
		return "redirect:/login";

	}
	
	@GetMapping(path="/logout")
	public String logout() { return "logout";}
	
	
	@GetMapping(path="/dashboard")
	public String index(Principal principal, HttpServletRequest request, Model model) {
		String name = principal.getName();
		User user = userServiceImpl.findByUsername(name);
		request.getSession().setAttribute("loggedInUser", user);
		model.addAttribute("loggedInUser", name);
		return "dashboard";
	}
	
	@GetMapping(path="/settings")
	public String userSettings(HttpSession sess, Model model) {
		User user = (User) sess.getAttribute("loggedInUser");
		model.addAttribute("user", user);
		return "user/userSettings";
	}
	
	@PostMapping(path="/settings")
	public String saveChangesForUser(User user, Model model) {		
		userServiceImpl.updateUser(user);
		model.addAttribute("message", new String("Saved!"));
		model.addAttribute("user", user);
		return "user/userSettings";
	}
	
	@GetMapping(path="/username")
	@ResponseBody
	public String loggedinuser(Principal principal, HttpSession sess) {
		User user = (User) sess.getAttribute("loggedInUser");
//		User user = userServiceImpl.findByUsername("manager");
		return "Logged in user: " + user.getUsername() + ", user id: " + user.getId();
//		return principal.getName();
	}
	
	
}

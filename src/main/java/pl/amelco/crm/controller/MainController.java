package pl.amelco.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
public class MainController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(path="/")
	public String getLandingPage() {
		return "login";

	}
	
	@GetMapping(path="/logout")
	public String logout() { return "logout";}
	
	
	@GetMapping(path="/dashboard")
	public String index(Principal principal, HttpServletRequest request, Model model, HttpSession sess) {
		if (sess.getAttribute("userID") == null) {
			User user = userServiceImpl.findByUsername(principal.getName());
			request.getSession().setAttribute("userID", user.getId());
//			System.out.println("User credentials from dashboard: " + user);
//			System.out.println("User id from session: " + sess.getAttribute("userID"));
			model.addAttribute("loggedInUser", user.getUsername());
			return "dashboard";
		}else {			
			Long idFromSession = (Long) sess.getAttribute("userID");
			User user = userRepository.findById(idFromSession);
//			System.out.println("User id from session: " + sess.getAttribute("userID"));
			model.addAttribute("loggedInUser", user.getUsername());
			return "dashboard";
		}
		
	}
}

package pl.amelco.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.amelco.crm.entity.User;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
public class MainController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@GetMapping(path="/")
	public String getLandingPage(Principal principal) {
		try {
			if (principal.getName() != null) {
				return "dashboard";
			}
		} catch (NullPointerException e) {}
		return "login";

	}
	
	@GetMapping(path="/logout")
	public String logout() { return "logout";}
	
	
	@GetMapping(path="/dashboard")
	public String index(Principal principal, HttpServletRequest request, Model model) {
		String name = principal.getName();
		User user = userServiceImpl.findByUsername(name);
		request.getSession().setAttribute("loggedInUser", user);
		model.addAttribute("loggedInUser", user.getUsername());
		model.addAttribute("loggedInUserRole", user.getRoles());
		return "dashboard";
	}
}

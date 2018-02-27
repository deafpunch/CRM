package pl.deafpunch.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
public class MainController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 *  @return Login page view
	 */
	
	@GetMapping(path="/login")
	public String getLandingPage() {
		return "login";	}

	/**
	 *  Shows main dashboard for logged in user
	 *  
	 * @param principal (getting authenticated user username)
	 * @param request (used to initialize session)
	 * @param model (used to pass to the view logged in user object)
	 * @param sess (used for setting logged in user ID inside session)
	 * @return dashboard view
	 */
	
	@GetMapping(path="/dashboard")
	public String index(Principal principal, HttpServletRequest request, Model model, HttpSession sess) {
		if (sess.getAttribute("userID") == null) {
			User user = userServiceImpl.findByUsername(principal.getName());
			request.getSession().setAttribute("userID", user.getId());
			model.addAttribute("loggedInUser", user.getUsername());
			return "dashboard";
		}else {			
			Long idFromSession = (Long) sess.getAttribute("userID");
			User user = userRepository.findById(idFromSession);
			model.addAttribute("loggedInUser", user.getUsername());
			return "dashboard";
		}
		
	}
}

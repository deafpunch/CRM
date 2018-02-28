package pl.deafpunch.crm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@GetMapping(path= {"/login", "/"})
	public String getLandingPage() {
		return "login";	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "login";
	}
	

	/**
	 *  Shows main dashboard for logged in user
	 *  
	 * @param principal (getting authenticated user username)
	 * @param request (used to initialize session)
	 * @param model (used to pass to the view logged in user object)
	 * @param sess (used for setting logged in user ID inside session)
	 * @return dashboard view
	 */
	
	@GetMapping(path="/index")
	public String index() {
		return "index";

	}
}

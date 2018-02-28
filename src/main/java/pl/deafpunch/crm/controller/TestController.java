package pl.deafpunch.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
@RequestMapping(path="/")
public class TestController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserServiceImpl userServiceImpl;

	
	
	/*
	 * 
	 * THIS CONTROLLER IS CREATED ONLY FOR TESTING PURPOSES
	 * 
	 * 
	 */
	
	
	@GetMapping(path="/username")
	@ResponseBody
	public String showLoggedinUser(Principal principal, HttpSession sess) {	
//		User user = userRepository.findById((Long)sess.getAttribute("userID"));
		User user = userServiceImpl.findById((long)2);
		return "User: " + user.getUsername() + ", user id: " + user.getId() + ", Role:" + user.getRoles();
	}
	
	@GetMapping(path="/file/upload")  
	public String uploadFile() {
		return "test/uploadFileTest";
	}
}

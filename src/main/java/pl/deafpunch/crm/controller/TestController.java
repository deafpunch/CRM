package pl.amelco.crm.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.FileModelService;

@Controller
@RequestMapping(path="/")
public class TestController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FileModelService fileModelService;
	
	
	/*
	 * 
	 * THIS CONTROLLER IS CREATED ONLY FOR TESTING PURPOSES
	 * 
	 * 
	 */
	
	
	@GetMapping(path="/username")
	@ResponseBody
	public String showLoggedinUser(Principal principal, HttpSession sess) {	
		User user = userRepository.findById((Long)sess.getAttribute("userID"));
		return "Logged in user: " + user.getUsername() + ", user id: " + user.getId() + ", Role:" + user.getRoles();
	}
	
	@GetMapping(path="/file/upload")  
	public String uploadFile() {
		return "test/uploadFileTest";
	}
}

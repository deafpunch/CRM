package pl.amelco.crm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.amelco.crm.entity.Role;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.RoleRepository;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping(path="/createuser")
	public String createUser(@ModelAttribute User user, Model model) {
		List<Role> roles = new ArrayList<>();
		roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
		return "user/addUser";
	}
	
	@PostMapping(path="/createuser")
	public String createUserPost(User user) {
		userServiceImpl.saveUser(user);
		return "user/successAdd";
	}
	
}

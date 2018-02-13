package pl.amelco.crm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.amelco.crm.entity.Role;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.RoleRepository;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
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
	
	@GetMapping(path="/allusers")
	public String allUsersList(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "admin/allUsers";
	}
	
	
	@GetMapping(path="/user/settings/{id}/details")
	public String manageUser(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id);
		model.addAttribute("user", user);
		List<Role> roles = new ArrayList<>();
		roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
		return "admin/userSettings";
	}
	
	@PostMapping(path="/user/settings/{id}/details")
	public String saveChangesForManagedUser(@PathVariable Long id, User user, Model model) {		
		userServiceImpl.updateUser(user);
		model.addAttribute("message", new String("Saved!"));
		model.addAttribute("user", user);
		return "admin/userSettings";
	}
	
}

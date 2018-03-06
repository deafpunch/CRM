package pl.deafpunch.crm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.deafpunch.crm.entity.Role;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.RoleRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * Forwarding to user creation form 
	 * @param user
	 * @param model
	 * @return Creation form
	 */
	
	@GetMapping(path="/createuser")
	public String createUser(@ModelAttribute User user, Model model) {
		List<Role> roles = new ArrayList<>();
		roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
		return "user/addUser";
	}
	
	/**
	 * Creating new user record in database with given filled form
	 * @param user (filled User type form)
	 * @param model (passing list of all users to the view)
	 * @return all registered users list view
	 */
	
	@PostMapping(path="/createuser")
	public String createUserPost(User user, Model model) {
		userServiceImpl.saveUser(user);
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		model.addAttribute("message", "User created!");
		return "admin/allUsers";
	}
	
	/**
	 * Passing to view list of all registered users
	 * @param model (with users list)
	 * @return all registered users list view
	 */
	
	@GetMapping(path="/allusers")
	public String allUsersList(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "admin/allUsers";
	}
	
	/**
	 * Showing details of specific user based on user ID given in @PathVariable
	 * @param id (User id)
	 * @param model (passing user record to the view)
	 * @return user details view
	 */
	
	@GetMapping(path="/user/settings/{id}/details")
	public String manageUser(@PathVariable Long id, Model model) {
		User user = userRepository.findOneById(id);
		model.addAttribute("user", user);
		putRolesListToModel(model);
		return "admin/userSettings";
	}
	
	/**
	 * Updating user record based on given User model from filled form
	 * @param id (ID of the user to update)
	 * @param user (object passed from the form)
	 * @param model (passing back updated user object and list of available user roles)
	 * @param sess (Parameter needed in userService method)
	 * @return same view with updated values
	 */
	
	@PostMapping(path="/user/settings/{id}/details")
	public String saveChangesForManagedUser(@PathVariable Long id, User user, Model model, HttpSession sess) {		
		user.setId(id);
		model.addAttribute("message", new String("Saved!"));
		model.addAttribute("user", user);
		putRolesListToModel(model);
		userServiceImpl.updateUser(user, sess);
		return "admin/userSettings";
	}
	
	/**
	 * Passing to the model list of all available user roles
	 * @param model
	 */
	
	public void putRolesListToModel(Model model){
		List<Role> roles = new ArrayList<>();
		roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
	}
	
}

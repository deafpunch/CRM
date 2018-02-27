package pl.deafpunch.crm.controller;

import java.security.Principal;
import java.util.Arrays;
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

import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.CompanySizeEnum;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.ClientRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
@RequestMapping(path="/dashboard")
public class ClientController {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Adding model attribute to GET.method filled with list of possible company sizes according to CompanysizeEnum values; 
	 * @param model
	 */
	
	@ModelAttribute
	public void getCompanySizeList(Model model) {
		List<CompanySizeEnum> sizes = Arrays.asList(CompanySizeEnum.values());
		model.addAttribute("sizes", sizes);
	}
	
	/**
	 * Returning view with for to add new client
	 * @param client (model based on Client entity)
	 * @param model
	 * @return View with form
	 */
	
	@GetMapping(path="/addclient")
	public String addNewClientGet(@ModelAttribute Client client, Model model) {
		getCompanySizeList(model);
		return "client/addClient";
	}
	
	/**
	 * 
	 * @param client
	 * @param sess
	 * @param principal
	 * @param model
	 * @return
	 */
	
	@PostMapping(path="/addclient")
	public String addNewClientPost(Client client, HttpSession sess, Principal principal, Model model) {
		User user = userServiceImpl.findByUsername(principal.getName());
		client.setUser(user);
		clientRepository.save(client);
		model.addAttribute("message", new String("New customer has been added successfully!"));
		List<Client> clients = clientRepository.findAll();
		model.addAttribute("clients", clients);
		return "client/clientsList";
	}
	
	@GetMapping(path="/clientslist")
	public String showClientsList(Model model) {
		List<Client> clients = clientRepository.findAll();
		model.addAttribute("clients", clients);
		return "client/clientsList";
	}
	
	@GetMapping(path="/client/{id}/details")
	public String showClientDetails(@PathVariable Long id, Model model) {
		Client client = new Client();
		client = clientRepository.findById(id);
		model.addAttribute("client", client);
		return "client/clientDetails";
	}
	
	@GetMapping(path="/client/{id}/edit")
	public String editClientDetails(@PathVariable Long id, Model model) {
		Client client = new Client();
		client = clientRepository.findById(id);
		model.addAttribute("client", client);
		getCompanySizeList(model);
		return "client/clientEdit";
	}
	
	@PostMapping(path="/client/{id}/edit")
	public String editClientDetailsPost(@PathVariable Long id, Model model, Client client, Principal principal) {
		User user = userServiceImpl.findByUsername(principal.getName());
		client.setUser(user);
		clientRepository.saveAndFlush(client);
		String message = "Changes were saved!";
		model.addAttribute("message", message);
		model.addAttribute("client", client);
		getCompanySizeList(model);
		return "client/clientDetails";
	}
	
	@GetMapping(path="/client/{id}/delete")
	public String deleteClient(@PathVariable Long id, Model model) {
		Client client = clientRepository.findById(id);
		clientRepository.delete(client);
		model.addAttribute("message", new String("Record has been deleted!"));
		List<Client> clients = clientRepository.findAll();
		model.addAttribute("clients", clients);
		return "client/clientsList";
	}
	
	@GetMapping(path="/client/{id}/copy")
	public String copyClientToNewRecord(@PathVariable Long id, Model model, Principal principal) {
		Client client = clientRepository.findById(id);
		client.setId(null);
		client.setClientName(null);
		client.setEmail(null);
		client.setUser(userServiceImpl.findByUsername(principal.getName()));
		model.addAttribute("copiedClient", client);
		return "client/addClient";
		
	}		
}

package pl.amelco.crm.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import pl.amelco.crm.entity.Client;
import pl.amelco.crm.entity.CompanySize;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.ClientRepository;
import pl.amelco.crm.repository.CompanySizeRepository;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.UserServiceImpl;

@Controller
public class ClientController {
	
	@Autowired
	CompanySizeRepository companySizeRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	@ModelAttribute
	public void getCompanySizeList(Model model) {
		List<CompanySize> sizes = companySizeRepository.findAll();
		model.addAttribute("sizes", sizes);
	}
	
	@GetMapping(path="/dashboard/addclient")
	public String addNewClientGet(@ModelAttribute Client client, Model model) {
		getCompanySizeList(model);
		return "client/addClient";
	}
	
	@PostMapping(path="/dashboard/addclient")
	public String addNewClientPost(Client client, HttpSession sess, Principal principal) {
		User user = userServiceImpl.findByUsername(principal.getName());
		client.setUser(user);
		clientRepository.save(client);
		return "client/successAdd";
	}
	
	@GetMapping(path="/dashboard/clientslist")
	public String showClientsList(Model model) {
		List<Client> clients = clientRepository.findAll();
		model.addAttribute("clients", clients);
		return "client/clientsList";
	}
	
	@GetMapping(path="/dashboard/client/{id}/details")
	public String showClientDetails(@PathVariable Long id, Model model) {
		Client client = new Client();
		client = clientRepository.findById(id);
		model.addAttribute("client", client);
		return "client/clientDetails";
	}
	
	@GetMapping(path="/dashboard/client/{id}/edit")
	public String editClientDetails(@PathVariable Long id, Model model) {
		Client client = new Client();
		client = clientRepository.findById(id);
		model.addAttribute("client", client);
		getCompanySizeList(model);
		return "client/clientEdit";
	}
	
	@PostMapping(path="/dashboard/client/{id}/edit")
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
	
	@GetMapping(path="/dashboard/client/{id}/delete")
	public String deleteClient(@PathVariable Long id, Model model) {
		Client client = clientRepository.findById(id);
		clientRepository.delete(client);
		model.addAttribute("message", new String("Record has been deleted!"));
		return "client/clientsList";
	}
	
	@GetMapping(path="/dashboard/client/{id}/copy")
	public String copyClientToNewRecord(@PathVariable Long id, Model model, Principal principal) {
		Client client = clientRepository.findById(id);
		client.setId(null);
		client.setClientName(null);
		client.setEmail(null);
		client.setUser(userServiceImpl.findByUsername(principal.getName()));
		model.addAttribute("copiedClient", client);
		return "client/addClient";
		
	}
	
//	@GetMapping(path="/test")
//	@ResponseBody
//	public String test(HttpSession sess) {
//		User user = (User) sess.getAttribute("loggedInUser");
//		return "Znaleziono w sesji: " + user.getUsername();
//	}
	
	
	
}

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
import org.springframework.web.bind.annotation.ResponseBody;

import pl.deafpunch.crm.classes.CompanySizeEnum;
import pl.deafpunch.crm.classes.RegionEnum;
import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.ClientAddress;
import pl.deafpunch.crm.entity.ClientNote;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.ClientAddressRepository;
import pl.deafpunch.crm.repository.ClientNoteRepository;
import pl.deafpunch.crm.repository.ClientRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.UserServiceImpl;

@Controller
@RequestMapping(path="/client")
public class ClientController {
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientAddressRepository clientAddressRepository;
	
	@Autowired
	ClientNoteRepository clientNoteRepository;
	
	/**
	 * Adding model attribute to GET.method filled with list of possible company sizes according to CompanysizeEnum values; 
	 * @param model
	 */
	
	@ModelAttribute
	public void getAllVocabularyLists(Model model) {
		List<CompanySizeEnum> sizes = Arrays.asList(CompanySizeEnum.values());
		model.addAttribute("sizes", sizes);
		List<RegionEnum> regions = Arrays.asList(RegionEnum.values());
		model.addAttribute("regions", regions);
	}
	
	/**
	 * Returning view with for to add new client
	 * @param client (model based on Client entity)
	 * @param model
	 * @return View with form
	 */
	
	@GetMapping(path="/addclient")
	public String addNewClientGet(@ModelAttribute Client client, Model model) {
		getAllVocabularyLists(model);
		model.addAttribute("clientAddress", new ClientAddress());
		model.addAttribute("clientNote", new ClientNote());
//		model.addAttribute("regions", new Region)
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
	@ResponseBody
	public String addNewClientPost(Client client, ClientNote clientNote, ClientAddress clientAddress, HttpSession sess, Principal principal, Model model) {
		User user = userServiceImpl.findByUsername(principal.getName());
		client.setUser(user);
//		clientAddressRepository.saveAndFlush(clientAddress);
//		clientNoteRepository.saveAndFlush(clientNote);
		clientRepository.save(client);
//		model.addAttribute("message", new String("New customer has been added successfully!"));
//		List<Client> clients = clientRepository.findAll();
//		model.addAttribute("clients", clients);
//		return "client/clientsList";
		return "success!!";
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
		client = clientRepository.findOneById(id);
		model.addAttribute("client", client);
		return "client/clientDetails";
	}
	
	@GetMapping(path="/client/{id}/edit")
	public String editClientDetails(@PathVariable Long id, Model model) {
		Client client = new Client();
		client = clientRepository.findOneById(id);
		model.addAttribute("client", client);
		getAllVocabularyLists(model);
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
		getAllVocabularyLists(model);
		return "client/clientDetails";
	}
	
	@GetMapping(path="/client/{id}/delete")
	public String deleteClient(@PathVariable Long id, Model model) {
		Client client = clientRepository.findOneById(id);
		clientRepository.delete(client);
		model.addAttribute("message", new String("Record has been deleted!"));
		List<Client> clients = clientRepository.findAll();
		model.addAttribute("clients", clients);
		return "client/clientsList";
	}
	
	@GetMapping(path="/client/{id}/copy")
	public String copyClientToNewRecord(@PathVariable Long id, Model model, Principal principal) {
		Client client = clientRepository.findOneById(id);
		client.setId(null);
		client.setClientName(null);
		client.setEmail(null);
		client.setUser(userServiceImpl.findByUsername(principal.getName()));
		model.addAttribute("copiedClient", client);
		return "client/addClient";
		
	}		
}

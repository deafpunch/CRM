package pl.deafpunch.crm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.deafpunch.crm.classes.SearchCriteria;
import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.CompanySizeEnum;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.ClientRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.ClientServiceImpl;

@Controller
@RequestMapping(path="/dashboard/client/search")
public class ClientSearchController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientServiceImpl clientServiceImpl;
	
	@Autowired
	ClientRepository clientRepository;
	
	
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
	 * Adding model attribute to GET.method with list of all existing application users
	 * @param model
	 */
	
	@ModelAttribute
	public void getUsersForCriterion(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
	}
	
	/**
	 * Return view with all possible search forms
	 * @param model
	 * @return view
	 */
	
	@GetMapping
	public String searchForClients(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		getCompanySizeList(model);
		getUsersForCriterion(model);
		return "client/search";
	}
	
	/**
	 * Search for clients with specific client record owner (based on given user ID)
	 * @param searchCriteria (String with ID of choosen user)
	 * @param model (passing results to the view)
	 * @return (Results as list of clients)
	 */
	
	@PostMapping(path="/byrecordowner")
	public String searchByClientOwner(SearchCriteria searchCriteria, Model model) {
		Long id = Long.parseLong(searchCriteria.getCriterion());
		List<Client> clients = clientServiceImpl.getClientsByOwner(id);

		model.addAttribute("clients", clients);
		passMessageToView(("Search result for username: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	/**
	 * Search for clients with specific client company name (based on given String)
	 * @param searchCriteria (String with name to search for)
	 * @param model (passing results to the view)
	 * @return (Results as list of clients)
	 */
	
	@PostMapping(path="/byclientname")
	public String searchByClientName(SearchCriteria searchCriteria, Model model) {
		String criterion = searchCriteria.getCriterion();
		List<Client> clients = clientServiceImpl.getClientsByClientname(criterion);
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for given word: " + criterion), model);
		return "client/searchResult";
	}
	
	/**
	 * Search for clients with specific client record ID (based on given client ID)
	 * @param searchCriteria (String with client ID to search for)
	 * @param model (passing result to the view)
	 * @return (View with found client details)
	 */
	
	@PostMapping(path="/byid")
	public String searchByClientId(SearchCriteria searchCriteria, Model model) {
		Client client = clientRepository.findById(Long.parseLong(searchCriteria.getCriterion()));
		model.addAttribute("client", client);
		return "client/clientDetails";
	}
	
	/**
	 * Search for clients with specific client address (based on given String with address)
	 * @param searchCriteria (String with address to search for)
	 * @param model (passing results to the view)
	 * @return (View with all found clients)
	 */
	
	@PostMapping(path="/byaddress")
	public String searchByClientAddress(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByAddress(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given address: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	/**
	 * Search for clients with specific company size (based on chosen size from dropdown list)
	 * @param searchCriteria (String with chosen size)
	 * @param model (passing results to the view)
	 * @return (View with all found clients)
	 */
	
	@PostMapping(path="/bycompanysize")
	public String searchByClientCompanySize(SearchCriteria searchCriteria, Model model) {
		String criterion = searchCriteria.getCriterion();
		List<Client> clients = clientServiceImpl.getClientsByCompanySize(CompanySizeEnum.valueOf(criterion));
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given company size: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	/**
	 * Search for clients with specific phone number (based on String with phone number)
	 * @param searchCriteria (String with phone number)
	 * @param model (passing results to the view)
	 * @return (View with all found clients)
	 */
	
	@PostMapping(path="/byphonenumber")
	public String searchByClientPhoneNumber(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByPhoneNumber(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given phone number: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	/**
	 * Search for clients with specific email address (based on String with email address)
	 * @param searchCriteria (String with email address)
	 * @param model (passing results to the view)
	 * @return (View with all found clients)
	 */
	
	@PostMapping(path="/byemail")
	public String searchByClientEmailAddress(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByEmailAddress(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given email address: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	/**
	 * Adding attribute to the model with given String (used to print as a message in the view)
	 * @param message
	 * @param model
	 */	
	
	public void passMessageToView(String message, Model model) {
		model.addAttribute("message", message);
	}
	
}

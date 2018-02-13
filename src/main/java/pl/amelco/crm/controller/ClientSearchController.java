package pl.amelco.crm.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.amelco.crm.classes.SearchCriteria;
import pl.amelco.crm.entity.Client;
import pl.amelco.crm.entity.CompanySizeEnum;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.ClientRepository;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.ClientServiceImpl;

@Controller
@RequestMapping(path="/dashboard/client/search")
public class ClientSearchController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientServiceImpl clientServiceImpl;
	
	@Autowired
	ClientRepository clientRepository;
	
	@ModelAttribute
	public void getCompanySizeList(Model model) {
		List<CompanySizeEnum> sizes = Arrays.asList(CompanySizeEnum.values());
		model.addAttribute("sizes", sizes);
	}
	
	@ModelAttribute
	public void getUsersForCriterion(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
	}
	
	@GetMapping
	public String searchForClients(Model model) {
		model.addAttribute("searchCriteria", new SearchCriteria());
		getCompanySizeList(model);
		getUsersForCriterion(model);
		return "client/search";
	}
	
	@PostMapping(path="/byrecordowner")
	public String searchByClientOwner(SearchCriteria searchCriteria, Model model) {
		Long id = Long.parseLong(searchCriteria.getCriterion());
		List<Client> clients = clientServiceImpl.getClientsByOwner(id);

		model.addAttribute("clients", clients);
		passMessageToView(("Search result for username: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	@PostMapping(path="/byclientname")
	public String searchByClientName(SearchCriteria searchCriteria, Model model) {
		String criterion = searchCriteria.getCriterion();
		List<Client> clients = clientServiceImpl.getClientsByClientname(criterion);
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for given word: " + criterion), model);
		return "client/searchResult";
	}
	
	@PostMapping(path="/byid")
	public String searchByClientId(SearchCriteria searchCriteria, Model model) {
		Client client = clientRepository.findById(Long.parseLong(searchCriteria.getCriterion()));
		model.addAttribute("client", client);
		return "client/clientDetails";
	}
	
	@PostMapping(path="/byaddress")
	public String searchByClientAddress(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByAddress(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given address: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	@PostMapping(path="/bycompanysize")
	public String searchByClientCompanySize(SearchCriteria searchCriteria, Model model) {
		String criterion = searchCriteria.getCriterion();
		List<Client> clients = clientServiceImpl.getClientsByCompanySize(CompanySizeEnum.valueOf(criterion));
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given company size: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	@PostMapping(path="/byphonenumber")
	public String searchByClientPhoneNumber(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByPhoneNumber(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given phone number: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	@PostMapping(path="/byemail")
	public String searchByClientEmailAddress(SearchCriteria searchCriteria, Model model) {
		List<Client> clients = clientServiceImpl.getClientsByEmailAddress(searchCriteria.getCriterion());
		model.addAttribute("clients", clients);
		passMessageToView(("Search results for client with given email address: " + searchCriteria.getCriterion()), model);
		return "client/searchResult";
	}
	
	
	public void passMessageToView(String message, Model model) {
		model.addAttribute("message", message);
	}
	
}

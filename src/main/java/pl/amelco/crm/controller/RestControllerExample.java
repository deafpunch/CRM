package pl.amelco.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.amelco.crm.entity.Client;
import pl.amelco.crm.entity.User;
import pl.amelco.crm.repository.ClientRepository;
import pl.amelco.crm.repository.UserRepository;
import pl.amelco.crm.service.ClientServiceImpl;

@RestController
@RequestMapping(path = "/api")
public class RestControllerExample {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClientServiceImpl clientServiceImpl;

	@GetMapping(path = "/allclients")
	public ResponseEntity<List<Client>> getAllClients() {
		List<Client> list = clientRepository.findAll();
		if (list == null) {
			return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}/user")
	public ResponseEntity<User> getUserByID(@PathVariable Long id) {
		User user = userRepository.findById(id);

		if (user == null) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping(path = "/createuser")
	public ResponseEntity<Void> createNewClient(@RequestBody Client client, UriComponentsBuilder ucBuilder) {

		if (clientServiceImpl.checkIfClientExist(client)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			clientServiceImpl.saveClient(client);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/client/{id}/details").buildAndExpand(client.getId()).toUri());
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
	}

	@PutMapping(path = "/client/{id}/edit")
	public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
		if (client == null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		} else if (client != null && !clientServiceImpl.checkIfClientExist(client)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			Client updatedClient = clientServiceImpl.updateClient(client);
			return new ResponseEntity<>(updatedClient, HttpStatus.OK);
		}

	}

}

package pl.deafpunch.crm.controller;

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

import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.ClientRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.ClientServiceImpl;

@RestController
@RequestMapping(path = "/api")
public class RestControllerExample {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ClientServiceImpl clientServiceImpl;

	/**
	 * Method is finding all records of type Client
	 * @return ResponseEntity with records list and HttpStatus.OK
	 */
	
	@GetMapping(path = "/allclients")
	public ResponseEntity<List<Client>> getAllClients() {
		List<Client> list = clientRepository.findAll();
		if (list == null) {
			return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	/**
	 * Search for user with given ID via @PathVariable.
	 * @param id (for user record)
	 * @return ResponseEntity with one user found, HttpStatus.OK
	 */

	@GetMapping(path = "/{id}/user")
	public ResponseEntity<User> getUserByID(@PathVariable Long id) {
		User user = userRepository.findById(id);

		if (user == null) {
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	/**
	 * Search for client with given ID via @PathVariable.
	 * @param id (for client record)
	 * @return ResponseEntity with one client found, HttpStatus.OK
	 */
	
	@GetMapping(path = "/{id}/client")
	public ResponseEntity<Client> getClientByID(@PathVariable Long id) {
		Client client = clientRepository.findById(id);

		if (client == null) {
			return new ResponseEntity<>(client, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Client>(client, HttpStatus.OK);
	}

	/**
	 * Creates new client record in database, based on given Client type object.
	 * If such record exists, returns HttpStatus.CONFLICT
	 * @param client
	 * @param ucBuilder 
	 * @return returning new created record wrapped in ResponseEntity and new URL path directing to this record.
	 */
	
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
	
	/**
	 * Edits client record based on found record by given client ID from @PathVariable
	 * @param id Client record ID
	 * @param client Passed model to merge with existing client record
	 * @return returning updated client record wrapped in ResponseEntity and HttpStatus.OK
	 */

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

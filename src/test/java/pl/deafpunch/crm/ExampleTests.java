package pl.deafpunch.crm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import pl.deafpunch.crm.controller.RestControllerExample;
import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.User;
import pl.deafpunch.crm.repository.ClientRepository;
import pl.deafpunch.crm.repository.UserRepository;
import pl.deafpunch.crm.service.ClientServiceImpl;
import pl.deafpunch.crm.service.UserService;
import pl.deafpunch.crm.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExampleTests {

	@Mock
	private ClientRepository clientRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RestControllerExample restControllerExample;
	
	@Mock
	private ClientServiceImpl clientServiceImpl;
	
	@Mock
	private UserServiceImpl userServiceImpl;
	
	@Test
	public void when_search_for_client_by_id_if_it_exists_then_return_true() {
		Client client = new Client();
		client.setId(1l);
		
		when(clientServiceImpl.checkIfClientExist(client)).thenReturn(true);
		
		assertTrue(clientServiceImpl.checkIfClientExist(client));		
	}
	
//	@Test
//	public void when_searching_user_id_then_return_user_object() {
//		User userA = new User();
//		userA.setId((long) 1);
//		when(userRepository.findById((long)1)).thenReturn(userA);
//		User userB = userServiceImpl.findById((long)1);
////		User userB = new User();
////		userB.setId((long)1);
//		System.out.println("UserA: " + userA);
//		System.out.println("UserB: " + userB);
//		assertEquals(userB.getId(), userA.getId());
//	}
	
	@Test
	public void when_save_user_then_it_is_returned_correctly() {
		User user = new User();
		user.setUsername("username");
		
		when(userRepository.save(user)).thenReturn(user);
		
		User result = userRepository.save(user);
		
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	
	// Testing REST controller methods.
	
	@Test
	public void when_update_client_then_it_is_returned_correctly() {
		Client client = new Client();
		client.setId(1l);
		ResponseEntity<Client> RE = new ResponseEntity<Client>(client, HttpStatus.OK);
		
		when(restControllerExample.updateClient(1l, client)).thenReturn(RE);
		
		ResponseEntity<Client> result = restControllerExample.updateClient(1l, client);
		
		assertNotNull(result);
		assertEquals(RE, result);
		assertNotNull(result.getHeaders().containsValue("/client/1/details"));
	}
	
	
	@Test
	public void when_searching_user_id_and_null_is_given_as_object_parameter_then_return_NOT_FOUND() {
		User userA = null;
		ResponseEntity<User> RE = new ResponseEntity<>(userA, HttpStatus.NOT_FOUND);
		when(restControllerExample.getUserByID(null)).thenReturn(RE);
		ResponseEntity<User> result = restControllerExample.getUserByID(null);
		assertEquals(RE, result);
		
	}
	
	@Test
	public void when_method_allclient_called_then_list_is_returned(){
		List<Client> list = new ArrayList<>();
		ResponseEntity<List<Client>> RE = new ResponseEntity<>(list, HttpStatus.OK);
		when(restControllerExample.getAllClients()).thenReturn(RE);
		ResponseEntity<List<Client>> result = restControllerExample.getAllClients();
		assertEquals(RE, result);
		assertNotNull(result);
	}	
}

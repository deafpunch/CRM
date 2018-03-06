package pl.deafpunch.crm.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void when_search_client_by_id_then_return_object() {
		//given
		Client client = new Client();
		client.setClientName("TestName");
		client.setEmail("client@mail.com");
		client.setPhoneNumber("123123123");
		entityManager.persist(client);
		//when
		Client result = clientRepository.findOneById((long)3);
		//then
		assertNotNull(result);
		assertSame(client, result);
	}
	
	@Test
	public void when_search_all_clients_by_record_owner_id_then_return_users_list() {
		//given
		User user = new User();
		user.setUsername("admin");
		user.setEmail("admin@admin.pl");
		Client clientA = new Client();
		clientA.setClientName("TestName1");
		clientA.setEmail("client@mail.com");
		clientA.setPhoneNumber("123123123");
		clientA.setUser(user);
		Client clientB = new Client();
		clientB.setClientName("TestName2");
		clientB.setEmail("client@mail.net");
		clientB.setPhoneNumber("111222333");
		clientB.setUser(user);
		entityManager.persist(user);
		entityManager.persist(clientA);
		entityManager.persist(clientB);
		List<Client> list = new ArrayList<>();
		list.add(clientA);
		list.add(clientB);
		//when
		List<Client> result = clientRepository.findByOwnerId((long)1);
		//then
		assertNotNull(result);
		assertEquals(list, result);
	}
	
	

}

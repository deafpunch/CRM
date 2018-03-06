package pl.deafpunch.crm.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import pl.deafpunch.crm.entity.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void when_searching_user_by_id_then_return_user_object() {
		//given
		User user = new User();
		user.setUsername("admin");
		user.setEmail("admin@admin.pl");
		entityManager.persist(user);
		//when
		User result = userRepository.findOneById((long)2);
		//then
		assertNotNull(result);
		assertSame(result, user);
	}
	
	@Test
	public void when_searching_user_by_username_then_return_user_object() {
		//given
		User user = new User();
		user.setUsername("admin");
		user.setEmail("admin@admin.com");
		entityManager.persist(user);
		//when
		User result = userRepository.findByUsername("admin");
		//then
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	public void when_searching_user_by_email_then_return_user_object() {
		//given
		User user = new User();
		user.setUsername("admin");
		user.setEmail("admin@admin.com");
		entityManager.persist(user);
		//when
		User result = userRepository.findByEmail("admin@admin.com");
		//then
		assertNotNull(result);
		assertSame(result, user);
	}

}

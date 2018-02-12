package pl.amelco.crm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.amelco.crm.entity.User;
import pl.amelco.crm.service.UserServiceImpl;

public class UserServiceImplTest {

	
	@Autowired
	UserServiceImpl usi;
	
	 @Test
	    public void when_searching_admin_then_return_object() {
	       User user1 = new User();
	       user1.setUsername("admin");
	       User user2 = usi.findByUsername("admin");
	       assertEquals(user1.getUsername(),
	    		   user2.getUsername());
	       
	    }
}

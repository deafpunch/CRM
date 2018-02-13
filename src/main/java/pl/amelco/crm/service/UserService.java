package pl.amelco.crm.service;

import java.util.List;

import pl.amelco.crm.entity.User;

public interface UserService {
	
	public User findByUsername(String username);

	public void saveUser(User user);
	
}

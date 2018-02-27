package pl.deafpunch.crm.service;

import javax.servlet.http.HttpSession;

import pl.deafpunch.crm.entity.User;

public interface UserService {
	
	public User findByUsername(String username);

	public void saveUser(User user);
	
	public void updateUser(User user, HttpSession sess);
	
	public User findById(Long id);
	
}

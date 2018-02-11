package pl.amelco.crm.service;

import pl.amelco.crm.entity.User;

public interface UserService {
	public User findByUserName(String name);

	public void saveUser(User user);
}

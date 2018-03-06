package pl.deafpunch.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.deafpunch.crm.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

//	@Query(value="SELECT u FROM users u WHERE u.username = ?1")
	User findByUsername(String username);

	User findOneById(Long id);
	
	User findByEmail(String email);


}

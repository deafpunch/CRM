package pl.amelco.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.amelco.crm.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

//	@Query(value="SELECT u FROM users u WHERE u.username = ?1")
	User findByUsername(String username);

	User findById(Long id);
	
	User findByEmail(String email);

//	@Query("SELECT u FROM users u WHERE u.username LIKE ?1%")
//	List<User> findBySome(String some);
//	
}

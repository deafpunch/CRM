package pl.deafpunch.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.deafpunch.crm.classes.CompanySizeEnum;
import pl.deafpunch.crm.entity.Client;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	@Query(value="SELECT c FROM clients c WHERE c.user.id = ?1")
	List<Client> findByOwnerId(Long id);
	
	Client findOneById(Long id);
	
	@Query(value="SELECT c FROM clients c WHERE c.clientName LIKE %?1%" )
	List<Client> findByClientsName(String clientName);
	
	@Query(value="SELECT c FROM clients c WHERE c.companySize = ?1" )
	List<Client> findByClientsCompanySize(CompanySizeEnum size);
	
	@Query(value="SELECT c FROM clients c WHERE c.phoneNumber LIKE %?1%" )
	List<Client> findByClientsPhoneNumber(String phoneNumber);
	
	List<Client> findByEmail(String email);

}

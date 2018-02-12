package pl.amelco.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.amelco.crm.entity.Client;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findById(Long id);
}

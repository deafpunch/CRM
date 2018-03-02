package pl.deafpunch.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.deafpunch.crm.entity.ClientAddress;

public interface ClientAddressRepository extends JpaRepository<ClientAddress, Long> {

}

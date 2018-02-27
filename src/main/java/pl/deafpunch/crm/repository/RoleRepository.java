package pl.deafpunch.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.deafpunch.crm.entity.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByName(String name);

	public Role findById(Long id);
}

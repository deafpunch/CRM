package pl.amelco.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.amelco.crm.entity.CompanySize;

@Repository("companySizeRepository")
public interface CompanySizeRepository extends JpaRepository<CompanySize, Long> {

}

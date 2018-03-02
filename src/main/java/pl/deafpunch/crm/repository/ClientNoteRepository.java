package pl.deafpunch.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.deafpunch.crm.entity.ClientNote;

public interface ClientNoteRepository extends JpaRepository<ClientNote, Long> {

}

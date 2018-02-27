package pl.deafpunch.crm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import pl.deafpunch.crm.entity.FileModel;

public interface FileModelRepository extends CrudRepository<FileModel, Long> {
    
	Optional<FileModel> findByFileName(String fileName);

}
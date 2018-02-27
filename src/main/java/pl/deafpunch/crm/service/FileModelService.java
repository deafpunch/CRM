package pl.deafpunch.crm.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.deafpunch.crm.entity.FileModel;
import pl.deafpunch.crm.repository.FileModelRepository;

@Service
public class FileModelService {

	@Autowired
    private final FileModelRepository fileModelRepository;

    FileModelService(FileModelRepository fileModelRepository) {
        this.fileModelRepository = fileModelRepository;
    }

    /*
     * EXPERIMENTING
     */
    

    public void addFile(MultipartFile multipartFile) throws IOException {
        FileModel fileModel = new FileModel(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getBytes());
        fileModelRepository.save(fileModel);
    }

    public FileModel findFileByName(String fileName){
        return fileModelRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException(fileName));
    }
}

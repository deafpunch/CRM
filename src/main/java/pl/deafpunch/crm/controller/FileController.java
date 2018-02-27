package pl.amelco.crm.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.amelco.crm.entity.FileModel;
import pl.amelco.crm.service.FileModelService;

@RestController
public class FileController {

	/*
	 * 
	 * EXPERIMENTING WITH FILES UPLOADING
	 * 
	 */
	
	
	@Autowired
	 private FileModelService fileModelService;

	@PostMapping(path="file/upload")
	@ResponseBody
	    public String addFile(@RequestParam("file") MultipartFile file) throws IOException {
	        fileModelService.addFile(file);
	        return "Success";
	}
	
	
	 @GetMapping(path="/getfile/{fileName}")
	    ResponseEntity getFile(@PathVariable String fileName){
	        FileModel fileByName = fileModelService.findFileByName(fileName);
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentDispositionFormData(fileByName.getFileName(), fileByName.getFileName());
	        headers.setContentType(MediaType.parseMediaType(fileByName.getFileType()));
	        return new ResponseEntity(fileByName.getData(), headers, HttpStatus.OK);
	}

	
	 
}

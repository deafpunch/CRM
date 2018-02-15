package pl.amelco.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {
	@Id
	@GeneratedValue
	private Long id;
	private String fileName;
	private String fileType;
	@Column
	@Lob
	private byte[] data;

	@OneToOne
	private Client client;

	@OneToOne
	private User user;
	
	private Boolean isAccepted;
	private Boolean finalDecision;
	
	

	public FileModel(String fileName, String fileType, byte[] data) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}
}

package pl.deafpunch.crm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "clientAddress")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String city;
	
	private String street;
	
	private String streetNumber;
	
	private String postalCode;
	
	private String region;
	
	
}

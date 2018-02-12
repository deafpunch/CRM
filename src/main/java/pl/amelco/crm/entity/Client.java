package pl.amelco.crm.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date created = new Date();

	@Column(nullable = false, unique = true)
	private String clientName;

	@NotEmpty
	private String address;

//	@ManyToOne(cascade = {CascadeType.ALL})
//	@JoinColumn(name = "company_size_id", nullable = false)
	@Enumerated(EnumType.STRING)
	private CompanySizeEnum companySize;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;	
	
	private String notes;
	

}

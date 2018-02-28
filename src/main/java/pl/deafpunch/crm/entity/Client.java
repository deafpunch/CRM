package pl.deafpunch.crm.entity;

import java.util.Date;

import javax.persistence.Column;
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

import pl.deafpunch.crm.classes.CompanySizeEnum;

@Entity(name = "clients")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date created = new Date();

	@Column(nullable = false, unique = true)
	private String clientName;

	@ManyToOne
	@JoinColumn(name="clientAddress_id")
	private ClientAddress address;

	@Enumerated(EnumType.STRING)
	private CompanySizeEnum companySize;

	@NotEmpty
	private String phoneNumber;

	@NotEmpty
	@Email
	@Column(nullable = false, unique = true)
	private String email;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;	
	
	private String notes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public CompanySizeEnum getCompanySize() {
		return companySize;
	}

	public void setCompanySize(CompanySizeEnum companySize) {
		this.companySize = companySize;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Client(Long id, Date created, String clientName, CompanySizeEnum companySize, String phoneNumber,
			String email, User user, String notes) {
		super();
		this.id = id;
		this.created = created;
		this.clientName = clientName;
		this.companySize = companySize;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.user = user;
		this.notes = notes;
	}

	public Client() {
		super();
	}
	
	
	

}

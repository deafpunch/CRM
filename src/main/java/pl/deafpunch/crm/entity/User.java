package pl.deafpunch.crm.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	
	private String password;
	
	@Email
	@NotNull
	@Column(unique=true)
	private String email;
	
	private Boolean enabled = true;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_client", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
	private Set<Client> clients;

}

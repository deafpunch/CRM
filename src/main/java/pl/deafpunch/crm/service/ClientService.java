package pl.deafpunch.crm.service;

import java.util.List;

import pl.deafpunch.crm.classes.CompanySizeEnum;
import pl.deafpunch.crm.entity.Client;

public interface ClientService {

	public void saveClient(Client client);
	
	public List<Client> findClientsByOwner(Long id);
	
	public List<Client> findClientsByClientname(String clientName);
	
	public List<Client> findClientsByAddress(String address);
	
	public List<Client> findClientsByCompanySize(CompanySizeEnum size);
	
	public List<Client> findClientsByPhoneNumber(String phoneNumber);
	
	public List<Client> findClientsByEmailAddress(String email);
	
	public boolean checkIfClientExist(Client client);
	
	public Client updateClient(Client client);
	
}

package pl.deafpunch.crm.service;

import java.util.List;

import pl.deafpunch.crm.classes.CompanySizeEnum;
import pl.deafpunch.crm.entity.Client;

public interface ClientService {

	public void saveClient(Client client);
	
	public List<Client> getClientsByOwner(Long id);
	
	public List<Client> getClientsByClientname(String clientName);
	
	public List<Client> getClientsByAddress(String address);
	
	public List<Client> getClientsByCompanySize(CompanySizeEnum size);
	
	public List<Client> getClientsByPhoneNumber(String phoneNumber);
	
	public List<Client> getClientsByEmailAddress(String email);
	
	public boolean checkIfClientExist(Client client);
	
	public Client updateClient(Client client);
	
}

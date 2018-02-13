package pl.amelco.crm.service;

import java.util.List;

import pl.amelco.crm.entity.Client;
import pl.amelco.crm.entity.CompanySizeEnum;

public interface ClientService {

	public void saveClient(Client client);
	
	public List<Client> getClientsByOwner(Long id);
	
	public List<Client> getClientsByClientname(String clientName);
	
	public List<Client> getClientsByAddress(String address);
	
	public List<Client> getClientsByCompanySize(CompanySizeEnum size);
	
	public List<Client> getClientsByPhoneNumber(String phoneNumber);
	
	public List<Client> getClientsByEmailAddress(String email);
	
}

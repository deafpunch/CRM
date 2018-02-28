package pl.deafpunch.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.deafpunch.crm.classes.CompanySizeEnum;
import pl.deafpunch.crm.entity.Client;
import pl.deafpunch.crm.repository.ClientRepository;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public void saveClient(Client client) {
		clientRepository.saveAndFlush(client);		
	}

	@Override
	public List<Client> getClientsByOwner(Long id) {
		List<Client> clients = clientRepository.findByOwnerId(id);
		return clients;
	}

	@Override
	public List<Client> getClientsByClientname(String clientName) {
		List<Client> clients = clientRepository.findByClientsName(clientName);		
		return clients;
	}

	@Override
	public List<Client> getClientsByAddress(String address) {
//		List<Client> clients = clientRepository.findByClientsAddress(address);
//		return clients;
		return null;
	}

	@Override
	public List<Client> getClientsByCompanySize(CompanySizeEnum size) {
		List<Client> clients = clientRepository.findByClientsCompanySize(size);
		return clients;
	}

	@Override
	public List<Client> getClientsByPhoneNumber(String phoneNumber) {
		List<Client> clients = clientRepository.findByClientsPhoneNumber(phoneNumber);
		return clients;
	}

	@Override
	public List<Client> getClientsByEmailAddress(String email) {
		List<Client> clients = clientRepository.findByEmail(email);
		return clients;
	}

	/**
	 * Checking if Client record exist based on unique values such as clienName and clients email address
	 */
	
	@Override
	public boolean checkIfClientExist(Client client) {
		// Client name and email address are marked as unique value
		Client byName = (Client) clientRepository.findByClientsName(client.getClientName());
		Client byEmail = (Client) clientRepository.findByEmail(client.getEmail());
		if(byName == null || byEmail == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	/**
	 *  This method is comparing two object of the same ID (client from database and from the form). 
	 *  It checks for changes and if there are any - changes are saved instantly to database.
	 */

	@Override
	public Client updateClient(Client client) {
		Client clientToSave = (Client) clientRepository.findByClientsName(client.getClientName());
		
//		if (!clientToSave.getClientName().equals(client.getClientName())) {
//			clientToSave.setClientName(client.getClientName());
//		}else if (!clientToSave.getAddress().equals(client.getAddress())) {
//			clientToSave.setAddress(client.getAddress());
//		}else if (!clientToSave.getCompanySize().equals(client.getCompanySize())) {
//			clientToSave.setCompanySize(client.getCompanySize());
//		}else if (!clientToSave.getPhoneNumber().equals(client.getPhoneNumber())) {
//			clientToSave.setPhoneNumber(client.getPhoneNumber());
//		}else if (!clientToSave.getEmail().equals(client.getEmail())) {
//			clientToSave.setEmail(client.getEmail());
//		}else if(!clientToSave.getNotes().equals(client.getNotes())) {
//			clientToSave.setNotes(client.getNotes());
//		}
		
		clientRepository.saveAndFlush(clientToSave);
		return clientToSave;
	}


}

package pl.amelco.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.amelco.crm.entity.Client;
import pl.amelco.crm.entity.CompanySizeEnum;
import pl.amelco.crm.repository.ClientRepository;

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
		List<Client> clients = clientRepository.findByClientsAddress(address);
		return clients;
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


}

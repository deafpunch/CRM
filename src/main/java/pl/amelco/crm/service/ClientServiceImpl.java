package pl.amelco.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.amelco.crm.entity.Client;
import pl.amelco.crm.repository.ClientRepository;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	@Override
	public void saveClient(Client client) {
		clientRepository.saveAndFlush(client);		
	}

}

package fr.orsys.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Client;
import fr.orsys.fx.pret_a_la_consommation.service.ClientService;

public class ClientServiceImpl implements ClientService {

	private List<Client> clients = new ArrayList<>();
	
	@Override
	public Client ajouterClient(final String nom, final String prenom) {
		Client client = new Client(nom, prenom);
		clients.add(client);
		return client;
	}

	@Override
	public Client ajouterClient(Client client) {
		clients.add(client);
		return client;
	}
	
	@Override
	public List<Client> recupererClients() {
		return clients;
	}

	@Override
	public Client recupererClient(Long id) {
		for (Client client : clients) {
			if (client.getId().equals(id)) {
				return client;
			}
		}
		return null;
	}

	@Override
	public boolean supprimerClient(Long id) {
		Client clientASupprimer = recupererClient(id);
		if (clientASupprimer==null) {
			return false;
		} else {
			return clients.remove(clientASupprimer);
		}
	}

}

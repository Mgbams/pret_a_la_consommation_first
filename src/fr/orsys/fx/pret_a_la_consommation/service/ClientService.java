package fr.orsys.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Client;

public interface ClientService {

	Client ajouterClient(final String nom, final String prenom);
	Client ajouterClient(Client client);
	
	List<Client> recupererClients();
	
	Client recupererClient(Long id);
	
	boolean supprimerClient(Long id);

}

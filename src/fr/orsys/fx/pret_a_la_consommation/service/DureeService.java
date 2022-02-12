package fr.orsys.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Duree;

/**
 * L'interface correspond à l'ardoise du restaurant
 * 
 * On ne fait que déclarer des méthodes dans l'interface
 * 
 * @author Fx COTE
 *
 */
public interface DureeService {

	// Déclaration de méthode
	Duree ajouterDuree(int dureeEnMois);
	Duree ajouterDuree(Duree object);
	
	List<Duree> recupererDurees();
	
	/**
	 * Cette méthode renvoit un objet de type Duree
	 * dont l'id est donnée en paramètre
	 * 
	 * @param id l'id recherché
	 * @return l'objet ayant l'id donné en paramètre, null s'il n'est pas trouvé
	 */
	Duree recupererDuree(Long id);

	
}

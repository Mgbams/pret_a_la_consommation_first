package fr.orsys.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Duree;
import fr.orsys.fx.pret_a_la_consommation.business.Motif;
import fr.orsys.fx.pret_a_la_consommation.business.Taux;

public interface TauxService {

	Taux ajouterTaux(double valeur, Duree duree, Motif motif);
	Taux ajouterTaux(Taux taux);
	
	Taux recupererTaux(Long id);

	List<Taux> recupererTaux();

}

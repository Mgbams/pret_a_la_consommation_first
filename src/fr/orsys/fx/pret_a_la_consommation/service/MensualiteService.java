package fr.orsys.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Mensualite;
import fr.orsys.fx.pret_a_la_consommation.business.Pret;

public interface MensualiteService {

	Mensualite ajouterMensualite(Pret pret, double partCapitalRembourse, double partInteretsRembourses);
	
	// Méthode qui renvoie toutes les mensualités d'un prêt donné en paramètre
	List<Mensualite> recupererMensualites(Pret pret);
}

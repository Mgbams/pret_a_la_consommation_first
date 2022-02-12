package fr.orsys.fx.pret_a_la_consommation.service;

import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Motif;

public interface MotifService {

	Motif ajouterMotif(String nom);
	Motif ajouterMotif(Motif motif);
	
	List<Motif> recupererMotifs();
	
	Motif recupererMotif(Long id);

	
}

package fr.orsys.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Duree;
import fr.orsys.fx.pret_a_la_consommation.service.DureeService;

public class DureeServiceImpl implements DureeService {

	// On déclare une liste de durées
	private List<Duree> durees = new ArrayList<>();
	
	// Implémentation de la méthode 
	// déclarée dans l'interface
	@Override
	public Duree ajouterDuree(int dureeEnMois) {
		Duree duree = new Duree(dureeEnMois);
		durees.add(duree);
		return duree;
	}

	@Override
	public Duree ajouterDuree(Duree duree) {
		durees.add(duree);
		return duree;
	}
	
	@Override
	public List<Duree> recupererDurees() {
		return durees;
	}

	@Override
	public Duree recupererDuree(Long id) {
		for (Duree duree : durees) {
			if (duree.getId().equals(id)) {
				return duree;
			}
		}
		return null;
	}

}
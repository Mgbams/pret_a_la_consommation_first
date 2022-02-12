package fr.orsys.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Duree;
import fr.orsys.fx.pret_a_la_consommation.business.Motif;
import fr.orsys.fx.pret_a_la_consommation.business.Taux;
import fr.orsys.fx.pret_a_la_consommation.service.TauxService;

public class TauxServiceImpl implements TauxService {

	private List<Taux> taux = new ArrayList<>();

	@Override
	public Taux ajouterTaux(double valeur, Duree duree, Motif motif) {
		Taux taux = new Taux(valeur, duree, motif);
		this.taux.add(taux);
		return taux;
	}
	
	@Override
	public Taux ajouterTaux(Taux taux) {
		this.taux.add(taux);
		return null;
	}
	
	@Override
	public Taux recupererTaux(Long id) {
		for (Taux taux : this.taux) {
			if (taux.getId().equals(id)) {
				return taux;
			}
		}
		return null;
	}

	@Override
	public List<Taux> recupererTaux() {
		return taux;
	}

}
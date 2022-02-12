package fr.orsys.fx.pret_a_la_consommation.service.impl;

import java.util.ArrayList;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Mensualite;
import fr.orsys.fx.pret_a_la_consommation.business.Pret;
import fr.orsys.fx.pret_a_la_consommation.service.MensualiteService;

public class MensualiteServiceImpl implements MensualiteService {

	private List<Mensualite> mensualites = new ArrayList<>();
	
	@Override
	public Mensualite ajouterMensualite(Pret pret, double partCapitalRembourse, double partInteretsRembourses) {
		Mensualite mensualite = new Mensualite(pret, partCapitalRembourse, partInteretsRembourses);
		mensualites.add(mensualite);
		return mensualite;
	}

	@Override
	public List<Mensualite> recupererMensualites(Pret pret) {
		List<Mensualite> mensualitesDuPret = new ArrayList<>();
		for (Mensualite mensualite : mensualitesDuPret) {
			if (mensualite.getPret().getId().equals(pret.getId())) {
				mensualitesDuPret.add(mensualite);
			}
		}
		return mensualitesDuPret;
	}

}

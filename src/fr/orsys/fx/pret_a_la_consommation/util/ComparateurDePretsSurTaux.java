package fr.orsys.fx.pret_a_la_consommation.util;

import java.util.Comparator;

import fr.orsys.fx.pret_a_la_consommation.business.Pret;

public class ComparateurDePretsSurTaux implements Comparator<Pret> {

	@Override
	public int compare(Pret pret1, Pret pret2) {
		return -((Double) pret1.getTaux().getValeur()).compareTo(pret2.getTaux().getValeur());
	}

}

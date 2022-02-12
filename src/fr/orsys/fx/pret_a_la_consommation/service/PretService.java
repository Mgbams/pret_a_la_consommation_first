package fr.orsys.fx.pret_a_la_consommation.service;

import java.time.LocalDate;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Client;
import fr.orsys.fx.pret_a_la_consommation.business.Pret;
import fr.orsys.fx.pret_a_la_consommation.business.Taux;
import fr.orsys.fx.pret_a_la_consommation.exceptions.DateEffetInvalideException;
import fr.orsys.fx.pret_a_la_consommation.exceptions.MontantExcessifException;

public interface PretService {

	Pret ajouterPret(Taux taux, Client client, Double montantDemande, LocalDate dateEffet) throws MontantExcessifException, DateEffetInvalideException;
	Pret ajouterPret(Pret pret);

	List<Pret> recupererPrets();

	Pret recupererPret(Long id);

	void trierPretsParMontantDecroissant();

	void trierPretsParTauxDecroissant();

	List<Pret> recupererPrets(LocalDate dateDebut, LocalDate dateFin);
	
	Long recupererDernierId();


}

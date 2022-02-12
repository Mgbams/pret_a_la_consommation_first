package fr.orsys.fx.pret_a_la_consommation.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.orsys.fx.pret_a_la_consommation.business.Client;
import fr.orsys.fx.pret_a_la_consommation.business.Mensualite;
import fr.orsys.fx.pret_a_la_consommation.business.Pret;
import fr.orsys.fx.pret_a_la_consommation.business.Taux;
import fr.orsys.fx.pret_a_la_consommation.exceptions.DateEffetInvalideException;
import fr.orsys.fx.pret_a_la_consommation.exceptions.MontantExcessifException;
import fr.orsys.fx.pret_a_la_consommation.service.MensualiteService;
import fr.orsys.fx.pret_a_la_consommation.service.PretService;
import fr.orsys.fx.pret_a_la_consommation.util.ComparateurDePretsSurTaux;

public class PretServiceImpl implements PretService {

	private static List<Pret> prets = new ArrayList<>();
	private MensualiteService mensualiteService = new MensualiteServiceImpl();
	
	@Override
	public Pret ajouterPret(Taux taux, Client client, Double montantDemande, LocalDate dateEffet) throws MontantExcessifException, DateEffetInvalideException {
		// On détermine le montant de l'échéance (ce que le client va rembourser chaque mois)
		// montantEcheance = (double) (montantDemande * tauxMensuel / (1 - Math.pow(1+tauxMensuel, -dureeEnMois)));
		double tauxMensuel = taux.getValeur() / 12;
		int nbMois = taux.getDuree().getDureeEnMois();
		double montantMensualite = (double) (montantDemande * tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -nbMois)));
		LocalDate datePrelevement = dateEffet;

		if (montantDemande > Pret.MONTANT_MAX) {
			throw new MontantExcessifException("Le montant max a été dépassé");
		}
		
		if (datePrelevement.isBefore(LocalDate.now())) {
			throw new DateEffetInvalideException("La date d'effet ne peut pas être dans le passé");
		}
		
		// On cree un objet pret
		Pret pret = new Pret(taux, client, montantDemande, dateEffet, montantMensualite);
		
		double partDesInterets = montantDemande * tauxMensuel;
		double capitalRembourse = montantMensualite - partDesInterets;

		double montantPaye = montantMensualite - partDesInterets;
		double montantRestant = montantDemande - montantPaye;

		Mensualite premiereMensualite = mensualiteService.ajouterMensualite(pret, capitalRembourse, partDesInterets);
		premiereMensualite.setDatePrelevement(datePrelevement);
		pret.getMensualites().add(premiereMensualite);
		
		for (int i = 1; i < nbMois; i++) {
			
		    partDesInterets = montantRestant * tauxMensuel;
			montantPaye = montantMensualite - partDesInterets;
			montantRestant = montantRestant - montantPaye;
			
			capitalRembourse = pret.getMensualites().get(i-1).getPartCapitalRembourse() + montantMensualite - partDesInterets;

			Mensualite nouvelleMensualite = mensualiteService.ajouterMensualite(pret, capitalRembourse, partDesInterets);
			datePrelevement = datePrelevement.plusMonths(1);
			nouvelleMensualite.setDatePrelevement(datePrelevement);
			pret.getMensualites().add(nouvelleMensualite);
		}
		
		// On ajoute le nouveau pret à la liste de prêts
		prets.add(pret);
		return pret;
	}

	@Override
	public Pret ajouterPret(Pret pret) {
		prets.add(pret);
		return pret;
	}
	
	@Override
	public List<Pret> recupererPrets() {
		return prets;
	}

	@Override
	public Pret recupererPret(Long id) {
		for (Pret pret : prets) {
			if (pret.getId().equals(id)) {
				return pret;
			}
		}
		return null;
	}

	@Override
	public void trierPretsParMontantDecroissant() {
		Collections.sort(prets);
	}

	@Override
	public void trierPretsParTauxDecroissant() {
		Collections.sort(prets, new ComparateurDePretsSurTaux());
	}

	@Override
	public List<Pret> recupererPrets(LocalDate dateDebut, LocalDate dateFin) {
		List<Pret> pretsCorrespondants = new ArrayList<>();
		for (Pret pret : prets) {
			if ((pret.getDateEffet().equals(dateDebut) || pret.getDateEffet().isAfter(dateDebut))
					&& (pret.getDateEffet().equals(dateFin) || pret.getDateEffet().isBefore(dateFin))) {
				pretsCorrespondants.add(pret);
			}
		}
		return pretsCorrespondants;
	}

	@Override
    public Long recupererDernierId() {
        Long id = 0L;
        for (Pret pret : prets) {
            if (pret.getId() > id) {
                id = pret.getId();
            }
        }
        return id;
    }

}
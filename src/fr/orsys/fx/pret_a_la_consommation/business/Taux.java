package fr.orsys.fx.pret_a_la_consommation.business;

import java.io.Serializable;
import java.util.List;

public class Taux implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private double valeur;
	
	private Duree duree;
	
	private Motif motif;
	
	private List<Pret> prets;

	private static Long compteur = 0L;

	public Taux(double valeur, Duree duree, Motif motif) {
		id = ++compteur;
		this.valeur = valeur;
		this.duree = duree;
		this.motif = motif;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public Duree getDuree() {
		return duree;
	}

	public void setDuree(Duree duree) {
		this.duree = duree;
	}

	public Motif getMotif() {
		return motif;
	}

	public void setMotif(Motif motif) {
		this.motif = motif;
	}

	public List<Pret> getPrets() {
		return prets;
	}

	public void setPrets(List<Pret> prets) {
		this.prets = prets;
	}

	public static Long getCompteur() {
		return compteur;
	}

	public static void setCompteur(Long compteur) {
		Taux.compteur = compteur;
	}

	@Override
	public String toString() {
		return "Taux [id=" + id + ", valeur=" + valeur + ", duree=" + duree + ", motif=" + motif + "]";
	}

}

package fr.orsys.fx.pret_a_la_consommation.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Motif implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nom;
	private String description;
	
	private List<Taux> taux;
	
	private static Long compteur = 0L;

	public Motif(String nom) {
		id = ++compteur;
		taux = new ArrayList<>();
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Taux> getTaux() {
		return taux;
	}

	public void setTaux(List<Taux> taux) {
		this.taux = taux;
	}

	@Override
	public String toString() {
		return "Motif [id=" + id + ", nom=" + nom + ", description=" + description + "]";
	}

}
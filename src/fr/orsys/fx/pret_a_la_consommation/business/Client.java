package fr.orsys.fx.pret_a_la_consommation.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nom;
	private String prenom;

	private List<Pret> prets;
	
	private static Long compteur = 0L;

	public Client(String nom, String prenom) {
		id = ++compteur;
		prets = new ArrayList<>();
		this.nom = nom;
		this.prenom = prenom;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Pret> getPrets() {
		return prets;
	}

	public void setPrets(List<Pret> prets) {
		this.prets = prets;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}

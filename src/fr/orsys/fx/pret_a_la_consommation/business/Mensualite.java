package fr.orsys.fx.pret_a_la_consommation.business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Mensualite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private LocalDate datePrelevement;
	
	private double partInteretsRembourses;
	
	private double partCapitalRembourse;
	
	private Pret pret;
	
	private static Long compteur = 0L;

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
	
	public Mensualite(Pret pret) {
		id = ++compteur;
	}
	
	public Mensualite(Pret pret, double partCapitalRembourse, double partInteretsRembourses) {
		this(pret);
		this.partCapitalRembourse = partCapitalRembourse;
		this.partInteretsRembourses = partInteretsRembourses;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDatePrelevement() {
		return datePrelevement;
	}

	public void setDatePrelevement(LocalDate datePrelevement) {
		this.datePrelevement = datePrelevement;
	}

	public double getPartInteretsRembourses() {
		return partInteretsRembourses;
	}

	public void setPartInteretsRembourses(double partInteretsRembourses) {
		this.partInteretsRembourses = partInteretsRembourses;
	}

	public double getPartCapitalRembourse() {
		return partCapitalRembourse;
	}

	public void setPartCapitalRembourse(double partCapitalRembourse) {
		this.partCapitalRembourse = partCapitalRembourse;
	}

	public Pret getPret() {
		return pret;
	}

	public void setPret(Pret pret) {
		this.pret = pret;
	}

	public static Long getCompteur() {
		return compteur;
	}

	public static void setCompteur(Long compteur) {
		Mensualite.compteur = compteur;
	}

	@Override
	public String toString() {
		return "Mensualite [id=" + id + ", datePrelevement=" + simpleDateFormat.format(datePrelevement) + ", partInteretsRembourses="
				+ partInteretsRembourses + ", partCapitalRembourse=" + partCapitalRembourse + "]";
	}

	
}

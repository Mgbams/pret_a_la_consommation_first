package fr.orsys.fx.pret_a_la_consommation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import fr.orsys.fx.pret_a_la_consommation.business.Client;
import fr.orsys.fx.pret_a_la_consommation.business.Duree;
import fr.orsys.fx.pret_a_la_consommation.business.Mensualite;
import fr.orsys.fx.pret_a_la_consommation.business.Motif;
import fr.orsys.fx.pret_a_la_consommation.business.Pret;
import fr.orsys.fx.pret_a_la_consommation.business.Taux;
import fr.orsys.fx.pret_a_la_consommation.exceptions.DateEffetInvalideException;
import fr.orsys.fx.pret_a_la_consommation.exceptions.MontantExcessifException;
import fr.orsys.fx.pret_a_la_consommation.service.ClientService;
import fr.orsys.fx.pret_a_la_consommation.service.DureeService;
import fr.orsys.fx.pret_a_la_consommation.service.MotifService;
import fr.orsys.fx.pret_a_la_consommation.service.PretService;
import fr.orsys.fx.pret_a_la_consommation.service.TauxService;
import fr.orsys.fx.pret_a_la_consommation.service.impl.ClientServiceImpl;
import fr.orsys.fx.pret_a_la_consommation.service.impl.DureeServiceImpl;
import fr.orsys.fx.pret_a_la_consommation.service.impl.MotifServiceImpl;
import fr.orsys.fx.pret_a_la_consommation.service.impl.PretServiceImpl;
import fr.orsys.fx.pret_a_la_consommation.service.impl.TauxServiceImpl;

public class App {

	private static final int AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT = 1;
	private static final int AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT = 2;
	private static final int AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES = 3;
	private static final int AJOUTER_PRET = 4;
	private static final int QUITTER = 5;

	private static DureeService dureeService = new DureeServiceImpl();
	private static MotifService motifService = new MotifServiceImpl();
	private static TauxService tauxService = new TauxServiceImpl();
	private static PretService pretService = new PretServiceImpl();
	private static ClientService clientService = new ClientServiceImpl();
	private static Scanner scanner = new Scanner(System.in);

	private static final String FORMAT_DATE = "dd/MM/yyyy";
	private static final String FORMAT_DATE_MOIS_ANNEE = "MM/yyyy";
	private static final String NOM_FICHIER_BINAIRE = "donnees.bin";

	private static SimpleDateFormat simpleDateFormatMensualite = new SimpleDateFormat(FORMAT_DATE_MOIS_ANNEE);
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
	
	public static void main(String[] args) {
		loggerLancement();
		lireFichierBinaire();
		
		if (motifService.recupererMotifs().isEmpty()) {
			ajouterMotifs();			
		}

		if (dureeService.recupererDurees().isEmpty()) {
			ajouterDurees();			
		}

		if (tauxService.recupererTaux().isEmpty()) {
			ajouterTaux();			
		}

		if (clientService.recupererClients().isEmpty()) {
			ajouterClients();
		}
		
		System.out.println("Bienvenue sur pret à la consommation");

		while (true) {
			afficherMenuPrincipal();
			int choix = demanderChoix("Faites votre choix : ", 1, QUITTER);
			switch (choix) {
			case AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT:
				pretService.trierPretsParMontantDecroissant();
				afficherPrets();
				break;
			case AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT:
				pretService.trierPretsParTauxDecroissant();
				afficherPrets();
				break;
			case AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES:
				LocalDate dateDebut = demanderDate("Entrez la date de début au format " + FORMAT_DATE + " : ", simpleDateFormat);
				LocalDate dateFin = demanderDate("Entrez la date de fin au format " + FORMAT_DATE + " : ", simpleDateFormat);
				afficherPrets(dateDebut, dateFin);
				break;
			case AJOUTER_PRET:
				ajouterPret();
				break;
			case QUITTER:
				System.out.println("Au revoir");
				scanner.close();
				ecrireFichierBinaire();
				System.exit(0);
				break;
			default:
				break;
			}
		}

	}

	private static void loggerLancement() {
		try {
			File file = new File("log/lancements.txt");
			if (!file.exists()) {
				Path path = Paths.get("log");
				Files.createDirectories(path);
			}
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(LocalDateTime.now() + " lancement de l'application\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void afficherPrets(LocalDate dateDebut, LocalDate dateFin) {
		for (Pret pret : pretService.recupererPrets(dateDebut, dateFin)) {
			System.out.println(pret);
		}
	}

	private static void afficherPrets() {
		for (Pret pret : pretService.recupererPrets()) {
			afficherPret(pret);
		}
	}

	private static void afficherPret(Pret pret) {
		float totalInterets = 0;
		
		System.out.println("Voici les détails du pret : id : Pret " + pret.getId() + ", client : "
				+ pret.getClient().getPrenom() + " " + pret.getClient().getNom().toUpperCase() + ", montant emprunté : "
				+ formaterMontant(pret.getMontantDemande()) + ", mensualité : "
				+ formaterMontant(pret.getMontantMensualite()));
		System.out.println("Date\tCapital remboursÃ©\tPart des intÃ©rÃªts");
		
		for (Mensualite mensualite : pret.getMensualites()) {
			totalInterets += mensualite.getPartInteretsRembourses();
			System.out.println(mensualite.getDatePrelevement().getMonthValue() + "/" + mensualite.getDatePrelevement().getYear() + "\t"
					+ formaterMontant(mensualite.getPartCapitalRembourse()) + "\t\t\t"
					+ formaterMontant(mensualite.getPartInteretsRembourses()));
		}
		
		System.out.println("Total des interets : " + formaterMontant(totalInterets));
	}

	public static String formaterMontant(java.lang.Object object) {

		java.text.NumberFormat format;

		format = java.text.NumberFormat.getInstance();
		format.setMaximumFractionDigits(-1);
		((java.text.DecimalFormat) format).setDecimalSeparatorAlwaysShown(true);
		((java.text.DecimalFormat) format).applyPattern("#,##0.00");

		try {
			return format.format(object);
		} catch (java.lang.IllegalArgumentException e) {
			return "?";
		}

	}

	private static void afficherMenuPrincipal() {
		System.out.println(AFFICHER_PRETS_TRIES_PAR_MONTANT_DECROISSANT
				+ ". Voir tous les préts triées par montant (du plus élévé au plus petit)");
		System.out.println(AFFICHER_PRETS_TRIES_PAR_TAUX_DECROISSANT
				+ ". Voir tous les préts triées par taux (du plus élévé au plus petit)");
		System.out.println(AFFICHER_PRETS_DEBUTANT_ENTRE_DEUX_DATES
				+ ". Voir la liste des préts qui débutent entre deux dates données");
		System.out.println(AJOUTER_PRET + ". Ajouter un prét");
		System.out.println(QUITTER + ". Quitter");
	}

	private static void ajouterPret() {
		LocalDate dateEffet = null;
		Client clientChoisi = null;
		Taux tauxChoisi = null;

		afficherClients();
		clientChoisi = clientService.recupererClient((long) demanderChoix("Veuillez saisir l'id du client concerné : ",
				1, clientService.recupererClients().size()));

		System.out.print("Veuillez saisir le montant demandé : ");
		double montantDemande = Double.parseDouble(scanner.nextLine());

		afficherTaux();
		tauxChoisi = tauxService.recupererTaux((long) demanderChoix("Veuillez saisir l'id du taux annuel : ", 1, tauxService.recupererTaux().size()));

		dateEffet = demanderDate("Veuillez saisir la date d'effet au format " + FORMAT_DATE_MOIS_ANNEE + " : ", simpleDateFormatMensualite);

		Pret pret;
		try {
			pret = pretService.ajouterPret(tauxChoisi, clientChoisi, montantDemande, dateEffet);
			afficherPret(pret);
		} catch (MontantExcessifException e) {
			// On arrive dans ce bloc car l'utilisateur a saisi un montant qui dépasse le montant maxi
			System.out.println(e.getMessage());
		} catch (DateEffetInvalideException e) {
			// On arrive dans ce bloc car l'utilisateur a saisi une date d'effet antérieure à aujourd'hui
			System.out.println(e.getMessage());
		} finally {
			
		}
	}

	private static void afficherClients() {
		for (Client client : clientService.recupererClients()) {
			System.out.println(client.getId() + ". " + client.getNom().toUpperCase() + " " + client.getPrenom());
		}

	}

	private static void afficherTaux() {
		for (Taux taux : tauxService.recupererTaux()) {
			System.out.println(taux.getId() + ". " + taux.getValeur() * 100 + " % sur "
					+ taux.getDuree().getDureeEnMois() + " mois pour " + taux.getMotif().getNom());
		}
	}

	private static void ajouterDurees() {
		dureeService.ajouterDuree(12);
		dureeService.ajouterDuree(24);
	}
	
	private static void ajouterMotifs() {
		motifService.ajouterMotif("Auto");
		motifService.ajouterMotif("Moto");
	}
	
	private static void ajouterTaux() {
		tauxService.ajouterTaux(0.01, dureeService.recupererDuree(1L), motifService.recupererMotif(1L));
		tauxService.ajouterTaux(0.015, dureeService.recupererDuree(2L), motifService.recupererMotif(1L));
		tauxService.ajouterTaux(0.02, dureeService.recupererDuree(1L), motifService.recupererMotif(2L));
		tauxService.ajouterTaux(0.025, dureeService.recupererDuree(2L), motifService.recupererMotif(2L));

	}

	private static void ajouterClients() {
		// On ajoute 5 clients à l'aide du boucle
		for (int i = 0; i < 5; i++) {
			clientService.ajouterClient("Nom" + i, "Prenom" + i);
		}
	}

	private static int demanderChoix(String message, int borneMin, int borneMax) {
		int valeur = borneMin - 1;
		// Utilisation d'une boucle do while
		// Le code dans le do sera executé au moins une fois
		do {
			System.out.print(message);
			try {
				String saisie = scanner.nextLine();
				valeur = Integer.parseInt(saisie);
				if (valeur < borneMin || valeur > borneMax) {
					System.out.println("Merci de saisir un nombre compris entre " + borneMin + " et " + borneMax);
				}
			} catch (Exception e) {
				System.out.println("Merci de saisir un nombre");
			}

		} while (!(valeur >= borneMin && valeur <= borneMax));
		return valeur;
	}

	private static LocalDate demanderDate(String message, SimpleDateFormat simpleDateFormat) {
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		System.out.print(message);
		do {
			try {
				date = simpleDateFormat.parse(scanner.nextLine());
			} catch (ParseException e) {
				System.out.println(message + " " + e);
			}
		} while (date == null);
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
	}

    @SuppressWarnings("unchecked")
	private static void lireFichierBinaire() {
        File fichier = new File(NOM_FICHIER_BINAIRE);
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Object> objects;

        try {
            fis = new FileInputStream(fichier);
            ois = new ObjectInputStream(fis);

            objects = (List<Object>) ois.readObject();
            for (Object object : objects) {
            	// Pour chaque objet on invoque la méthode getClass() pour savoir à  quelle classe l'objet appartient
                switch (object.getClass().getSimpleName()) {
                case "Client":
                    clientService.ajouterClient((Client) object);
                    break;
                case "Pret":
                    pretService.ajouterPret((Pret) object);
                    break;
                case "Duree":
                    dureeService.ajouterDuree((Duree) object);
                    break;
                case "Motif":
                    motifService.ajouterMotif((Motif) object);
                    break;
                case "Taux":
                    tauxService.ajouterTaux((Taux) object);
                    break;
                default:
                    break;
                }
            }
            System.out.println("Nombre d'objets lus : " + objects.size());
            Pret.setCompteur(pretService.recupererDernierId());
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }
	
	private static void ecrireFichierBinaire() {
        File fichier = new File(NOM_FICHIER_BINAIRE);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        List<Object> objects;

        try {
            fos = new FileOutputStream(fichier);
            oos = new ObjectOutputStream(fos);

            objects = new ArrayList<Object>();
            for (Client client : clientService.recupererClients()) {
            	objects.add(client);				
			}
            for (Pret pret : pretService.recupererPrets()) {
            	objects.add(pret);				
			}
            for (Motif motif : motifService.recupererMotifs()) {
            	objects.add(motif);				
			}     
            for (Duree duree : dureeService.recupererDurees()) {
            	objects.add(duree);				
			}
            for (Taux taux : tauxService.recupererTaux()) {
            	objects.add(taux);				
			}
            oos.writeObject(objects);
        } catch (IOException e) {
            System.out.print(e.toString());
        } finally {
        	// Ce bloc sera exécuté dans tous les cas
        	try {
				oos.close();
				fos.close();        	
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

}
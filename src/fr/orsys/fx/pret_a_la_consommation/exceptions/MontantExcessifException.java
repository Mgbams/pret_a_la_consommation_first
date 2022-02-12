package fr.orsys.fx.pret_a_la_consommation.exceptions;

/**
 * Cette exception sera levée si le montant du prêt dépasse 20k €
 * 
 * @author fxcote
 *
 */
public class MontantExcessifException extends Exception {



	/*
	 * Chaque objet de type MontantExcessifException va recevoir un coup de tampon
	 * correspondant à la version de l'exception
	 */
	private static final long serialVersionUID = 1L;

	public MontantExcessifException(String message) {
		super(message);
	}
}

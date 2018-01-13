package mainPackage.gameEngine.event;

import java.util.Random;

import mainPackage.gameEngine.Engine;

public class ListEvent {

	private static Event[] listeEventDate = new Event[15]; // 15 et 78 a automatiser
	private static Event[] listeEvent = new Event[78];

	public static final int INDICE_NOM = 0;
	public static final int INDICE_PROBA = 1;

	public static Event trouverEvent(String nom)// Trouve un event a partir de son nom
	{
		Event evenement = new Event();
		evenement.setNom("dhbfuevdbu<kvcuECVufvguef"); // Initialisation du nom
														// peu probable qu'il
														// soit le meme

		for (int i = 0; i < ListEvent.listeEventDate.length; i++) {
			if (ListEvent.listeEventDate[i].getNom() == nom) {
				evenement = ListEvent.listeEventDate[i];
			}
		}
		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			if (ListEvent.listeEvent[i].getNom() == nom) {
				evenement = ListEvent.listeEvent[i];
			}
		}

		return evenement;
	}

	public static int probaTotal() // Renvoie la somme des proba de tous les events
	{
		int total = 0;
		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			total = total + ListEvent.listeEvent[i].getProbabilite();
		}
		return total;
	}

	public static Event choisisEvent() // Prend l'evenement pour le jour meme
	{
		Event evenementChoisi = null;
		int proba = 0;

		Random rand = new Random();
		int nbrAlea = rand.nextInt(probaTotal());

		for (int i = 0; nbrAlea >= proba; i++) {
			proba += ListEvent.listeEvent[i].getProbabilite();
			if (nbrAlea < proba) {
				evenementChoisi = ListEvent.listeEvent[i];
			}
		}

		for (int i = 0; i < listeEventDate.length; i++) {
			if (listeEventDate[i].getDate() == Engine.journee.getDate()) {
				evenementChoisi = listeEventDate[i];
			}
		}
		
		if (evenementChoisi.getOccurence() != -1) {
			evenementChoisi.setOccurence(evenementChoisi.getOccurence() - 1);
		}

		return evenementChoisi;
	}

	public static Event[] getListeEvent() {
		return ListEvent.listeEvent;
	}

	public static Event[] getListeEventDate() {
		return ListEvent.listeEventDate;
	}

	public static void setListeEvent(Event[] listeEvent) {
		ListEvent.listeEvent = listeEvent;
	}

	public static void setListeEventDate(Event[] listeEventDate) {
		ListEvent.listeEventDate = listeEventDate;
	}
}

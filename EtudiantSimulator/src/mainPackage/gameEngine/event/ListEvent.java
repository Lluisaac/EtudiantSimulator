package mainPackage.gameEngine.event;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class ListEvent {

	private static Event[] listeEventDate = new Event[0]; // 0 et 0 a automatiser
	private static Event[] listeEvent = new Event[0];

	public static final int INDICE_NOM = 0;
	public static final int INDICE_DATE= 1;
	public static final int INDICE_PROBA = 2;
	public static final int INDICE_OCCURENCE = 3;
	public static final int INDICE_JOURS_RESTANTS =4 ;
	
	public static void mettreListEvent()
	{
		//Exemple de event classic dans le fichier texte( modif possible):
		//"mamie":"elle est morte":"la mort":"7":"2"
		//"heritage":"":"170":"1":"5,0"
		//ou/et
		//"feteDesMorts","03/11/2018","","1",""
		
		//Rappel pour l'occurence on l'ajoute a ce qu'il y as de base.
	}
	
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
		ModificateurEvent.applicateur(evenementChoisi);
		return evenementChoisi;
	}
	
	public Date chercheDateDispo()
	{
		boolean verification=true;
		Date date=Engine.journee.getDate().dateJourSuivant();
		
		do
		{
			date.addJour(1);
			for(int i=0;i<listeEventDate.length;i++)
			{
				if(ListEvent.listeEventDate[i].getDate()==date)
				{
					verification=false;
				}
			}
		}while(verification);
		return date;
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

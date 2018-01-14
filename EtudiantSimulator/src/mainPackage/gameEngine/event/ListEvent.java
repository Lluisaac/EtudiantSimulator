package mainPackage.gameEngine.event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class ListEvent {

	private static ArrayList<Event> listeEventDate;
	private static ArrayList<Event> listeEvent;

	public static void mettreListEvent() {		
		//Syntaxe: 
		//Séparation pour chaque event: \n
		//Séparation pour chaque attribut: ;
		//Séparation pour chaque Sous-attribut (tableau, liste...): _
		//Séparation auxilière pour chaque sous-sous-attribut: |
		
		//nom,description,archetype,date,occurence,probabilite,joursrestants,acces
		
		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("events.etsim");

			byte[] buffer = new byte[8];

			while (file.read(buffer) >= 0) {
				for (byte byt : buffer) {
					content += (char) byt;
				}
			}

			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] contentTab1 = content.split("\n");
		
		ListEvent.listeEvent = new ArrayList<Event>();
		ListEvent.listeEventDate = new ArrayList<Event>();
		
		for (int i = 0; i < contentTab1.length - 1; i++) {
			String[] contentTab2 = contentTab1[i].split(";");
			
			Event newEvent;
			
			if(contentTab2[3].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], new Date(contentTab2[3]), Integer.parseInt(contentTab2[4]), ModificateurEvent.creatArrayFromString(contentTab2[7]));
				ListEvent.listeEvent.add(newEvent);
			} else if (contentTab2[5].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], new Date(contentTab2[4]), Integer.parseInt(contentTab2[5]), ModificateurEvent.creatArrayFromString(contentTab2[7]));
				ListEvent.listeEventDate.add(newEvent);
			} else {
				newEvent = new Event();
			}
			
			if (!contentTab2[6].equals("")) {
				String[] contentTab3 = contentTab2[6].split("_");
				int[] tabJoursRestants = {Integer.parseInt(contentTab3[0]), Integer.parseInt(contentTab3[1])};
				newEvent.setJoursRestantsProbaAjoutee(tabJoursRestants);
			}			
		}
	}

	public static Event trouverEvent(String nom)// Trouve un event a partir de
												// son nom
	{

		for (int i = 0; i < ListEvent.listeEventDate.size(); i++) {
			if (ListEvent.listeEventDate.get(i).getNom() == nom) {
				return ListEvent.listeEventDate.get(i);
			}
		}
		
		for (int i = 0; i < ListEvent.listeEvent.size(); i++) {
			if (ListEvent.listeEvent.get(i).getNom() == nom) {
				return ListEvent.listeEvent.get(i);
			}
		}
		
		return null; //L'evenement n'existe pas
	}

	public static int probaTotal() // Renvoie la somme des proba de tous les
									// events
	{
		int total = 0;
		for (int i = 0; i < ListEvent.listeEvent.size(); i++) {
			total = total + ListEvent.listeEvent.get(i).getProbabilite();
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
			proba += ListEvent.listeEvent.get(i).getProbabilite();
			if (nbrAlea < proba) {
				evenementChoisi = ListEvent.listeEvent.get(i);
			}
		}

		for (int i = 0; i < listeEventDate.size(); i++) {
			if (listeEventDate.get(i).getDate() == Engine.journee.getDate()) {
				evenementChoisi = listeEventDate.get(i);
			}
		}

		if (evenementChoisi.getOccurence() != -1) {
			evenementChoisi.setOccurence(evenementChoisi.getOccurence() - 1);
		}
		evenementChoisi.executer();
		return evenementChoisi;
	}

	public Date chercheDateDispo() {
		boolean verification = true;
		Date date = Engine.journee.getDate().dateJourSuivant();

		do {
			date.addJour(1);
			for (int i = 0; i < listeEventDate.size(); i++) {
				if (ListEvent.listeEventDate.get(i).getDate() == date) {
					verification = false;
				}
			}
		} while (verification);
		return date;
	}

	public static void applicateur(Event evenement)
	{
//		Event evenementModif;
//		for (int i = 0; i < evenement.getAcces().size(); i++) {
//			evenementModif = ListEvent.trouverEvent(evenement.getAcces().get(i).get(ListEvent.INDICE_NOM));
//
//			if (evenementModif.getDate() == null) {
//				evenementModif.setProbabilite(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_PROBA)));
//				evenementModif.setOccurence(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_OCCURENCE)));
//				evenementModif.setJoursRestantsProbaAjoutee(evenement.getAcces().get(i).get(ListEvent.INDICE_JOURS_RESTANTS));
//
//			}
//			else
//			{
//				evenementModif.setDate( evenement.getAcces().get(i).get(ListEvent.INDICE_DATE) );
//				evenementModif.setOccurence(Integer.parseInt(evenement.getAcces().get(i).get( evenementModif.getOccurence()+ListEvent.INDICE_OCCURENCE )));
//			}
//		}
		
	}

	public static void regulateur()// Verifie que des probas ne doivent pas etre
									// ajuste par occurence, vérifie les jours restants et regule
	{
		int[] jourRestant=new int[2];
		for(int i=0;i<ListEvent.getListeEvent().size();i++)
		{
			jourRestant[0]=ListEvent.getListeEvent().get(i).getJoursRestantsProbaAjoutee()[0]-1;
			jourRestant[1]=ListEvent.getListeEvent().get(i).getJoursRestantsProbaAjoutee()[1];
			
			if(ListEvent.getListeEvent().get(i).getOccurence()==0 )
			{
				ListEvent.getListeEvent().get(i).setProbabilite(0);
			}
			
			switch(ListEvent.getListeEvent().get(i).getJoursRestantsProbaAjoutee()[0])//A faire
			{
				case(0):
				case(-1):
				break;
				case(1):
					ListEvent.getListeEvent().get(i).setJoursRestantsProbaAjoutee( jourRestant );
					ListEvent.getListeEvent().get(i).setProbabilite(jourRestant[1]);
				default:
					ListEvent.getListeEvent().get(i).setJoursRestantsProbaAjoutee( jourRestant );
			}
			
		}
	}
	
	public static ArrayList<Event> getListeEvent() {
		return ListEvent.listeEvent;
	}

	public static ArrayList<Event> getListeEventDate() {
		return ListEvent.listeEventDate;
	}

	public static void setListeEvent(ArrayList<Event> listeEvent) {
		ListEvent.listeEvent = listeEvent;
	}

	public static void setListeEventDate(ArrayList<Event> listeEventDate) {
		ListEvent.listeEventDate = listeEventDate;
	}
}

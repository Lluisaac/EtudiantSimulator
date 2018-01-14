package mainPackage.gameEngine.event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class ListEvent {

	private static Event[] listeEventDate;
	private static Event[] listeEvent;

	public static void mettreListEvent() {		
		//Syntaxe: 
		//S�paration pour chaque event: \n
		//S�paration pour chaque attribut: ;
		//S�paration pour chaque Sous-attribut (tableau, liste...): _
		//S�paration auxili�re pour chaque sous-sous-attribut: |
		
		//nom;description;archetype;date;occurence;probabilite;joursrestants;acces;
		//PCCass�;Votre PC est cass�;broken;;-1;5;;GoReparateur_5/1/1_0_15;
		//L'evenement PCCass� peut arriver une infinit� de fois, a une probabilit� de 5 et va augmenter de 15 la probabilit� de GoReparateur pendant 4 jours.
		
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
		
		ArrayList<Event> temp = new ArrayList<Event>();
		ArrayList<Event> tempDate = new ArrayList<Event>();
		
		for (int i = 0; i < contentTab1.length - 1; i++) {
			String[] contentTab2 = contentTab1[i].split(";");
			
			Event newEvent;
			
			if(contentTab2[3].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], new Date(contentTab2[3]), Integer.parseInt(contentTab2[4]), ModificateurEvent.createArrayFromString(contentTab2[7]));
				temp.add(newEvent);
			} else if (contentTab2[5].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], new Date(contentTab2[4]), Integer.parseInt(contentTab2[5]), ModificateurEvent.createArrayFromString(contentTab2[7]));
				tempDate.add(newEvent);
			} else {
				newEvent = new Event();
			}
			
			if (!contentTab2[6].equals("")) {
				String[] contentTab3 = contentTab2[6].split("_");
				int[] tabJoursRestants = {Integer.parseInt(contentTab3[0]), Integer.parseInt(contentTab3[1])};
				newEvent.setJoursRestantsProbaAjoutee(tabJoursRestants);
			}			
		}
		
		ListEvent.fromListToArray(temp, tempDate);
	}

	public static void fromListToArray(ArrayList<Event> liste, ArrayList<Event> listeDate) {
		ListEvent.listeEvent = new Event[liste.size()];
		ListEvent.listeEventDate = new Event[listeDate.size()];
		
		for (int i = 0; i < liste.size(); i++) {
			ListEvent.listeEvent[i] = liste.get(i);
		}
		
		for (int i = 0; i < listeDate.size(); i++) {
			ListEvent.listeEventDate[i] = listeDate.get(i);
		}
	}
	
	public static Event trouverEvent(String nom)// Trouve un event a partir de
												// son nom
	{

		for (int i = 0; i < ListEvent.listeEventDate.length; i++) {
			if (ListEvent.listeEventDate[i].getNom() == nom) {
				return ListEvent.listeEventDate[i];
			}
		}
		
		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			if (ListEvent.listeEvent[i].getNom() == nom) {
				return ListEvent.listeEvent[i];
			}
		}
		
		return null; //L'evenement n'existe pas
	}

	public static int probaTotal() // Renvoie la somme des proba de tous les
									// events
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
		evenementChoisi.executer();
		return evenementChoisi;
	}

	public Date chercheDateDispo() {
		boolean verification = true;
		Date date = Engine.journee.getDate().dateJourSuivant();

		do {
			date.addJour(1);
			for (int i = 0; i < listeEventDate.length; i++) {
				if (ListEvent.listeEventDate[i].getDate() == date) {
					verification = false;
				}
			}
		} while (verification);
		return date;
	}

	public static void regulateur()// Verifie que des probas ne doivent pas etre
									// ajuste par occurence, v�rifie les jours restants et regule
	{
		int[] jourRestant=new int[2];
		for(int i=0;i<ListEvent.getListeEvent().length;i++)
		{
			jourRestant[0]=ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0]-1;
			jourRestant[1]=ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[1];
			
			if(ListEvent.getListeEvent()[i].getOccurence()==0 )
			{
				ListEvent.getListeEvent()[i].setProbabilite(0);
			}
			
			switch(ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0])//A faire
			{
				case(0):
				case(-1):
				break;
				case(1):
					ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee( jourRestant );
					ListEvent.getListeEvent()[i].setProbabilite(jourRestant[1]);
				default:
					ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee( jourRestant );
			}
			
		}
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

package mainPackage.gameEngine.event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.graphicsEngine.dialog.EventDialog;

public class ListEvent {

	private static Event[] listeEventDate;
	private static Event[] listeEvent;

	public static void mettreListEvent() {
		// Syntaxe:
		// Séparation pour chaque event: \n
		// Séparation pour chaque attribut: ;
		// Séparation pour chaque Sous-attribut (tableau, liste...): _
		// Séparation auxilière pour chaque sous-sous-attribut: |

		// nom;description;archetype;date;occurence;probabilite;joursrestants;acces;
		// PCCassé;Votre PC est cassé;broken;;-1;5;;GoReparateur_5/1/1_0_15;
		// L'evenement PCCassé peut arriver une infinité de fois, a une
		// probabilité de 5 et va augmenter de 15 la probabilité de GoReparateur
		// pendant 4 jours.

		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("listes\\events.etsim");

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

		for (int i = 0; i < contentTab1.length; i++) {
			String[] contentTab2 = contentTab1[i].split(";", -1);
			Event newEvent;

			if (!contentTab2[3].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], new Date(contentTab2[3]),
						Integer.parseInt(contentTab2[4]), ModificateurEvent.createArrayFromString(contentTab2[7]),
						contentTab2[8].split("_"));
				tempDate.add(newEvent);
			} else if (!contentTab2[5].equals("")) {
				newEvent = new Event(contentTab2[0], contentTab2[1], contentTab2[2], Integer.parseInt(contentTab2[4]),
						Integer.parseInt(contentTab2[5]), ModificateurEvent.createArrayFromString(contentTab2[7]),
						contentTab2[8].split("_"));
				temp.add(newEvent);
			} else {
				newEvent = new Event();
			}

			if (!contentTab2[6].equals("")) {
				String[] contentTab3 = contentTab2[6].split("_");
				int[] tabJoursRestants = { Integer.parseInt(contentTab3[0]), Integer.parseInt(contentTab3[1]) };
				newEvent.setJoursRestantsProbaAjoutee(tabJoursRestants);
			}
		}

		ListEvent.fromListToArray(temp, tempDate);
		ListEvent.createTampon();
	}

	private static void createTampon() {
		ListEvent.listeEvent[ListEvent.listeEvent.length - 1] = new Event("Blank", "Blank", "Blank", -1,
				ListEvent.sommeProbas() * 3, null, null);
	}

	private static int sommeProbas() {
		int val = 0;
		for (int i = 0; i < ListEvent.listeEvent.length - 1; i++) {
			val += ListEvent.listeEvent[i].getProbabilite();
		}
		return val;
	}

	public static void fromListToArray(ArrayList<Event> liste, ArrayList<Event> listeDate) {
		ListEvent.listeEvent = new Event[liste.size() + 1];
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

		return null; // L'evenement n'existe pas
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
			if (ListEvent.listeEvent[i].getOccurence() != 0) {
				proba += ListEvent.listeEvent[i].getProbabilite();
				if (nbrAlea < proba) {
					evenementChoisi = ListEvent.listeEvent[i];
				}
			}
		}

		for (int i = 0; i < listeEventDate.length || i < listeEvent.length; i++) {
			if (ListEvent.listeEvent[i].getOccurence() != 0) {
				if (i < listeEventDate.length && listeEventDate[i].getDate() == Engine.journee.getDate()) {
					evenementChoisi = listeEventDate[i];
				}
				if (i < listeEvent.length && listeEvent[i].getDate() == Engine.journee.getDate()) {
					evenementChoisi = listeEvent[i];
				}
			}
		}

		if (evenementChoisi.getOccurence() != -1) {
			evenementChoisi.setOccurence(evenementChoisi.getOccurence() - 1);
		}

		if (evenementChoisi.getNom() != "Blank") {
			evenementChoisi.executer();
			return evenementChoisi;
		} else {
			return null;
		}
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
									// ajuste par occurence, vérifie les jours
									// restants et regule
	{
		int[] jourRestant = new int[2];
		for (int i = 0; i < ListEvent.getListeEvent().length; i++) {
			jourRestant[0] = ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0] - 1;
			jourRestant[1] = ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[1];

			switch (ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0])// A
																					// faire
			{
			case (0):
			case (-1):// Occurence infinie
				break;
			case (1):
				ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee(jourRestant);
				ListEvent.getListeEvent()[i]
						.setProbabilite(ListEvent.getListeEvent()[i].getProbabilite() - jourRestant[1]);
			default:
				ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee(jourRestant);
			}

		}
	}

	public static String staticToString() {
		String r = "Base:";
		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			r += ListEvent.listeEvent[i].toString() + "\n";
		}

		r += "Dates:";

		for (int i = 0; i < ListEvent.listeEventDate.length; i++) {
			r += ListEvent.listeEventDate[i].toString() + "\n";
		}

		return r;
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

	public static void afficherEvent(Event event) {
		Engine.eventDialog = new EventDialog(event.getNom(), event.getResume(), event.getArchetype());
	}
}

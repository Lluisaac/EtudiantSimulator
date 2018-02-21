package mainPackage.gameEngine.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.graphicsEngine.dialog.EventDialog;

public class ListEvent {

	private static Event[] listeEventDate;
	private static Event[] listeEvent;

	public static void mettreListEvent() throws SAXException, IOException, ParserConfigurationException {
		Element listEvents = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Engine.parseXML("listes\\events.xml")).getDocumentElement();
		
		NodeList events = listEvents.getChildNodes();
		
		ArrayList<Event> listeEventsTemp = new ArrayList<Event>();
		
		for (int i = 0; i < events.getLength(); i++) {
			listeEventsTemp.add(new Event(events.item(i)));
		}
		
		ListEvent.listeEventDate = ListEvent.toTabDate(listeEventsTemp);
		ListEvent.listeEvent = ListEvent.toTab(listeEventsTemp);
		
		ListEvent.createTampon();
	}

	private static Event[] toTab(ArrayList<Event> listeEventsTemp) {
		Event[] liste = new Event[ListEvent.countSansDate(listeEventsTemp) + 1];
		
		int compteur = 0;
		
		for (int i = 0; i < listeEventsTemp.size(); i++) {
			if (!listeEventsTemp.get(i).hasDate()) {
				liste[compteur] = listeEventsTemp.get(i);
				compteur++;
			}
		}
		
		return liste;
	}

	private static int countSansDate(ArrayList<Event> listeEventsTemp) {
		int compteur = 0;
		
		for (int i = 0; i < listeEventsTemp.size(); i++) {
			if (!listeEventsTemp.get(i).hasDate()) {
				compteur++;
			}
		}
		return compteur;
	}

	private static Event[] toTabDate(ArrayList<Event> listeEventsTemp) {
		Event[] liste = new Event[ListEvent.countAvecDate(listeEventsTemp)];
		
		int compteur = 0;
		
		for (int i = 0; i < listeEventsTemp.size(); i++) {
			if (listeEventsTemp.get(i).hasDate()) {
				liste[compteur] = listeEventsTemp.get(i);
				compteur++;
			}
		}
		
		return liste;
	}
	
	private static int countAvecDate(ArrayList<Event> listeEventsTemp) {
		return listeEventsTemp.size() - ListEvent.countSansDate(listeEventsTemp);
	}

	public static void createTampon() {
		ListEvent.listeEvent[ListEvent.listeEvent.length - 1] = new Event("Blank", "Blank", "Blank", -1,
				(ListEvent.sommeProbasSansTampon() * 4) + 1);
	}

	private static int sommeProbasSansTampon() {
		int val = 0;
		for (int i = 0; i < ListEvent.listeEvent.length - 1; i++) {
			val += ListEvent.listeEvent[i].getProbabilite();
		}
		return val;
	}

	private static int sommeProbas() {
		int val = 0;
		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			val += ListEvent.listeEvent[i].getProbabilite();
		}
		return val;
	}

	// Trouve un event a partir de son nom
	public static Event trouverEvent(String nom) {

		for (int i = 0; i < ListEvent.listeEventDate.length; i++) {
			if (ListEvent.listeEventDate[i].getNom().equals(nom)) {
				return ListEvent.listeEventDate[i];
			}
		}

		for (int i = 0; i < ListEvent.listeEvent.length; i++) {
			if (ListEvent.listeEvent[i].getNom().equals(nom)) {
				return ListEvent.listeEvent[i];
			}
		}

		return null; // L'evenement n'existe pas
	}

	public static Event choisisEvent() // Prend l'evenement pour le jour meme
	{
		Event evenementChoisi = null;
		int proba = 0;

		Random rand = new Random();
		int nbrAlea = rand.nextInt(sommeProbas() != 0 ? sommeProbas() : 1);

		for (int i = 0; nbrAlea >= proba; i++) {
			if (ListEvent.listeEvent[i].getOccurence() != 0) {
				proba += ListEvent.listeEvent[i].getProbabilite();
				if (nbrAlea < proba) {
					evenementChoisi = ListEvent.listeEvent[i];
				}
			}
		}

		for (int i = 0; i < listeEventDate.length; i++) {
			if (listeEventDate[i].getDate().equals(Engine.journee.getDate())) {
				evenementChoisi = listeEventDate[i];
			}
		}

		if (!(evenementChoisi.getNom().equals("Blank"))) {
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
		System.out.println(event.getProbabilite());
		Engine.eventDialog = new EventDialog(event);
	}
}

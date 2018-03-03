package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mainPackage.gameEngine.event.Event;
import mainPackage.gameEngine.event.ListEvent;
import mainPackage.gameEngine.jour.Date;

public class ModificateurEvent {

	private String nom;

	private String date;

	private int occurence;

	private int proba;

	private ArrayList<ModificateurEvent> defaultEvent;
	private ArrayList<ModificateurObjet> defaultObjet;
	private ModificateurPlayer defaultPlayer;

	private ArrayList<ModificateurGeneral> accesChoix;

	public ModificateurEvent(Node item) {
		NamedNodeMap attributs = item.getAttributes();
		
		this.nom = attributs.getNamedItem("nom").getNodeValue();

		if (attributs.getNamedItem("date") != null) {
			this.date = attributs.getNamedItem("date").getNodeValue();
		}

		this.occurence = Integer.parseInt(attributs.getNamedItem("occurence").getNodeValue());
		this.proba = Integer.parseInt(attributs.getNamedItem("probabilite").getNodeValue());

		NodeList dansEvent = item.getChildNodes();
		int compteur = 1;

		if (dansEvent.getLength() > 0) {
			if (dansEvent.item(0).getNodeName().equals("default")) {
				this.genererDefault(dansEvent.item(0));
			} else {
				compteur = 0;
			}
		}

		this.accesChoix = ModificateurGeneral.fromNodesToArray(compteur, dansEvent);
	}

	public void appliquer() {
		Event temp = ListEvent.trouverEvent(this.nom);

		if (temp != null) {
			temp.setOccurence(temp.getOccurence() + this.occurence);
			temp.setProbabilite(temp.getProbabilite() + this.proba);

			if (this.date != null) {
				if (this.proba == 0) {
					temp.setDate(new Date(this.date));
				} else {
					int[] tab = { new Date().differenceDate(new Date(this.date)), this.proba };
					temp.setJoursRestantsProbaAjoutee(tab);
				}
			}

			temp.setDefaultEvent(this.defaultEvent);
			temp.setDefaultObjet(this.defaultObjet);
			temp.setDefaultPlayer(this.defaultPlayer);

			temp.setAccesChoix(this.accesChoix);
		}
	}

	private void genererDefault(Node item) {
		NodeList dansDefault = item.getChildNodes();

		for (int i = 0; i < dansDefault.getLength(); i++) {
			switch (dansDefault.item(i).getNodeName()) {
			case "modifEvent":
				this.defaultEvent = ModificateurEvent.fromNodeToArray(dansDefault.item(i));
				break;
			case "modifObjet":
				this.defaultObjet = ModificateurObjet.fromNodeToArray(dansDefault.item(i));
				break;
			case "modifPlayer":
				this.defaultPlayer = new ModificateurPlayer(dansDefault.item(i));
				break;
			}
		}
	}

	public static ArrayList<ModificateurEvent> fromNodeToArray(Node item) {
		NodeList events = item.getChildNodes();
		ArrayList<ModificateurEvent> liste = new ArrayList<ModificateurEvent>();

		for (int i = 0; i < events.getLength(); i++) {
			liste.add(new ModificateurEvent(events.item(i)));
		}

		return liste;
	}

}

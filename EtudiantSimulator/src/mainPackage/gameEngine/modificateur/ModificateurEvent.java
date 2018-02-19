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
	
	public ModificateurEvent(Node item) {
		NamedNodeMap attributs = item.getAttributes();
		
		this.nom = attributs.getNamedItem("nom").getNodeValue();
		
		if (attributs.getNamedItem("date") != null) {
			this.date = attributs.getNamedItem("date").getNodeValue();
		}
		
		this.occurence = Integer.parseInt(attributs.getNamedItem("occurence").getNodeValue());
		this.proba = Integer.parseInt(attributs.getNamedItem("proba").getNodeValue());
	}

	public void appliquer() {
		Event temp = ListEvent.trouverEvent(this.nom);
		
		temp.setOccurence(temp.getOccurence() + this.occurence);
		temp.setProbabilite(temp.getProbabilite() + this.proba);
		
		if (this.date != null) {
			if (this.proba == 0) {
				temp.setDate(new Date(this.date));
			} else {
				int[] tab = {new Date().differenceDate(new Date(this.date)), this.proba};
				temp.setJoursRestantsProbaAjoutee(tab);
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

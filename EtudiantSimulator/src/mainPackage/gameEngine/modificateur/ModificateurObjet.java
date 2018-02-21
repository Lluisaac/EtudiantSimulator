package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class ModificateurObjet {

	private String nom;
	private float[] attributs;
	private int dispo;
	
	private ArrayList<ModificateurEvent> modifEvent;
	private ArrayList<ModificateurObjet> modifObjet;

	public ModificateurObjet(Node item) {
		this.nom = item.getAttributes().getNamedItem("nom").getNodeValue();
		if (item.getAttributes().getNamedItem("dispo").getNodeValue().equals("default")) {
			this.dispo = 2;
		} else {
			this.dispo = Boolean.parseBoolean(item.getAttributes().getNamedItem("dispo").getNodeValue()) ? 1 : 0;
		}
		
		Node objet = item.getFirstChild();
		NamedNodeMap attributs = objet.getAttributes();
		if (objet.getNodeName().equals("upgrade")) {
			this.attributs = new float[8];
			
			for (int i = 0; i < attributs.getLength(); i++) {
				switch (attributs.item(i).getNodeName()) {
				case "cout":
					this.attributs[0] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "argentJ":
					this.attributs[1] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "savoirJ":
					this.attributs[2] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "faimJ":
					this.attributs[3] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "fatigueJ":
					this.attributs[4] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "bonheurJ":
					this.attributs[5] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "tempsLibreJ":
					this.attributs[6] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "durabilite":
					this.attributs[7] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;

				}
			}
		} else {
			this.attributs = new float[7];
			
			for (int i = 0; i < attributs.getLength(); i++) {
				switch (attributs.item(i).getNodeName()) {
				case "cout":
					this.attributs[0] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "savoir":
					this.attributs[1] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "faim":
					this.attributs[2] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "fatigue":
					this.attributs[3] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "bonheur":
					this.attributs[4] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "tempsLibre":
					this.attributs[5] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				case "durabilite":
					this.attributs[6] = Float.parseFloat(attributs.item(i).getNodeValue());
					break;
				}
			}
		}
		
		NodeList modifs = item.getChildNodes();
		
		this.modifEvent = new ArrayList<ModificateurEvent>();
		this.modifObjet = new ArrayList<ModificateurObjet>();
		
		for (int i = 1; i < modifs.getLength(); i++) {
			switch (modifs.item(i).getNodeName()) {
			case "modifEvent":
				this.modifEvent = ModificateurEvent.fromNodeToArray(modifs.item(i));
				break;
			case "modifObjet":
				this.modifObjet = ModificateurObjet.fromNodeToArray(modifs.item(i));
				break;
			}
		}
	}

	public void appliquer() {
		ListeObjets.trouveObjet(this.nom).setAttributs(attributs);
		if (this.dispo != 2) {
			ListeObjets.trouveObjet(this.nom).setDebloque(this.dispo == 1 ? true : false);
		}
		
		this.appliquerModif();
	}
	
	public void appliquerModif() {
		for (int i = 0; i < this.modifEvent.size(); i++) {
			this.modifEvent.get(i).appliquer();
		}

		for (int i = 0; i < this.modifObjet.size(); i++) {
			this.modifObjet.get(i).appliquer();
		}
	}

	public static ArrayList<ModificateurObjet> fromNodeToArray(Node item) {
		NodeList objets = item.getChildNodes();
		ArrayList<ModificateurObjet> modif = new ArrayList<ModificateurObjet>();

		for (int i = 0; i < objets.getLength(); i++) {
			modif.add(new ModificateurObjet(objets.item(i)));
		}

		return modif;
	}
}
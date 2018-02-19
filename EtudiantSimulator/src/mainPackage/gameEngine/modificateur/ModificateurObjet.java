package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class ModificateurObjet { // L'ordre est à respecter

	private String nom;
	private float[] attributs;
	private boolean dispo;

	public ModificateurObjet(Node item) {
		this.nom = item.getAttributes().getNamedItem("nom").getNodeValue();
		this.dispo = Boolean.parseBoolean(item.getAttributes().getNamedItem("dispo").getNodeValue());

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
	}


	public void appliquer() {
		ListeObjets.trouveObjet(this.nom).setAttributs(attributs);
		ListeObjets.trouveObjet(this.nom).setDebloque(this.dispo);
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
package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModificateurGeneral {

	private ArrayList<ModificateurEvent> accesEvent;
	private ArrayList<ModificateurObjet> accesObjet;
	private ModificateurPlayer accesPlayer;

	private String nom;
	
	private boolean noDefault;

	public ModificateurGeneral(Node item) {
		NamedNodeMap attributs = item.getAttributes();
		
		this.nom = attributs.getNamedItem("nom").getNodeValue();
		this.setNoDefault(Boolean.parseBoolean(attributs.getNamedItem("noDefault").getNodeValue()));
		
		NodeList modificateurs = item.getChildNodes();
		
		for (int i = 0; i < modificateurs.getLength(); i++) {
			switch (modificateurs.item(i).getNodeName()) {
			case "modifEvent":
				this.accesEvent = ModificateurEvent.fromNodeToArray(modificateurs.item(i));
				break;
			case "modifObjet":
				this.accesObjet = ModificateurObjet.fromNodeToArray(modificateurs.item(i));
				break;
			case "modifPlayer":
				this.accesPlayer = new ModificateurPlayer(modificateurs.item(i));
				break;
			}
		}
	}

	public boolean isNoDefault() {
		return noDefault;
	}

	public void setNoDefault(boolean noDefault) {
		this.noDefault = noDefault;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void appliquer() {
		if (this.accesEvent != null) {
			for (int i = 0; i < this.accesEvent.size(); i++) {
				this.accesEvent.get(i).appliquer();
			}
		}

		if (this.accesObjet != null) {
			for (int i = 0; i < this.accesObjet.size(); i++) {

				this.accesObjet.get(i).appliquer();
			}
		}

		if (this.accesPlayer != null) {
			this.accesPlayer.appliquer();
		}
		
		
	}

	public static ArrayList<ModificateurGeneral> fromNodesToArray(int indexDepart, NodeList dansEvent) {
		ArrayList<ModificateurGeneral> liste = new ArrayList<ModificateurGeneral>();
		
		for (int i = indexDepart; i < dansEvent.getLength(); i++) {
			liste.add(new ModificateurGeneral(dansEvent.item(i)));
		}
		
		return liste;
	}
}

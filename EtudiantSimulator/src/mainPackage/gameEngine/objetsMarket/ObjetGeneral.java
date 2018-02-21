
package mainPackage.gameEngine.objetsMarket;

import java.util.ArrayList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.modificateur.ModificateurEvent;
import mainPackage.gameEngine.modificateur.ModificateurObjet;

public abstract class ObjetGeneral {

	private String nom;
	protected float[] attributs;
	private ArrayList<ModificateurEvent> modifEvent;
	private ArrayList<ModificateurObjet> modifObjet;

	private String[] titres;
	private Date endOfPurchaseDate;

	private boolean debloque;

	public ObjetGeneral(Node item) {
		NamedNodeMap attributs = item.getAttributes();
		
		for (int i = 0; i < attributs.getLength(); i++) {
			switch (attributs.item(i).getNodeName()) {
			case "nom":
				this.nom = attributs.item(i).getNodeValue();
				break;
			case "dispo":
				this.debloque = Boolean.parseBoolean(attributs.item(i).getNodeValue());
				break;
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
		
		this.endOfPurchaseDate = new Date(0, 0, 0);
	}

	public boolean equals(ObjetGeneral objet) {
		return this.nom.equals(objet.getNom());
	}

	public String getNom() {

		return this.nom;
	}

	public float[] getAttributs() {

		return this.attributs;
	}

	public void setTitres(String[] titres) {

		this.titres = titres;
	}

	abstract public void acheter();
	
	abstract public void appliquer();

	public ArrayList<String> toTabString() {		
		ArrayList<String> string = new ArrayList<String>();
		for (int i = 0; i < this.attributs.length - 1; i++) {
			if (this.attributs[i] != 0) {
				if (i == 0) {
					string.add(titres[i] + ": " + attributs[i] + " euros");
				} else {
					string.add(titres[i] + ": " + attributs[i]);
				}
			}
		}
		return string;
	}

	public Date getEndOfPurchaseDate() {
		return endOfPurchaseDate;
	}

	public void setEndOfPurchaseDate(Date date) {
		this.endOfPurchaseDate = date;
		this.setDebloque(Engine.journee.getDate().superieurDate(date) || Engine.journee.getDate().equals(date));
	}

	public boolean isDebloque() {
		return this.debloque;
	}

	public void setAttributs(float attributs[]) {
		this.attributs = attributs;
	}

	abstract public void refreshDebloque();

	public void setDebloque(boolean b) {
		this.debloque = b;
	}

	public String toString() {
		String r = this.getNom() + " ";

		for (int i = 0; i < this.attributs.length; i++) {
			r += this.attributs[i] + " ";
		}

		return r;
	}

	public void appliquerModif() {
		for (int i = 0; i < this.modifEvent.size(); i++) {
			this.modifEvent.get(i).appliquer();
		}

		for (int i = 0; i < this.modifObjet.size(); i++) {
			this.modifObjet.get(i).appliquer();
		}
	}
}

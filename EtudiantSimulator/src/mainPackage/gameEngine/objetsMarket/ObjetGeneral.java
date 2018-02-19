
package mainPackage.gameEngine.objetsMarket;


import java.util.ArrayList;

import org.w3c.dom.Node;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.modificateur.ModificateurEvent;
import mainPackage.gameEngine.modificateur.ModificateurObjet;
import mainPackage.gameEngine.player.Player;

public abstract class ObjetGeneral {

	private String nom;
	protected float[] attributs;
	private ArrayList<ModificateurEvent> modifEvent;
	private ArrayList<ModificateurObjet> modifObjet;

	private String[] titres;
	private Date endOfPurchaseDate;
	
	private boolean debloque;

	public ObjetGeneral(String nom, float[] attribut, boolean debloque, ArrayList<ModificateurEvent> modifEvent, ArrayList<ModificateurObjet> modifObjet) {
		this.nom = nom;
		this.attributs = attribut;
		this.endOfPurchaseDate = new Date();
		this.debloque=debloque;
		this.modifEvent = modifEvent;
		this.modifObjet = modifObjet;
	}
	
	public ObjetGeneral(Node item) {
		
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

	abstract public void affectation(Player player);

	public ArrayList<String> toTabString() {
		ArrayList<String> string = new ArrayList<String>();
		for (int i = 0; i < this.attributs.length-1; i++) {
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
	
	public void setAttributs(float attributs[])
	{
		this.attributs=attributs;
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

	public void executerModificateur() {
		for (int i = 0; i < this.modifEvent.size(); i++) {
			this.modifEvent.get(i).appliquer();
		}
		
		for (int i = 0; i < this.modifObjet.size(); i++) {
			this.modifObjet.get(i).appliquer();
		}
	}
}

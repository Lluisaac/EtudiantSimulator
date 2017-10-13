
package mainPackage.gameEngine.objetsMarket;


import java.util.ArrayList;

import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.player.Player;

public abstract class ObjetGeneral {

	private String nom;
	protected float[] attributs;

	private String[] titres;
	private Date purchaseDate;
	
	private boolean debloque;

	public ObjetGeneral(String nom, float[] attribut, boolean debloque) {
		this.nom = nom;
		this.attributs = attribut;
		this.purchaseDate = new Date();
		this.debloque=debloque;
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
					string.add(titres[i] + ": " + attributs[i] + "€");
				} else {
					string.add(titres[i] + ": " + attributs[i]);
				}
			}
		}
		return string;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date date) {
		this.purchaseDate = date;
	}

	public boolean isDebloque() {
		return this.debloque;
	}
	
	public String toString() {
		String r = this.getNom() + " ";
		
		for (int i = 0; i < this.attributs.length; i++) {
			r += this.attributs[i] + " ";
		}
		
		return r;
	}
}


package mainPackage.gameEngine.player;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.filiere.Filiere;

public class Player {

	private Filiere filiere;

	private float argent;
	private float savoir;
	private float faim;
	private float fatigue;
	private float bonheur;

	private int argentJ = 0;
	private int savoirJ = 0;
	private int faimJ = -4;
	private int fatigueJ = 15;
	private int bonheurJ = 0;

	private final int ARGENT_DEPART;

	// private int jourAct;
	// private int moisAct;

	private int gainParMois;
	private int loyer;

	public Player(Filiere filiaire, boolean isPlayerOP) {
		this.filiere = filiaire;

		this.argent = 0;
		this.savoir = 0;
		this.faim = 100;
		this.fatigue = 0;
		this.bonheur = 100;
		
		if (isPlayerOP) {
			this.setArgentJ(150);
			this.setBonheurJ(10);
		}

		// this.jourAct = 0;
		// this.moisAct = 1;

		// Generation de gainParMois
		int[] tabCROUS = {376, 400, 463};

		Random rand = new Random();

		int indiceCROUS = rand.nextInt(2);

		gainParMois += tabCROUS[indiceCROUS];

		int nbParents = rand.nextInt(2);

		for (int i = 0; i < nbParents; i++) {
			gainParMois += 50 + rand.nextInt(150);
		}

		// Generation de Loyer
		loyer += 300 + rand.nextInt(150);

		// Generation de stock d'argent
		this.ARGENT_DEPART = 100 + rand.nextInt(1400);
		this.argent = ARGENT_DEPART;
	}

	public Player(Filiere filiaire, float argent, float conn, float faim, float fat, float bon, int argentJ, int connJ,
			int faimJ, int fatJ, int bonJ, int argentDepart, int gain, int loyer) {
		this.filiere = filiaire;

		// this.jourAct = jour;
		// this.moisAct = mois;

		this.argent = argent;
		this.savoir = conn;
		this.faim = faim;
		this.fatigue = fat;
		this.bonheur = bon;

		this.argentJ = argentJ;
		this.savoirJ = connJ;
		this.faimJ = faimJ;
		this.fatigueJ = fatJ;
		this.bonheurJ = bonJ;

		this.ARGENT_DEPART = argentDepart;

		this.gainParMois = gain;
		this.loyer = loyer;
	}

	public void paimentEtGainsMois() {

		this.argent += this.gainParMois - this.loyer;
	}

	public int getArgentDepart() {

		return this.ARGENT_DEPART;
	}

	public float getArgent() {

		return this.argent;
	}

	public void setArgent(float argent) {

		this.argent = argent;
	}

	public float getSavoir() {

		return this.savoir;
	}

	public void setSavoir(float savoir) {

		this.savoir = savoir;
	}

	public float getFaim() {

		return this.faim;
	}

	public void setFaim(float faim) {

		this.faim = faim;
	}

	public float getFatigue() {

		return this.fatigue;
	}

	public void setFatigue(float fatigue) {

		this.fatigue = fatigue;
	}

	public float getBonheur() {

		return this.bonheur;
	}

	public void setBonheur(float bonheur) {

		this.bonheur = bonheur;
	}

	public int getArgentJ() {

		return this.argentJ;
	}

	public void setArgentJ(int argentJ) {

		this.argentJ = argentJ;
	}

	public int getSavoirJ() {

		return this.savoirJ;
	}

	public void setSavoirJ(int savoirJ) {

		this.savoirJ = savoirJ;
	}

	public int getFaimJ() {

		return this.faimJ;
	}

	public void setFaimJ(int faimJ) {

		this.faimJ = faimJ;
	}

	public int getFatigueJ() {

		return this.fatigueJ;
	}

	public void setFatigueJ(int fatigueJ) {

		this.fatigueJ = fatigueJ;
	}

	public int getBonheurJ() {

		return this.bonheurJ;
	}

	public void setBonheurJ(int bonheurJ) {

		this.bonheurJ = bonheurJ;
	}

	public int getGainParMois() {

		return this.gainParMois;
	}

	public int getLoyer() {

		return this.loyer;
	}

	public Filiere getFiliaire() {

		return this.filiere;
	}

	public float modifierArgent(float modif) {

		float temp = this.argentJ - (180 * modif) / 1400;
		this.argent += temp;
		return temp;
	}

	public float modifierFatigue(float modif) {

		float temp = this.fatigueJ;
		Engine.journee.setTempsLibreJ(this, modif);

		if (modif < 50) {
			temp += (8 * ((modif * 2) / 100)) * -1.875;
		}
		else if (modif == 50) {
			temp += -15;
		}
		else {
			temp += -15 - 1.875 * ((Engine.journee.getTempsLibreJ() / 60) * (2 * ((modif - 50) / 100)));
		}
		this.fatigue += temp;
		return temp;
	}

	public float modifierSavoir(float modif) {
		float temp = this.savoirJ + ((Engine.journee.getTempsLibreJ() * (modif / 100)) / 30) + (Engine.journee.getTempsTravail(this) * 3);
		
		if (this.fatigue > 50 && this.fatigue <= 80) {
			temp *= 0.8f;
		}
		else if (this.fatigue > 80) {
			temp = temp / 2;
		}

		this.savoir += temp;
		return temp;
	}

	public float modifierFaim(float modif) {

		float temp = this.faimJ + (8 * modif) / 100;
		this.faim += temp;
		return temp;
	}

	public float modifierBonheur(float modif) {

		float temp = this.bonheurJ + (Engine.journee.getTempsLibreJ() * (1 - modif / 100)) / 120 - 2;

		if (this.argent < 0) {
			temp -= 2;
		}
		else if (this.argent < this.ARGENT_DEPART) {
			temp -= 1;
		}
		this.bonheur += temp;
		return temp;
	}

	@Override
	public String toString() {

		String string = "";
		string += this.filiere.getDuree() + ";" + this.filiere.getNom() + "\n" + this.argent + "\n" + this.savoir
				+ "\n" + this.faim + "\n" + this.fatigue + "\n" + this.bonheur + "\n" + this.argentJ + "\n"
				+ this.savoirJ + "\n" + this.faimJ + "\n" + this.fatigueJ + "\n" + this.bonheurJ + "\n"
				+ this.ARGENT_DEPART + "\n" + this.gainParMois + "\n" + this.loyer;

		return string;
	}

	public boolean checkSavoir() {
		boolean r = this.getFiliaire().getSavoirRequisParAn() <= this.getSavoir();
		System.out.println("Savoir requis: " + this.getFiliaire().getSavoirRequisParAn() + "\nSavoir actuel: " + this.getSavoir());
		return r;
	}
	
	public boolean checkSavoirTotal() {
		boolean r = this.getFiliaire().getSavoirRequisParAn() * this.getFiliaire().getDuree() <= this.getSavoir();
		System.out.println("Savoir requis total: " + this.getFiliaire().getSavoirRequisParAn() * this.getFiliaire().getDuree() + "\nSavoir actuel: " + this.getSavoir());
		return r;
	}

	public void prelassage() {
		this.setSavoir(this.getSavoir() - this.getSavoir()/10);
		this.setArgent(this.getArgent() + this.getArgentDepart()/5);
		this.setBonheur(100);
		this.setFaim(100);
		this.setFatigue(0);
	}

}

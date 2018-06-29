
package mainPackage.gameEngine.player;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.objetsMarket.ListeObjets;

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

		// Generation de gainParMois
		int[] tabCROUS = { 376, 400, 463 };

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

	public Player(String save) {
		String[] content = save.split(";");
		this.filiere = new Filiere(content[0]);
		
		String[] val = content[1].split(",");
		
		this.argent = Float.parseFloat(val[0]);
		this.savoir = Float.parseFloat(val[1]);
		this.faim = Float.parseFloat(val[2]);
		this.fatigue = Float.parseFloat(val[3]);
		this.bonheur = Float.parseFloat(val[4]);
		
		val = content[2].split(",");
		
		this.argentJ = Integer.parseInt(val[0]);
		this.savoirJ = Integer.parseInt(val[1]);
		this.faimJ = Integer.parseInt(val[2]);
		this.fatigueJ = Integer.parseInt(val[3]);
		this.bonheurJ = Integer.parseInt(val[4]);
		
		val = content[3].split(",");
		
		this.ARGENT_DEPART = Integer.parseInt(val[0]);
		this.gainParMois = Integer.parseInt(val[1]);
		this.loyer = Integer.parseInt(val[2]);
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
	
	public void setLoyer(int loyer) {
		this.loyer = loyer;
	}

	public void setGainParMois(int gainParMois) {
		this.gainParMois = gainParMois;
	}

	public float modifierArgent(float modif) {

		float temp = this.argentJ - (180 * modif) / 1400;
		this.argent += temp;
		
		temp += ListeObjets.getUpgradeArgent();
		
		return temp;
	}

	public float modifierFatigue(float modif) {

		float temp = this.fatigueJ;
		Engine.journee.setTempsLibre(this, modif);

		if (modif < 50) {
			temp += (8 * ((modif * 2) / 100)) * -1.875;
		} else if (modif == 50) {
			temp += -15;
		} else {
			temp += -15 - 1.875 * ((Engine.journee.getTempsLibre() / 60) * (2 * ((modif - 50) / 100)));
		}
		this.fatigue += temp;
		
		temp += ListeObjets.getUpgradeFatigue();
		
		return temp;
	}

	public float modifierSavoir(float modif) {
		float temp = this.savoirJ + ((Engine.journee.getTempsLibre() * (modif / 100)) / 30)
				+ (Engine.journee.getTempsTravail(this) * 3);

		if (this.fatigue > 50 && this.fatigue <= 80) {
			temp *= 0.8f;
		} else if (this.fatigue > 80) {
			temp = temp / 2;
		}

		this.savoir += temp;
		
		temp += ListeObjets.getUpgradeSavoir();
		
		return temp;
	}

	public float modifierFaim(float modif) {

		float temp = this.faimJ + (8 * modif) / 100;
		this.faim += temp;
		
		temp += ListeObjets.getUpgradeFaim();
		
		return temp;
	}

	public float modifierBonheur(float modif) {

		float temp = this.bonheurJ + (Engine.journee.getTempsLibre() * (1 - modif / 100)) / 120 - 2;

		if (this.argent < 0) {
			temp -= 2;
		} else if (this.argent < this.ARGENT_DEPART) {
			temp -= 1;
		}
		this.bonheur += temp;
		
		temp += ListeObjets.getUpgradeBonheur();
		
		return temp;
	}

	@Override
	public String toString() {
		return this.filiere.toString() + ";" + this.argent + "," + this.savoir + "," + this.faim + "," + this.fatigue + "," + this.bonheur + ";"
				+ this.argentJ + "," + this.savoirJ + "," + this.faimJ + "," + this.fatigueJ + "," + this.bonheurJ + ";" + this.ARGENT_DEPART + "," 
				+ this.gainParMois + "," + this.loyer + ";";
	}

	public boolean checkSavoir() {
		boolean r = this.getFiliaire().getSavoirRequisParAn() <= this.getSavoir();
		System.out.println(
				"Savoir requis: " + this.getFiliaire().getSavoirRequisParAn() + "\nSavoir actuel: " + this.getSavoir());
		return r;
	}

	public boolean checkSavoirTotal() {
		boolean r = this.getFiliaire().getSavoirRequisParAn() * this.getFiliaire().getDuree() <= this.getSavoir();
		System.out.println(
				"Savoir requis total: " + this.getFiliaire().getSavoirRequisParAn() * this.getFiliaire().getDuree()
						+ "\nSavoir actuel: " + this.getSavoir());
		return r;
	}

	public void prelassage() {
		this.setSavoir(this.getSavoir() - this.getSavoir() / 10);
		this.setArgent(this.getArgent() + this.getArgentDepart() / 5);
		this.setBonheur(100);
		this.setFaim(100);
		this.setFatigue(0);
	}

}

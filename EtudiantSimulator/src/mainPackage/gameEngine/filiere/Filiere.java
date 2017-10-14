
package mainPackage.gameEngine.filiere;

import java.util.Random;

public class Filiere {

	private String nom;
	private final int duree;
	private int[] heures;
	private int savoirRequis;
	private boolean debloque;

	public Filiere(String nom, int duree, int[] heures, boolean debloque) {
		this.duree = duree;
		this.nom = nom;
		setHeures(heures);
		setSavoirRequis();
		this.debloque = debloque;
	}
	
	public Filiere(int i) {
		this.duree = ListeFilieres.getFiliere(i).duree;
		this.nom = ListeFilieres.getFiliere(i).nom;
		setHeures(ListeFilieres.getFiliere(i).heures);
		setSavoirRequis(ListeFilieres.getFiliere(i).savoirRequis);
		this.debloque = ListeFilieres.getFiliere(i).debloque;
	}

	public Filiere(String save) {
		String[] content = save.split(",");
		this.nom = content[0];
		this.duree = Integer.parseInt(content[1]);

		
		String[] val = content[2].split("_");
		int[] heures = new int[val.length];
		for (int i = 0; i < val.length; i++) {
			heures[i] = Integer.parseInt(val[i]);
		}
		this.heures = heures;
		
		this.savoirRequis = Integer.parseInt(content[3]);
		this.debloque = Boolean.parseBoolean(content[4]);
	}
	
	public String toString() {
		String r = this.nom + "," + this.duree + ",";
		
		for (int i = 0; i < this.heures.length; i++) {
			if (i < this.heures.length - 1) {
				r += this.heures[i] + "_";				
			}
			else {
				r += this.heures[i];				
			}
		}
		r+= "," + savoirRequis + "," + debloque;
		
		return r;
	}

	public int getDuree() {

		return duree;
	}

	public String getNom() {

		return nom;
	}

	public int getTempsTravail(int i) {
		return this.heures[i];
	}

	public int[] getHeures() {

		return heures;
	}

	public void setHeures(int[] heures) {

		int max;
		int[] corr;
		int r;

		if (this.getNom() == "Prepa") {
			corr = new int[] {-1, -1, -1, -1, -1, -1, 0};
			max = 6;
		}
		else {
			corr = new int[] {-1, -1, -1, -1, -1, 0, 0};
			max = 5;
		}

		Random rand = new Random();

		for (int i = max; i > 0; i--) {

			do {
				r = rand.nextInt(6);
			} while (corr[r] >= 0);

			corr[r] = heures[i - 1];
		}
		
		this.heures = corr;
	}

	public int getSavoirRequis() {

		return savoirRequis;
	}
	
	public int getSavoirRequisParAn() {

		return savoirRequis / this.getDuree();
	}

	public void setSavoirRequis() {

		int r = 0;

		for (int i = 0; i < 7; i++) {
			r += this.getHeures()[i];
		}
		Random rand = new Random();

		this.savoirRequis = (int) (r * (1 + rand.nextFloat())) * 36 * 3 * this.getDuree();
	}
	
	public void setSavoirRequis(int i) {
		this.savoirRequis = i;
	}

	public boolean isDebloque() {

		return this.debloque;
	}
}

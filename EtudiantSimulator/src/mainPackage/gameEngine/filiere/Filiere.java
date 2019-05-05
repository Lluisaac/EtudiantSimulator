
package mainPackage.gameEngine.filiere;

import java.util.Random;

public class Filiere {

	private static final String SEPARATEUR_HEURES = "_";
	private static final String SEPARATEUR_ATTRIBUT = ",";
	private static final int INDEX_DEBLOQUE = 4;
	private static final int INDEX_SAVOIR = 3;
	private static final int INDEX_HEURES = 2;
	private static final int INDEX_DUREE = 1;
	private static final int INDEX_NOM = 0;
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

	public Filiere(String sauvegarde) {
		String[] informationsDeFiliere = sauvegarde.split(SEPARATEUR_ATTRIBUT);
		this.nom = informationsDeFiliere[INDEX_NOM];
		this.duree = Integer.parseInt(informationsDeFiliere[INDEX_DUREE]);

		
		String[] heuresNonFormattees = informationsDeFiliere[INDEX_HEURES].split(SEPARATEUR_HEURES);
		int[] heures = formatter(heuresNonFormattees);
		this.heures = heures;
		
		this.savoirRequis = Integer.parseInt(informationsDeFiliere[INDEX_SAVOIR]);
		this.debloque = Boolean.parseBoolean(informationsDeFiliere[INDEX_DEBLOQUE]);
	}

	private int[] formatter(String[] val) {
		int[] heures = new int[val.length];
		for (int i = 0; i < val.length; i++) {
			heures[i] = Integer.parseInt(val[i]);
		}
		return heures;
	}
	
	public String toString() {
		String r = this.nom + SEPARATEUR_ATTRIBUT + this.duree + SEPARATEUR_ATTRIBUT;
		
		for (int i = 0; i < this.heures.length; i++) {
			if (i < this.heures.length - 1) {
				r += this.heures[i] + SEPARATEUR_HEURES;				
			}
			else {
				r += this.heures[i];				
			}
		}
		r+= SEPARATEUR_ATTRIBUT + savoirRequis + SEPARATEUR_ATTRIBUT + debloque;
		
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

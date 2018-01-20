package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class ModificateurObjet { // L'ordre est à respecter

	private String nom;
	private float[] attributs;
	private boolean dispo;

	public ModificateurObjet(String modif) {
		String[] contentTab = modif.split("\\|");
		String[] contentTab2 = contentTab[1].split("#");
		
		this.attributs = new float[contentTab2.length];

		this.nom = contentTab[0];
		for (int i = 0; i < contentTab2.length; i++) {
			this.attributs[i] = Float.parseFloat(contentTab2[i]);
		}
		this.dispo = Boolean.parseBoolean(contentTab[2]);

	}

	public void appliquer() {
		ListeObjets.trouveObjet(this.nom).setAttributs(attributs);
		ListeObjets.trouveObjet(this.nom).setDebloque(this.dispo);
	}

	public static ArrayList<ModificateurObjet> createArrayFromString(String liste) {
		// Va créer chaque modifObjet pour en faire une array list, chaque
		// modifObjet doit etre séparé par _

		if (liste.equals("")) {
			return null;
		}

		ArrayList<ModificateurObjet> modifListe = new ArrayList<ModificateurObjet>();

		String[] contentTab = liste.split("_");

		for (int i = 0; i < contentTab.length; i++) {
			modifListe.add(new ModificateurObjet(contentTab[i]));
		}

		return modifListe;
	}
}
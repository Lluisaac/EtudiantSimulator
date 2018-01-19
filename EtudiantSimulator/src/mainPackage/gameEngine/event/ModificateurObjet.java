package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class ModificateurObjet { // L'ordre est à respecter

	private String nom;
	private float[] attributs;

	ModificateurObjet(String modif) {
		String[] contentTab = modif.split("\\|");
		String[] contentTab2 = contentTab[1].split("#");
		
		this.attributs = new float[contentTab2.length];

		this.nom = contentTab[0];
		for (int i = 0; i < contentTab2.length; i++) {
			this.attributs[i] = Float.parseFloat(contentTab2[i]);
		}

	}

	public void appliquer() {
		for (int i = 0; i < this.attributs.length; i++) {
			System.out.println(this.attributs[i] + " ");
		}
		ListeObjets.trouveObjet(this.nom).setAttributs(attributs);
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
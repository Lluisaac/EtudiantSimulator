package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

public class ModificateurGeneral {

	private ArrayList<ModificateurEvent> accesEvent;
	private ArrayList<ModificateurObjet> accesObjet;
	private ModificateurPlayer accesPlayer;

	private String nom;

	public ModificateurGeneral(String nom, ArrayList<ModificateurEvent> events, ArrayList<ModificateurObjet> objets,
			ModificateurPlayer players) {
		this.nom = nom;
		this.accesEvent = events;
		this.accesObjet = objets;
		this.accesPlayer = players;
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

	public static ArrayList<ModificateurGeneral> createArrayFromString(String liste) {
		// Va créer chaque modifGeneral pour en faire une array list, chaque
		// modifGeneral doit etre séparé par _

		if (liste.equals("")) {
			return null;
		}

		ArrayList<ModificateurGeneral> modifListe = new ArrayList<ModificateurGeneral>();

		String[] contentTab = liste.split("@");

		for (int i = 0; i < contentTab.length; i++) {
			String[] modificateurs = contentTab[i].split("_");
			modifListe.add(
					new ModificateurGeneral(modificateurs[0], ModificateurEvent.createArrayFromString(modificateurs[1]),
							ModificateurObjet.createArrayFromString(modificateurs[2]),
							new ModificateurPlayer(modificateurs[3])));
		}

		return modifListe;
	}
}

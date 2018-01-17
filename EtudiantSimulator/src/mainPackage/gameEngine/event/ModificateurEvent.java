package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.jour.Date;

public class ModificateurEvent { 
	
	private String nom;

	private Date date;
	
	private int occurence;
	
	private int proba;
	
	public ModificateurEvent(String modif) {
		
		//L'acces sera sous cette syntaxe:
		//nom,date,occurence,proba
		//Le nom pour trouver l'event
		//La date pour créer un event daté
		//l'occurence et la proba pour l'augmenter si l'on veut (mettre une incrémentation), doit forcément au moins valoir quelque chose
		//Si c'est les deux, ce sera parce que c'est une incrémentation de proba pendant une durée limitée
		
		//Le string doit être séparé par |
		
		String[] contentTab = modif.split("|");
		
		this.nom = contentTab[0];
		
		if (!contentTab[1].equals("")) {
			this.date = new Date(contentTab[1]);
		} else {
			this.date = null;
		}
		
		this.occurence = Integer.parseInt(contentTab[2]);
		
		this.proba = Integer.parseInt(contentTab[3]);
	}
	
	public void appliquer() {
		Event temp = ListEvent.trouverEvent(this.nom);
		
		temp.setOccurence(temp.getOccurence() + this.occurence);
		temp.setProbabilite(temp.getProbabilite() + this.proba);
		
		if (!(this.date == null)) {
			if (this.proba == 0) {
				temp.setDate(this.date);
			} else {
				int[] tab = {new Date("1/1/1").differenceDate(this.date), this.proba};
				temp.setJoursRestantsProbaAjoutee(tab);
			}
		}
	}
	
	public static ArrayList<ModificateurEvent> createArrayFromString(String liste) {
		//Va créer chaque modifEvent pour en faire une array list, chaque modifEvent doit etre séparé par _
		
		if (liste.equals("")) {
			return null;
		}
		
		ArrayList<ModificateurEvent> modifListe = new ArrayList<ModificateurEvent>();
		
		String[] contentTab = liste.split("_");
		
		for (int i = 0; i < contentTab.length - 1; i++) {
			modifListe.add(new ModificateurEvent(contentTab[i]));
		}
		
		return modifListe;
	}
	

}

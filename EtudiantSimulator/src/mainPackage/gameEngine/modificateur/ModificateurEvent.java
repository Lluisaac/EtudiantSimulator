package mainPackage.gameEngine.modificateur;

import java.util.ArrayList;

import mainPackage.gameEngine.event.Event;
import mainPackage.gameEngine.event.ListEvent;
import mainPackage.gameEngine.jour.Date;

public class ModificateurEvent { 
	
	private String nom;

	private String date;
	
	private int occurence;
	
	private int proba;
	
	public ModificateurEvent(String modif) {
		
		//L'acces sera sous cette syntaxe:
		//nom,date,occurence,proba
		//Le nom pour trouver l'event
		//La date pour cr�er un event dat�
		//l'occurence et la proba pour l'augmenter si l'on veut (mettre une incr�mentation), doit forc�ment au moins valoir quelque chose
		//Si c'est les deux, ce sera parce que c'est une incr�mentation de proba pendant une dur�e limit�e
		
		//Le string doit �tre s�par� par |
		
		String[] contentTab = modif.split("\\|", -1);
		this.nom = contentTab[0];
		
		if (!contentTab[1].equals("")) {
			this.date = contentTab[1];
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
		
		if (this.date != null) {
			if (this.proba == 0) {
				temp.setDate(new Date(this.date));
			} else {
				int[] tab = {new Date().differenceDate(new Date(this.date)), this.proba};
				temp.setJoursRestantsProbaAjoutee(tab);
			}
		}
	}
	
	public static ArrayList<ModificateurEvent> createArrayFromString(String liste) {
		//Va cr�er chaque modifEvent pour en faire une array list, chaque modifEvent doit etre s�par� par _
		
		if (liste.trim().equals("")) {
			return null;
		}
		ArrayList<ModificateurEvent> modifListe = new ArrayList<ModificateurEvent>();
		
		String[] contentTab = liste.split("_");
		
		for (int i = 0; i < contentTab.length; i++) {
			
			modifListe.add(new ModificateurEvent(contentTab[i]));
		}
		
		return modifListe;
	}
	

}
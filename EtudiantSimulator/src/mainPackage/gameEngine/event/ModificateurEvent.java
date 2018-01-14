package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.Engine;
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
		//La date pour cr�er un event dat�
		//l'occurence et la proba pour l'augmenter si l'on veut (mettre une incr�mentation), doit forc�ment au moins valoir quelque chose
		//Si c'est les deux, ce sera parce que c'est une incr�mentation de proba pendant une dur�e limit�e
		
		//Le string doit �tre s�par� par |
		
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
		
		if (this.date == null) {
			if (this.proba == 0) {
				temp.setDate(this.date);
			} else {
				int[] tab = {Engine.journee.getDate().differenceDate(this.date), this.proba};
				temp.setJoursRestantsProbaAjoutee(tab);
			}
		}
	}
	
	public static ArrayList<ModificateurEvent> creatArrayFromString(String liste) {
		//Va cr�er chaque modifEvent pour en faire une array list, chaque modifEvent doit etre s�par� par _
		
		ArrayList<ModificateurEvent> modifListe = new ArrayList<ModificateurEvent>();
		
		String[] contentTab = liste.split("_");
		
		for (int i = 0; i < contentTab.length - 1; i++) {
			modifListe.add(new ModificateurEvent(contentTab[i]));
		}
		
		return modifListe;
	}
	

}

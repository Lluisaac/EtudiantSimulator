package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.modificateur.ModificateurEvent;
import mainPackage.gameEngine.modificateur.ModificateurGeneral;
import mainPackage.gameEngine.modificateur.ModificateurObjet;
import mainPackage.gameEngine.modificateur.ModificateurPlayer;

public class Event {

	// Plusieurs type de Event, ce qui ont une proba de infinie sur une date, et
	// le reste.
	private String resume;// Une explication de l'event
	private String nom;
	private Date date;
	private int occurence;// Signifie non valide
	private int probabilite;
	private String archetype;
	private int[] joursRestantsProbaAjoutee = new int[2];// Utile que pour les
															// quetes
	private ArrayList<ModificateurEvent> defaultEvent;
	private ArrayList<ModificateurObjet> defaultObjet;
	private ModificateurPlayer defaultPlayer;

	private ArrayList<ModificateurGeneral> accesChoix;

	public Event(String nom, String resume, String archetype, Date date, int occurence)// Noel,Paque,anniversaire
	// du
	// joueur,JAPD,../
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.date = date;
		this.resume = resume;
		this.archetype = archetype;
		this.probabilite = -1; // Infini
	}

	public Event(String nom, String resume, String archetype, int occurence, int probabilite) // tout
																								// le
																								// reste
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.resume = resume;
		this.archetype = archetype;
		this.probabilite = probabilite;
		this.date = null;
	}

	public void executer(int i) {
		if(!this.accesChoix.get(i).getNom().contains("noDefault")) {
			this.executerDefault();
		}
		this.accesChoix.get(i).appliquer();
	}

	public void executerDefault() {
		if (this.defaultEvent != null) {
			for (int i = 0; i < this.defaultEvent.size(); i++) {
				this.defaultEvent.get(i).appliquer();
			}
		}

		if (this.defaultObjet != null) {
			for (int i = 0; i < this.defaultObjet.size(); i++) {

				this.defaultObjet.get(i).appliquer();
			}
		}

		if (this.defaultPlayer != null) {
			this.defaultPlayer.appliquer();
		}
	}

	public String toString() {
		String str = nom + ";" + resume + ";" + archetype + ";";

		if (this.date != null) {
			str += this.date.toString();
		}

		str += ";" + occurence + ";" + probabilite + ";";
		return str;
	}

	public String getResume() {
		return resume;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getArchetype() {
		return archetype;
	}

	public int getProbabilite() {
		return this.occurence < 0 ? this.occurence == -1 ? probabilite : 0 : this.probabilite * this.occurence;
	}

	public void setProbabilite(int probabilite) {
		this.probabilite = probabilite;
	}

	public ArrayList<ModificateurEvent> getAcces() {
		return defaultEvent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDate(String date) {
		this.date.setDate(date);
	}

	public int getOccurence() {
		return occurence;
	}

	public ArrayList<ModificateurEvent> getDefaultEvent() {
		return defaultEvent;
	}

	public void setDefaultEvent(ArrayList<ModificateurEvent> defaultEvent) {
		this.defaultEvent = defaultEvent;
	}

	public ArrayList<ModificateurObjet> getDefaultObjet() {
		return defaultObjet;
	}

	public void setDefaultObjet(ArrayList<ModificateurObjet> defaultObjet) {
		this.defaultObjet = defaultObjet;
	}

	public ModificateurPlayer getDefaultPlayer() {
		return defaultPlayer;
	}

	public void setDefaultPlayer(ModificateurPlayer defaultPlayer) {
		this.defaultPlayer = defaultPlayer;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}

	public int[] getJoursRestantsProbaAjoutee() {
		return joursRestantsProbaAjoutee;
	}

	public void setJoursRestantsProbaAjoutee(int[] joursRestantsProbaAjoutee) {
		this.joursRestantsProbaAjoutee = joursRestantsProbaAjoutee;
	}

	public ArrayList<ModificateurGeneral> getAccesChoix() {
		return accesChoix;
	}

	public void setAccesChoix(ArrayList<ModificateurGeneral> accesChoix) {
		this.accesChoix = accesChoix;
	}

	public int getSizeAccesChoix() {
		return this.accesChoix.size();
	}
}

package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.jour.Date;

public class Event {

	// Plusieurs type de Event, ce qui ont une proba de infinie sur une date, et
	// le reste.
	private String resume;// Une explication de l'event
	private String nom;
	private Date date;
	private int occurence;//Signifie non valide
	private int probabilite;
	private String archetype;
	private int[] joursRestantsProbaAjoutee = new int[2];//Utile que pour les quetes
	private ArrayList<ModificateurEvent> acces;

	public Event() {
		this.acces = new ArrayList<ModificateurEvent>();
		this.probabilite = 0;
		this.occurence = 0;
	}

	public Event(String nom, String resume, String archetype, Date date, int occurence,
			ArrayList<ModificateurEvent> acces)// Noel,Paque,anniverssaire du
												// joueur,JAPD,../
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.date = date;
		this.resume = resume;
		this.archetype = archetype;
		this.acces = acces;
		this.probabilite = -1; // Infini
	}

	public Event(String nom, String resume, String archetype, int occurence, int probabilite,
			ArrayList<ModificateurEvent> acces)// tous le reste
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.resume = resume;
		this.archetype = archetype;
		this.acces = acces;
		this.probabilite = probabilite;
		this.date = null;
	}
	
	
	public void executer() {
		for (int i = 0; i < this.acces.size(); i++) {
		this.acces.get(i).appliquer();
		}
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
		return probabilite;
	}

	public void setProbabilite(int probabilite) {
		this.probabilite = probabilite;
	}

	public ArrayList<ModificateurEvent> getAcces() {
		return acces;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date=date;
	}

	public void setDate(String date) {
		this.date.setDate(date);
	}

	public int getOccurence() {
		return occurence;
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
	
	public void setJoursRestantsProbaAjoutee(String texte) {
		int[] joursRestants=new int[2];
		String[] tabString=texte.split(",");
		joursRestants[0]=Integer.parseInt(tabString[0]);
		joursRestants[1]=Integer.parseInt(tabString[1]);
	}
}

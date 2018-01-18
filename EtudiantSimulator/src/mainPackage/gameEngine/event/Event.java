package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class Event {

	// Plusieurs type de Event, ce qui ont une proba de infinie sur une date, et
	// le reste.
	private String resume;// Une explication de l'event
	private String nom;
	private Date date;
	private int occurence;// Signifie non valide
	private int probabilite;
	private String archetype;
	private int[] joursRestantsProbaAjoutee = new int[2];// Utile que pour les quetes
	private ArrayList<ModificateurEvent> acces;
	private String[] modifieurAttribut;

	public Event() {
		this.acces = new ArrayList<ModificateurEvent>();
		this.probabilite = 0;
		this.occurence = 0;
	}

	public Event(String nom, String resume, String archetype, Date date, int occurence,
			ArrayList<ModificateurEvent> acces, String[] modifieurAttribut)// Noel,Paque,anniverssaire du
												// joueur,JAPD,../
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.date = date;
		this.resume = resume;
		this.archetype = archetype;
		this.acces = acces;
		this.probabilite = -1; // Infini
		this.modifieurAttribut=modifieurAttribut;
	}

	public Event(String nom, String resume, String archetype, int occurence, int probabilite,
			ArrayList<ModificateurEvent> acces, String[] modifieurAttribut)// tous le reste
	{
		this.nom = nom;
		this.setOccurence(occurence);
		this.resume = resume;
		this.archetype = archetype;
		this.acces = acces;
		this.probabilite = probabilite;
		this.date = null;
		this.modifieurAttribut=modifieurAttribut;
	}

	public void executer() {
		if (!(this.acces == null)) {
			for (int i = 0; i < this.acces.size(); i++) {
				this.acces.get(i).appliquer();
			}
		}
		this.appliquerAttributs();
	}

	
	
	public void appliquerAttributs() {
		String[] change=new String[2];
		for(int i=0;i<this.modifieurAttribut.length;i++)
		{
			change=modifieurAttribut[i].split("|");
			verifAttribut(change);
		}	
	}

	public void verifAttribut(String[] change) {
		switch (change[0])
		{
		case "loyerAjout":
			Engine.getPlayer().setLoyer(Engine.getPlayer().getLoyer() + Integer.parseInt(change[1]));
			break;
		case "loyer":
			Engine.getPlayer().setLoyer(Integer.parseInt(change[1]));
			break;
		case "bonheur":
			Engine.getPlayer().setBonheur(Engine.getPlayer().getBonheur() +  Integer.parseInt(change[1]));
			break;
		case "bonheurJ":
			Engine.getPlayer().setBonheurJ(Engine.getPlayer().getBonheurJ() +  Integer.parseInt(change[1]));
			break;
		case "fatigue":
			Engine.getPlayer().setFatigue(Engine.getPlayer().getFatigue() +  Integer.parseInt(change[1]));
			break;
		case "fatigueJ":
			Engine.getPlayer().setFatigueJ(Engine.getPlayer().getFatigueJ() +  Integer.parseInt(change[1]));
			break;
		case "faim":
			Engine.getPlayer().setFaim(Engine.getPlayer().getFaim() +  Integer.parseInt(change[1]));
			break;
		case "faimJ":
			Engine.getPlayer().setFaimJ(Engine.getPlayer().getFaimJ() +  Integer.parseInt(change[1]));
			break;
		case "savoir":
			Engine.getPlayer().setSavoir(Engine.getPlayer().getSavoir() +  Integer.parseInt(change[1]));
			break;
		case "savoirJ":
			Engine.getPlayer().setSavoirJ(Engine.getPlayer().getSavoirJ() +  Integer.parseInt(change[1]));
			break;
		case "argent":
			Engine.getPlayer().setArgent(Engine.getPlayer().getArgent() +  Integer.parseInt(change[1]));
			break;
		case "argentJ":
			Engine.getPlayer().setArgentJ(Engine.getPlayer().getArgentJ() +  Integer.parseInt(change[1]));
			break;
		case "orientation":
			//A faire un jouir
			break;
		case "jourFerie":
			break;
		case "vacance":
			//A faire
			break;
		case "mensuel":
			Engine.getPlayer().setGainParMois(Engine.getPlayer().getGainParMois() +  Integer.parseInt(change[1]));
			break;
		case "statObjet":
			break;
		}
		
	}

	public String toString() {
		return nom + ";" + resume + ";" + archetype + ";" + ";" + occurence + ";" + probabilite + ";";
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
		this.date = date;
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
		int[] joursRestants = new int[2];
		String[] tabString = texte.split("_");
		joursRestants[0] = Integer.parseInt(tabString[0]);
		joursRestants[1] = Integer.parseInt(tabString[1]);
	}
}

package mainPackage.gameEngine.event;

import java.util.ArrayList;
import java.util.Objects;

import mainPackage.gameEngine.jour.Date;

public class Event {

	
	//Plusieurs type  de Event, ce qui ont une proba de infinie sur une date, et le reste.
	private String resume;// Une explication de l'event
	private String nom;
	private Date date;
	private int occurence;
	private int probabilite;
	private String archetype;
	private ArrayList<ArrayList<Objects>> acces=new ArrayList<ArrayList<Objects>>();

	public Event()
	{
		this.probabilite=0;
		this.occurence=0;
	}
	
	public Event(Date date,String nom,String resume, String archetype,int occurence, ArrayList<ArrayList<Objects>> acces)//Noel,Paque,anniverssaire du joueur,JAPD,../
	{
		this.nom=nom;
		this.setOccurence(occurence);
		this.date=date;
		this.resume=resume;
		this.archetype=archetype;
		this.acces=acces;
		this.probabilite=-1;  //Infini
	}
	
	public Event(String nom,String resume,String archetype,int probabilite,int occurence, ArrayList<ArrayList<Objects>> acces)//tous le reste
	{
		this.nom=nom;
		this.setOccurence(occurence);
		this.resume=resume;
		this.archetype=archetype;
		this.acces=acces;
		this.probabilite=probabilite;
		this.date=null;
	}

	public String getResume() {
		return resume;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom=nom;
	}
	
	public String getArchetype() {
		return archetype;
	}
	
	public int getProbabilite() {
		return probabilite;
	}
	
	public void setProbabilite(int probabilite) {
		this.probabilite=probabilite;
	}
	
	public  ArrayList<ArrayList<Objects>> getAcces() {
		return acces;
	}
	
	public Date getDate() {
		return date;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
}

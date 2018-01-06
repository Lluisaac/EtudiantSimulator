package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.jour.Date;

public class Event {

	//Plusieurs type  de Event, ce qui ont une proba de infinie sur une date, et le reste.
	private String resume;// Une explication de l'event
	private String nom;
	private Date date;
	private int probabilite;
	private String archetype;
	private ArrayList<Event> acces = new ArrayList<Event>();

	
	public Event(Date date,String nom,String resume, String archetype,ArrayList<Event> acces)//Noel,Paque,anniverssaire du joueur,JAPD,../
	{
		this.nom=nom;
		this.date=date;
		this.resume=resume;
		this.archetype=archetype;
		this.acces=acces;
		this.probabilite=-1;  //Infini
	}
	
	public Event(String nom,String resume,String archetype,int probabilite,ArrayList<Event> acces)//tous le reste
	{
		this.nom=nom;
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
	
	public String getArchetype() {
		return archetype;
	}
	
	public int getProbabilite() {
		return probabilite;
	}
	
	public ArrayList<Event> getAcces() {
		return acces;
	}
	
	public Date getDate() {
		return date;
	}
}

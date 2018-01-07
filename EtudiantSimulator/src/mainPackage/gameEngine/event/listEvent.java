package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.jour.Jour;

public class listEvent {

	private Event[] listeEventDate = new Event[15]; //15 et 78 a modifier
	private Event[] listeEvent = new Event[78];
	private ArrayList<Event> listeEventModifie = new ArrayList<Event>();
	
	
	public listEvent()
	{
		//Creation de listeEventDate et listeEvent a partir d'un fichier.esm
		//Rappel pour la partit acces = "nom" , "probabilité" , "nbr jour", "occurence ajoute"
	}
	
	
	public int probaTotal(ArrayList<Event> listeEvent) // Renvoie la somme des proba de tous les events
	{
		int total=0;
		for(int i=0;i<listeEvent.size();i++)
		{
			total=total+listeEvent.get(i).getProbabilite();
		}
		return total;
	}
	
	public Event choisisEvent(Event[] listeEventDate, ArrayList<Event> listeEvent)
	{
		Event evenementChoisis;
		
		int nbrAlea=(int)( Math.random()*( probaTotal(listeEvent)*4 + 1 ) );
		evenementChoisis= listeEvent.get(nbrAlea);
		
		for(int i=0;i<listeEventDate.length;i++)
		{
			if(listeEventDate[i].getDate()== Jour.getDate())
			{
				evenementChoisis=listeEventDate[i];
			}
		}
		
		if(!evenementChoisis.getAcces().isEmpty()) //Pas vide :p
		{
			for(int i=0;i<evenementChoisis.getAcces().size();i++)
			{
				
			}
		}
		return evenementChoisis;
	}
	
}

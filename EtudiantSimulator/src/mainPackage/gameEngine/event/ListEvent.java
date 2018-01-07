package mainPackage.gameEngine.event;

import mainPackage.gameEngine.Engine;

public class ListEvent {

	private Event[] listeEventDate = new Event[15]; //15 et 78 a modifier
	private Event[] listeEvent = new Event[78];
	
	
	public ListEvent()
	{
		//Creation de listeEventDate et listeEvent a partir d'un fichier.esm
		//Rappel pour la partit acces = "nom" , "probabilité" , "nbr jour", "occurence ajoute" pour ce qui ont une certaines proba
		//Rappel pour la partit accces= "nom" , "date" pour ce qui ont 100% de proba d'arrivée
	}
	
	
	public int probaTotal() // Renvoie la somme des proba de tous les events
	{
		int total=0;
		for(int i=0;i<listeEvent.length;i++)
		{
			total=total+listeEvent[i].getProbabilite();
		}
		return total;
	}
	
	public Event touverEvent(String nom)//Trouve un event a partir de son nom
	{
		Event evenement= new Event();
		evenement.setNom("dhbfuevdbu<kvcuECVufvguef"); //Initialisation du nom peu probable qu'il soit le meme
		
		for(int i=0;i<this.listeEventDate.length;i++)
		{
			if(this.listeEventDate[i].getNom()==nom)
			{
					evenement=this.listeEventDate[i];
			}
		}
		for(int i=0;i<this.listeEvent.length;i++)
		{
			if(this.listeEvent[i].getNom()==nom)
			{
					evenement=this.listeEvent[i];
			}
		}
		
		return evenement;
	}
	
	public Event choisisEvent() //Prend l'evenement pour le jour meme
	{
		Event evenementChoisis;
		
		int nbrAlea=(int)( Math.random()*( probaTotal()*4 + 1 ) );
		evenementChoisis= this.listeEvent[nbrAlea];
		
		for(int i=0;i<listeEventDate.length;i++)
		{
			if(listeEventDate[i].getDate()== Engine.journee.getDate())
			{
				evenementChoisis=listeEventDate[i];
			}
		}
		
		evenementChoisis.setOccurence(evenementChoisis.getOccurence()-1); //reduit son occurence de 1
		
		return evenementChoisis;
	}
	
}

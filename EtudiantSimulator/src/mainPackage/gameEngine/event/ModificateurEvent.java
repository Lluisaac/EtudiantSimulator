package mainPackage.gameEngine.event;

import java.util.ArrayList;
import java.util.Objects;

public class ModificateurEvent { //Classe qui modifiras la listEvent pour les proba a venir ( quete )

	private ListEvent evenements;
	private ArrayList<ArrayList<Objects>> acces=new ArrayList<ArrayList<Objects>>();
	
	public ModificateurEvent(ListEvent evenement, ArrayList<ArrayList<Objects>> acces)
	{
		this.evenements=evenement;
		this.acces=acces;
	}
	
	
	public ListEvent applicateur(Event evenement)
	{
		evenement.getAcces();
		for(int i=0;i<evenement.getAcces().size();i++)
		{
			evenement.getAcces().get(i).get(0); //le nom
		}
		return this.evenements;
	}
	
	public ListeEvent regulateur()//Verifie que des probas ne doivent pas etre ajuste
	{
		
	}
}

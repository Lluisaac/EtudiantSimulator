package mainPackage.gameEngine.event;

import java.util.ArrayList;
import java.util.Objects;

public class ModificateurEvent { //Classe qui modifiras la listEvent pour les proba a venir ( quete )

	private ListEvent evenements;
	
	public ModificateurEvent(ListEvent evenements)
	{
		this.evenements=evenements;
	}
	
	
	public ListEvent applicateur(Event evenement) //A faire
	{
		Event evenementModif;
		for(int i=0;i<evenement.getAcces().size();i++)
		{
			evenementModif=evenements.trouverEvent( evenement.getAcces().get(i).get(0) );
			
			if(evenementModif.getDate()==null)
			{
				evenementModif.setProbabilite( Integer.parseInt( evenement.getAcces().get(i).get(1) ) );
			}
		}
		return this.evenements;
	}
	
	public ListeEvent regulateur()//Verifie que des probas ne doivent pas etre ajuste
	{
		
	}
}

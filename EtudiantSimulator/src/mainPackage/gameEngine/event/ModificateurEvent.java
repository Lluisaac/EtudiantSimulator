package mainPackage.gameEngine.event;

import java.util.ArrayList;
import java.util.Objects;

public class ModificateurEvent {

	private ListEvent evenement;
	private ArrayList<ArrayList<Objects>> acces=new ArrayList<ArrayList<Objects>>();
	
	public ModificateurEvent(ListEvent evenement, ArrayList<ArrayList<Objects>> acces)
	{
		this.evenement=evenement;
		this.acces=acces;
	}
	
	
}

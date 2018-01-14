package mainPackage.gameEngine.event;

public class ModificateurEvent { // A fusionner avec ListEvent

	public void applicateur(Event evenement)
	{
		Event evenementModif;
		for (int i = 0; i < evenement.getAcces().size(); i++) {
			evenementModif = ListEvent.trouverEvent(evenement.getAcces().get(i).get(ListEvent.INDICE_NOM));

			if (evenementModif.getDate() == null) {
				evenementModif.setProbabilite(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_PROBA)));
				evenementModif.setOccurence(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_OCCURENCE)));
				evenementModif.setJoursRestantsProbaAjoutee(evenement.getAcces().get(i).get(ListEvent.INDICE_JOURS_RESTANTS));

			}
			else
			{
				evenementModif.setDate( evenement.getAcces().get(i).get(ListEvent.INDICE_DATE) );
				evenementModif.setOccurence(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_OCCURENCE)));
			}
		}
	}

	public void regulateur()// Verifie que des probas ne doivent pas etre
									// ajuste par occurence, vérifie les jours restants et regule
	{
		int[] jourRestant=new int[2];
		for(int i=0;i<ListEvent.getListeEvent().length;i++)
		{
			jourRestant[0]=ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0]-1;
			jourRestant[1]=ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[1];
			
			if(ListEvent.getListeEvent()[i].getOccurence()==0 )
			{
				ListEvent.getListeEvent()[i].setProbabilite(0);
			}
			
			switch(ListEvent.getListeEvent()[i].getJoursRestantsProbaAjoutee()[0])//A faire
			{
				case(0):
				case(-1):
				break;
				case(1):
					ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee( jourRestant );
					ListEvent.getListeEvent()[i].setProbabilite(jourRestant[1]);
				default:
					ListEvent.getListeEvent()[i].setJoursRestantsProbaAjoutee( jourRestant );
			}
			
		}
	}
}

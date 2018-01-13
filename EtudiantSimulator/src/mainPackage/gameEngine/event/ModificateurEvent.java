package mainPackage.gameEngine.event;

public class ModificateurEvent { // A fusionner avec ListEvent

	public void applicateur(Event evenement) // A faire
	{
		Event evenementModif;
		for (int i = 0; i < evenement.getAcces().size(); i++) {
			evenementModif = ListEvent.trouverEvent(evenement.getAcces().get(i).get(ListEvent.INDICE_NOM));

			if (evenementModif.getDate() == null) {
				evenementModif.setProbabilite(Integer.parseInt(evenement.getAcces().get(i).get(ListEvent.INDICE_PROBA)));
			}
		}
	}

	public void regulateur()// Verifie que des probas ne doivent pas etre
									// ajuste
	{

	}
}

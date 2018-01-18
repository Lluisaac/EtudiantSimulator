package mainPackage.gameEngine.event;


import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class ModificateurObjet { //L'ordre est à respecter
	
	private String nom;
	private float[] attributs;
	
	ModificateurObjet(String modif)
	{
		String[] contentTab = modif.split("|");
		String[] contentTab2 = contentTab[1].split("_");
		
		this.nom=contentTab[0];
		for(int i=0;i<contentTab[0].length();i++)
		{
			this.attributs[i]=Float.parseFloat(contentTab2[i]);
		}
		
	}
	
	public void appliquer()
	{
		ListeObjets.trouveObjet(this.nom).setAttributs(attributs);
	}
	
}
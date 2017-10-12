
package mainPackage.gameEngine.objetsMarket;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

@SuppressWarnings("unused")
public class ObjetBonus extends ObjetGeneral {

	public ObjetBonus(String nom, float[] attribut) {
		super(nom, attribut);
		String[] titres = {"Prix", "Savoir", "Faim", "Fatigue", "Bonheur", "Tmps Perdu" ,"Durabilité"};
		this.setTitres(titres);
	}

	@Override
	public void affectation(Player player) {

		if(!this.getPurchaseDate().superieurDate(Engine.journee.getDate() )) 
			//Vérifie que PurchaseDate soit dépassé ou égal a la date de validité
		{
			player.setArgent(player.getArgent() - this.attributs[0]);
			player.setSavoir(player.getSavoir() + this.attributs[1]);
			player.setFaim(player.getFaim() + this.attributs[2]);
			player.setFatigue(player.getFatigue() + this.attributs[3]);
			player.setBonheur(player.getBonheur() + this.attributs[4]);
			Engine.journee.setTempsLibreJ((int) (Engine.journee.getTempsLibreJ() - this.attributs[5]));
			Date newDate=new Date(Engine.journee.getDate());
			newDate.addJour(Math.round(this.attributs[6]));
			this.setPurchaseDate(newDate);
		
		}
	}
}

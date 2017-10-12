
package mainPackage.gameEngine.objetsMarket;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

@SuppressWarnings("unused")
public class ObjetUpgrade extends ObjetGeneral {
	
	public ObjetUpgrade(String nom, float[] attribut) {
		super(nom, attribut);
		String[] titres = {"Prix", "Argent J", "Savoir J", "Faim J", "Fatigue J", "Bonheur J", "Tmps Libre", "Durabilité"};
		this.setTitres(titres);
	}

	@Override
	public void affectation(Player player) {

		if( !this.getPurchaseDate().superieurDate(Engine.journee.getDate()))
		{
			player.setArgent(player.getArgent() - this.attributs[0]);
			player.setArgentJ((int) (player.getArgentJ() + this.attributs[1]));
			player.setSavoirJ((int) (player.getSavoirJ() + this.attributs[2]));
			player.setFaimJ((int) (player.getFaimJ() + this.attributs[3]));
			player.setFatigueJ((int) (player.getFatigueJ() + this.attributs[4]));
			player.setBonheurJ((int) (player.getBonheurJ() + this.attributs[5]));
			Engine.journee.setTempsLibreJ((int) (Engine.journee.getTempsLibreJ() + this.attributs[6]));
			Date newDate=new Date(Engine.journee.getDate());
			newDate.addJour(Math.round(this.attributs[7]));
			this.setPurchaseDate(newDate);
		}
	}
	//GitHub Test
}

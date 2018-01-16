
package mainPackage.gameEngine.objetsMarket;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

@SuppressWarnings("unused")
public class ObjetUpgrade extends ObjetGeneral {
	
	public ObjetUpgrade(String nom, float[] attribut, boolean debloque) {
		super(nom, attribut, debloque);
		String[] titres = {"Prix", "Argent J", "Savoir J", "Faim J", "Fatigue J", "Bonheur J", "Tmps Libre", "Durabilit�"};
		this.setTitres(titres);
	}

	@Override
	public void affectation(Player player) {
		if( (!this.getEndOfPurchaseDate().superieurDate(Engine.journee.getDate()) && this.isDebloque())) {
			
			player.setArgent(player.getArgent() - this.attributs[0]);
			player.setArgentJ((int) (player.getArgentJ() + this.attributs[1]));
			player.setSavoirJ((int) (player.getSavoirJ() + this.attributs[2]));
			player.setFaimJ((int) (player.getFaimJ() + this.attributs[3]));
			player.setFatigueJ((int) (player.getFatigueJ() + this.attributs[4]));
			player.setBonheurJ((int) (player.getBonheurJ() + this.attributs[5]));
			
			Engine.journee.setTempsLibre((int) (Engine.journee.getTempsLibre() + this.attributs[6]));
			Date newDate=new Date(Engine.journee.getDate());
			newDate.addJour(Math.round(this.attributs[7]));
			this.setEndOfPurchaseDate(newDate);
			this.setDebloque(false);
		}
	}

	public void refreshDebloque() {
		if (Engine.journee.getDate().equals(this.getEndOfPurchaseDate())) {
			this.setDebloque(true);
			Engine.getPlayer().setArgentJ((int) (Engine.getPlayer().getArgentJ() - this.attributs[1]));
			Engine.getPlayer().setSavoirJ((int) (Engine.getPlayer().getSavoirJ() - this.attributs[2]));
			Engine.getPlayer().setFaimJ((int) (Engine.getPlayer().getFaimJ() - this.attributs[3]));
			Engine.getPlayer().setFatigueJ((int) (Engine.getPlayer().getFatigueJ() - this.attributs[4]));
			Engine.getPlayer().setBonheurJ((int) (Engine.getPlayer().getBonheurJ() - this.attributs[5]));
		}
	}
}

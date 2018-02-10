
package mainPackage.gameEngine.objetsMarket;

import java.util.ArrayList;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.modificateur.ModificateurEvent;
import mainPackage.gameEngine.modificateur.ModificateurObjet;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

@SuppressWarnings("unused")
public class ObjetBonus extends ObjetGeneral {

	public ObjetBonus(String nom, float[] attribut, boolean debloque, ArrayList<ModificateurEvent> modifEvent, ArrayList<ModificateurObjet> modifObjet) {
		super(nom, attribut, debloque, modifEvent, modifObjet);
		String[] titres = { "Prix", "Savoir", "Faim", "Fatigue", "Bonheur", "Tmps Perdu", "Durabilit�" };
		this.setTitres(titres);
	}

	@Override
	public void affectation(Player player) {
		if ((!this.getEndOfPurchaseDate().superieurDate(Engine.journee.getDate()) && this.isDebloque())) {
		// V�rifie que PurchaseDate soit d�pass� ou �gal a la date de validit�
			player.setArgent(player.getArgent() - this.attributs[0]);
			player.setSavoir(player.getSavoir() + this.attributs[1]);
			player.setFaim(player.getFaim() + this.attributs[2]);
			player.setFatigue(player.getFatigue() + this.attributs[3]);
			player.setBonheur(player.getBonheur() + this.attributs[4]);
			
			Engine.journee.setBuffer((int) (Engine.journee.getBuffer() - this.attributs[5]));
			
			Date newDate = new Date(Engine.journee.getDate());
			newDate.addJour(Math.round(this.attributs[6]));
			this.setEndOfPurchaseDate(newDate);
			this.setDebloque(false);
		}
	}

	@Override
	public void refreshDebloque() {
		if (Engine.journee.getDate().equals(this.getEndOfPurchaseDate())) {
			this.setDebloque(true);
		}
	}
}

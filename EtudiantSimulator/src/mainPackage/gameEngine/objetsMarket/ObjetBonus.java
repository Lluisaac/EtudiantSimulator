
package mainPackage.gameEngine.objetsMarket;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;


public class ObjetBonus extends ObjetGeneral {

	public ObjetBonus(Node item) {
		super(item);
		this.attributs = new float[7];

		NamedNodeMap attributs = item.getFirstChild().getAttributes();

		this.attributs[0] = Float.parseFloat(attributs.getNamedItem("cout").getNodeValue());
		this.attributs[1] = Float.parseFloat(attributs.getNamedItem("savoir").getNodeValue());
		this.attributs[2] = Float.parseFloat(attributs.getNamedItem("faim").getNodeValue());
		this.attributs[3] = Float.parseFloat(attributs.getNamedItem("fatigue").getNodeValue());
		this.attributs[4] = Float.parseFloat(attributs.getNamedItem("bonheur").getNodeValue());
		this.attributs[5] = Float.parseFloat(attributs.getNamedItem("tempsLibre").getNodeValue());
		this.attributs[6] = Float.parseFloat(attributs.getNamedItem("durabilite").getNodeValue());

		String[] titres = {"Prix:", "savoir", "faim", "fatigue", "bonheur", "tempsLibre",
				"durabilite"};

		this.setTitres(titres);
	}
	
	@Override
	public void acheter() {
		this.appliquer();
	}

	@Override
	public void appliquer() {
		if ((!this.getEndOfPurchaseDate().superieurDate(Engine.journee.getDate()) && this.isDebloque())) {
			Engine.getPlayer().setArgent(Engine.getPlayer().getArgent() - this.attributs[0]);
			Engine.getPlayer().setSavoir(Engine.getPlayer().getSavoir() + this.attributs[1]);
			Engine.getPlayer().setFaim(Engine.getPlayer().getFaim() + this.attributs[2]);
			Engine.getPlayer().setFatigue(Engine.getPlayer().getFatigue() + this.attributs[3]);
			Engine.getPlayer().setBonheur(Engine.getPlayer().getBonheur() + this.attributs[4]);
			
			Engine.journee.setBuffer((int) (Engine.journee.getBuffer() - this.attributs[5]));
			
			Date newDate = new Date(Engine.journee.getDate());
			newDate.addJour(Math.round(this.attributs[6]));
			this.setEndOfPurchaseDate(newDate);
			this.setDebloque(false);
			super.appliquerModif();
		}
	}

	@Override
	public void refreshDebloque() {
		if (Engine.journee.getDate().equals(this.getEndOfPurchaseDate())) {
			this.setDebloque(true);
		}
	}
}

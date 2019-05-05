
package mainPackage.gameEngine.objetsMarket;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class ObjetUpgrade extends ObjetGeneral {

	public ObjetUpgrade(Node item) {
		super(item);

		this.attributs = new float[8];

		NamedNodeMap attributs = item.getFirstChild().getAttributes();

		this.attributs[0] = Float.parseFloat(attributs.getNamedItem("cout").getNodeValue());
		this.attributs[1] = Float.parseFloat(attributs.getNamedItem("argentJ").getNodeValue());
		this.attributs[2] = Float.parseFloat(attributs.getNamedItem("savoirJ").getNodeValue());
		this.attributs[3] = Float.parseFloat(attributs.getNamedItem("faimJ").getNodeValue());
		this.attributs[4] = Float.parseFloat(attributs.getNamedItem("fatigueJ").getNodeValue());
		this.attributs[5] = Float.parseFloat(attributs.getNamedItem("bonheurJ").getNodeValue());
		this.attributs[6] = Float.parseFloat(attributs.getNamedItem("tempsLibreJ").getNodeValue());
		this.attributs[7] = Float.parseFloat(attributs.getNamedItem("durabilite").getNodeValue());

	}

	@Override
	public void acheter() {
		ListeObjets.addUpgrade(this);

		Engine.getPlayer().setArgent(Engine.getPlayer().getArgent() - this.attributs[0]);

		Date newDate = new Date(Engine.journee.getDate());
		newDate.addJour(Math.round(this.attributs[7]));
		this.setEndOfPurchaseDate(newDate);
		this.setDebloque(false);
		super.appliquerModification();
	}

	@Override
	public void appliquer() {
		Engine.getPlayer().setArgent((int) (Engine.getPlayer().getArgent() + this.attributs[1]));
		Engine.getPlayer().setSavoir((int) (Engine.getPlayer().getSavoir() + this.attributs[2]));
		Engine.getPlayer().setFaim((int) (Engine.getPlayer().getFaim() + this.attributs[3]));
		Engine.getPlayer().setFatigue((int) (Engine.getPlayer().getFatigue() + this.attributs[4]));
		Engine.getPlayer().setBonheur((int) (Engine.getPlayer().getBonheur() + this.attributs[5]));
		Engine.journee.setBuffer((int) (Engine.journee.getBuffer() + this.attributs[6]));

	}

	public void refreshDebloque() {
		if (this.getEndOfPurchaseDate().equals(Engine.journee.getDate())) {
			this.setDebloque(true);
			ListeObjets.removeUpgrade(this);
		}
	}
}

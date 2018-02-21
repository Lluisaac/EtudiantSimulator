package mainPackage.gameEngine.modificateur;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import mainPackage.gameEngine.Engine;

public class ModificateurPlayer {// L'ordre est à respecter
	private float argent;
	private float savoir;
	private float faim;
	private float fatigue;
	private float bonheur;

	private int argentJ;
	private int savoirJ;
	private int faimJ;
	private int fatigueJ;
	private int bonheurJ;

	private int gainParMois;
	private int loyer;

	private int tempsLibre;
	private int tempsLibreJ;

	public ModificateurPlayer(Node item) {
		NamedNodeMap attributs = item.getAttributes();

		for (int i = 0; i < attributs.getLength(); i++) {
			switch (attributs.item(i).getNodeName()) {
			case "argent":
				this.argent = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "savoir":
				this.savoir = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "faim":
				this.faim = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "fatigue":
				this.fatigue = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "bonheur":
				this.bonheur = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "argentJ":
				this.argentJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "savoirJ":
				this.savoirJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "faimJ":
				this.faimJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "fatigueJ":
				this.fatigueJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "bonheurJ":
				this.bonheurJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "gainParMois":
				this.gainParMois = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "loyer":
				this.loyer = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "tempsLibre":
				this.tempsLibre = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			case "tempsLibreJ":
				this.tempsLibreJ = Integer.parseInt(attributs.item(i).getNodeValue());
				break;
			}
		}
	}

	public void appliquer() {
		Engine.getPlayer().setArgent(this.argent + Engine.getPlayer().getArgent());
		Engine.getPlayer().setSavoir(this.savoir + Engine.getPlayer().getSavoir());
		Engine.getPlayer().setFaim(this.faim + Engine.getPlayer().getFaim());
		Engine.getPlayer().setFatigue(this.fatigue + Engine.getPlayer().getFatigue());
		Engine.getPlayer().setBonheur(this.bonheur + Engine.getPlayer().getBonheur());

		Engine.getPlayer().setArgentJ(this.argentJ + Engine.getPlayer().getArgentJ());
		Engine.getPlayer().setSavoirJ(this.savoirJ + Engine.getPlayer().getSavoirJ());
		Engine.getPlayer().setFaimJ(this.faimJ + Engine.getPlayer().getFaimJ());
		Engine.getPlayer().setFatigueJ(this.fatigueJ + Engine.getPlayer().getFatigueJ());
		Engine.getPlayer().setBonheurJ(this.bonheurJ + Engine.getPlayer().getBonheurJ());

		Engine.getPlayer().setGainParMois(this.gainParMois + Engine.getPlayer().getGainParMois());
		Engine.getPlayer().setLoyer(this.loyer + Engine.getPlayer().getLoyer());

		Engine.journee.setBuffer((int) (Engine.journee.getBuffer() - this.tempsLibre));
		Engine.journee.setTempsLibreJ((int) (Engine.journee.getTempsLibreJ() + this.tempsLibreJ));
	}
}

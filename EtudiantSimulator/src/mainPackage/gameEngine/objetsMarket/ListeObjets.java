package mainPackage.gameEngine.objetsMarket;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import mainPackage.gameEngine.Engine;

public class ListeObjets {

	private static ArrayList<ObjetGeneral> listeObjets = new ArrayList<ObjetGeneral>();
	
	private static ArrayList<ObjetUpgrade> upgrades = new ArrayList<ObjetUpgrade>();

	public static void refreshListeObjets() {
		for (int i = 0; i < ListeObjets.getListeObjets().size(); i++) {
			ListeObjets.getListeObjets().get(i).refreshDebloque();
		}
	}
	
	public static void genererListe() throws SAXException, IOException, ParserConfigurationException {
		
		Element listObjets = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Engine.parseXML("listes\\objets.xml")).getDocumentElement();
		
		NodeList objets = listObjets.getChildNodes();
		
		for (int i = 0; i < objets.getLength(); i++) {
			
			if (objets.item(i).getFirstChild().getNodeName().equals("upgrade")) {
				ListeObjets.listeObjets.add(new ObjetUpgrade(objets.item(i)));
			} else {
				ListeObjets.listeObjets.add(new ObjetBonus(objets.item(i)));
			}
		}		
	}

	public static ArrayList<ObjetGeneral> getlisteObjetsDebloques() {

		ArrayList<ObjetGeneral> temp = new ArrayList<ObjetGeneral>();

		for (int i = 0; i < ListeObjets.listeObjets.size(); i++) {
			if (ListeObjets.listeObjets.get(i).isDebloque()) {
				temp.add(ListeObjets.listeObjets.get(i));
			}
		}
		return temp;
	}

	public static ArrayList<ObjetGeneral> getListeObjets() {
		return listeObjets;
	}
	
	public static void setlisteObjets(ArrayList<ObjetGeneral> listeObjets) {

		ListeObjets.listeObjets = listeObjets;
	}

	public static ObjetGeneral getObjetGeneral(int i) {
		return listeObjets.get(i);
	}

	public static String staticToString() {
		String r = "";

		for (int i = 0; i < ListeObjets.listeObjets.size(); i++) {
			if (!ListeObjets.listeObjets.get(i).isDebloque() && ListeObjets.listeObjets.get(i) != null) {
				r += ListeObjets.listeObjets.get(i).getNom() + ","
						+ ListeObjets.listeObjets.get(i).getEndOfPurchaseDate().toString() + ";";
			}
		}
		return r;
	}

	public static void genererListe(String save) {
		throw new SecurityException("This save is deprecated");
	}

	public static void resetListe() {
		ListeObjets.listeObjets = new ArrayList<ObjetGeneral>();
		try {
			ListeObjets.genererListe();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static ObjetGeneral trouveObjet(String nom) {
		for (int i = 0; i < listeObjets.size(); i++) {
			if (nom.equals(listeObjets.get(i).getNom())) {
				return listeObjets.get(i);
			}
		}
		for (int i = 0; i < listeObjets.size(); i++) {
			if (nom.equals(listeObjets.get(i).getResume())) {
				return listeObjets.get(i);
			}
		}
		return null;
	}
	
	public static void addUpgrade(ObjetUpgrade upg) {
		ListeObjets.upgrades.add(upg);
	}
	
	public static void removeUpgrade(ObjetUpgrade upg) {
		ListeObjets.upgrades.remove(upg);
	}
	
	public static void appliquerUpgrade() {
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			ListeObjets.upgrades.get(i).appliquer();
		}
	}
	
	public static float getUpgradeArgent() {
		int com = 0;
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			com += ListeObjets.upgrades.get(i).getAttributs()[1];
		}
		return com;
	}
	
	public static float getUpgradeSavoir() {
		int com = 0;
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			com += ListeObjets.upgrades.get(i).getAttributs()[2];
		}
		return com;
	}
	
	public static float getUpgradeFaim() {
		int com = 0;
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			com += ListeObjets.upgrades.get(i).getAttributs()[3];
		}
		return com;
	}
	
	public static float getUpgradeFatigue() {
		int com = 0;
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			com += ListeObjets.upgrades.get(i).getAttributs()[4];
		}
		return com;
	}

	public static float getUpgradeBonheur() {
		int com = 0;
		for (int i = 0; i < ListeObjets.upgrades.size(); i++) {
			com += ListeObjets.upgrades.get(i).getAttributs()[5];
		}
		return com;
	}
}
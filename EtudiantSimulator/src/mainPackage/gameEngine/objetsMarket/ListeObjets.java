package mainPackage.gameEngine.objetsMarket;

import java.io.File;
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

	public static void refreshListeObjets() {
		for (int i = 0; i < ListeObjets.getListeObjets().size(); i++) {
			ListeObjets.getListeObjets().get(i).refreshDebloque();
		}
		Engine.getWindow().actualiserMagasin();
	}
	
	public static void genererListe() throws SAXException, IOException, ParserConfigurationException {
		Element listObjets = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("listes\\objets.xml")).getDocumentElement();
		
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
		return null;
	}
}
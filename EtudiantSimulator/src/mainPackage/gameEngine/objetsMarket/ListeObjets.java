package mainPackage.gameEngine.objetsMarket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;

public class ListeObjets {

	private static ArrayList<ObjetGeneral> listeObjets = new ArrayList<ObjetGeneral>();

	public static ArrayList<ObjetGeneral> getListeObjets() {

		return listeObjets;
	}
	
	public static void refreshListeObjets() {
		for (int i = 0; i < ListeObjets.getListeObjets().size(); i++) {
			ListeObjets.getListeObjets().get(i).refreshDebloque();
		}
		Engine.getWindow().actualiserMagasin();
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
				r+= ListeObjets.listeObjets.get(i).getNom() + "," + ListeObjets.listeObjets.get(i).getEndOfPurchaseDate().toString() + ";";
			}
		}
		return r;
	}

	public static void genererListe() {
		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("listes\\objets.etsim");

			byte[] buffer = new byte[8];

			while (file.read(buffer) >= 0) {
				for (byte byt : buffer) {
					content += (char) byt;
				}
			}

			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] contentTab = content.split("\n");

		for (int i = 0; i < contentTab.length-1; i++) {

			String[] objetsInfo = contentTab[i].split(";");

			String[] listeAttributs = objetsInfo[2].split("\\,");
			float[] attributs = new float[listeAttributs.length];

			for (int j = 0; j < listeAttributs.length; j++) {
				attributs[j] = Float.parseFloat(listeAttributs[j]);
			}
			
			Boolean debloque;
			
			debloque=objetsInfo[3].equals("true");
			
			if (objetsInfo[0].equals("Upgrade")) {
				ListeObjets.listeObjets.add(new ObjetUpgrade(objetsInfo[1], attributs,debloque));
			} else {
				ListeObjets.listeObjets.add(new ObjetBonus(objetsInfo[1], attributs,debloque ));
			}
		}
	}

	public static void genererListe(String save) {
		ListeObjets.genererListe();
		String[] val = save.split(";");
		
		for (int i = 0; i < val.length; i++) {
			String[] val2 = val[i].split("\\,");
			for (int j = 0; j < ListeObjets.listeObjets.size(); j++) {
				
				if (ListeObjets.listeObjets.get(j).getNom().equals(val2[0])) {			
					ListeObjets.listeObjets.get(j).setEndOfPurchaseDate(new Date(val2[1]));
				}
			}
		}
	}

	public static void resetListe() {
		ListeObjets.listeObjets = new ArrayList<ObjetGeneral>();
		ListeObjets.genererListe();	
	}
}
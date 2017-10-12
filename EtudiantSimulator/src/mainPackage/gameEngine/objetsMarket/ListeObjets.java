package mainPackage.gameEngine.objetsMarket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ListeObjets {

	private static ArrayList<ObjetGeneral> listeObjets = new ArrayList<ObjetGeneral>();

	public ListeObjets() {
		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("objets.etsim");

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

		for (int i = 0; i < contentTab.length; i++) {

			String[] objetsInfo = contentTab[i].split(";");

			String[] listeAttributs = objetsInfo[2].split(",");
			float[] attributs = new float[listeAttributs.length];

			for (int j = 0; j < listeAttributs.length; j++) {
				attributs[j] = Float.parseFloat(listeAttributs[j]);
			}

			if (objetsInfo[0] == "Upgrade") {
				ListeObjets.listeObjets.add(new ObjetUpgrade(objetsInfo[1], attributs));
			} else {
				ListeObjets.listeObjets.add(new ObjetBonus(objetsInfo[1], attributs));
			}
		}
	}

	public static ArrayList<ObjetGeneral> getlisteObjets() {

		return listeObjets;
	}

	public static void setlisteObjets(ArrayList<ObjetGeneral> listeFil) {

		listeObjets = listeFil;
	}

	public static ArrayList<ObjetGeneral> getListeFilDebloquees() {
		ArrayList<ObjetGeneral> r = new ArrayList<ObjetGeneral>();
		for (int i = 0; i < listeObjets.size(); i++) {
			if (listeObjets.get(i).isDebloque()) {
				r.add(listeObjets.get(i));
			}
		}
		return r;
	}

	public static ObjetGeneral getObjetGeneral(int i) {
		return listeObjets.get(i);
	}
	
	public String toString() {
		String r = "";
		
		for (int i = 0; i < ListeObjets.listeObjets.size(); i++) {
			r += ListeObjets.listeObjets.get(i) + "\n";
		}
		
		return r;
	}
}
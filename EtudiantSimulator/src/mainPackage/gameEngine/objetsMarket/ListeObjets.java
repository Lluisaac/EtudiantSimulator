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

<<<<<<< HEAD
		String[] contentTab = content.split("\n");

=======
>>>>>>> 3effa97493ff1e3e19374f0411556f7e2a6bf9f3
		for (int i = 0; i < contentTab.length-1; i++) {

			String[] objetsInfo = contentTab[i].split(";");

			String[] listeAttributs = objetsInfo[2].split(",");
			float[] attributs = new float[listeAttributs.length];

			for (int j = 0; j < listeAttributs.length; j++) {
				attributs[j] = Float.parseFloat(listeAttributs[j]);
			}
			
			Boolean debloque;
			
<<<<<<< HEAD
=======
			String test= objetsInfo[3];
			System.out.print(test);
			System.out.println(test.equals("true"));
			
>>>>>>> 3effa97493ff1e3e19374f0411556f7e2a6bf9f3
			debloque=objetsInfo[3].equals("true");
			
			if (objetsInfo[0].equals("Upgrade")) {
				ListeObjets.listeObjets.add(new ObjetUpgrade(objetsInfo[1], attributs,debloque));
			} else {
				ListeObjets.listeObjets.add(new ObjetBonus(objetsInfo[1], attributs,debloque ));
			}
		}
	}

	public static ArrayList<ObjetGeneral> getlisteObjets() {

		return listeObjets;
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
	
	public String toString() {
		String r = "";
		
		for (int i = 0; i < ListeObjets.listeObjets.size(); i++) {
			r += ListeObjets.listeObjets.get(i) + "\n";
		}
		
		return r;
	}
}

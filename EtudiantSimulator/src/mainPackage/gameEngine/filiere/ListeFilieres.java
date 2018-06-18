package mainPackage.gameEngine.filiere;

import java.util.ArrayList;

import javax.swing.JFrame;


public class ListeFilieres {
	
	private static ArrayList<Filiere> listeFilieres = new ArrayList<Filiere>();	
	
	public static void mettreFillieres() {
		getListeFilieres().add(new Filiere("DUT", 2, new int[] {7, 7, 7, 4, 6, 0, 0}, true));
		getListeFilieres().add(new Filiere("BTS", 2, new int[] {8, 7, 5, 8, 7, 0, 0}, true));
		getListeFilieres().add(new Filiere("Licence", 3, new int[] {3, 6, 4, 5, 4, 0, 0}, true));
		getListeFilieres().add(new Filiere("trololo", 3, new int[] {3, 6, 4, 5, 4, 0, 0}, true));
	}	
	
	public static ArrayList<Filiere> getListeFilieres() {
	
		return listeFilieres;
	}

	public static void setListeFilieres(ArrayList<Filiere> listeFil) {
	
		listeFilieres = listeFil;
	}
	
	public static ArrayList<Filiere> getListeFilDebloquees() {
		ArrayList<Filiere> r = new ArrayList<Filiere>();
		for (int i = 0; i < listeFilieres.size(); i++) {
			if (listeFilieres.get(i).isDebloque()) {
				r.add(listeFilieres.get(i));
			}
		}
		return r;
	}
	
	public static Filiere getFiliere(int i) {
		return listeFilieres.get(i);
	}
}

/*Mettre des commentaire dans vos codes*/
package mainPackage;

import mainPackage.graphicsEngine.design.DesignTask;

public class Main {

	public static void main(String[] args) {

		// Nettoyage de la console
		System.out.flush();
		System.err.flush();

		DesignTask.setBestLookAndFeelAvailable();

		Controller.start();		
	}
}

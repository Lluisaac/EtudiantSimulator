package mainPackage;

import javax.swing.JFrame;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;
import mainPackage.graphicsEngine.window.MenuWindow;

public class Controller 
{
	public static void start()
	{
		JFrame temp = new JFrame();

		MenuWindow menuWindow = new MenuWindow(temp);
		int selection = menuWindow.showDialog();
			
		switch (selection) {
			case -1:
				System.exit(0);
				break;

			case 0:
				temp.dispose();

				Engine.createEngine(false, null);
				break;

			case 1:
				Filiere filiere = ListeFilieres.askFiliere(temp);

				temp.dispose();

				Engine.createEngine(true, filiere);
				break;
		}
	}
}

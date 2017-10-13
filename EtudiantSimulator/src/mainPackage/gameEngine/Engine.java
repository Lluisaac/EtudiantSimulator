
package mainPackage.gameEngine;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;
import mainPackage.gameEngine.jour.Jour;
import mainPackage.gameEngine.objetsMarket.ListeObjets;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.dialog.FiliaireDialog;
import mainPackage.graphicsEngine.window.MainWindow;

public class Engine
{

	public static boolean jeuFini = false;

	// Player One
	private static Player player;

	public static Jour journee;

	private static MainWindow window;

	public static void createEngine(boolean newGame, Filiere filiaire) 
	{
		
		if (newGame)
		{
			player = new Player(filiaire, false);
		}
		else
		{
			player = loadGame();
		}
		
		journee = new Jour();

		window = new MainWindow();

		window.reinitialiserCalendrier();
		
		ListeObjets listeObjets = new ListeObjets();
		
		gameLoop();
	}

	public static void gameLoop() {

		window.actualiserMagasin();
		while (!jeuFini)
		{

			verifierFinDeJeu();
			regulation();

			while (!window.isValidated())
			{
				regulation();
				window.actualiserBesoins();
			}

			try
			{
				Thread.sleep(window.getWaitingTime());
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			window.actualiserBesoins();

			journee = new Jour(journee);
			boolean temp = journee.declencherJour(window);
			jeuFini = temp;

			window.mettreJour();
			window.creerContenuJour();
		}
	}

	private static void verifierFinDeJeu() {

		if (player.getFaim() <= 0)
		{
			int rep = JOptionPane.showConfirmDialog(window,
					"Apr�s avoir battu le record du faible poids pour un adulte, vous un Darwin Award : vous mourrez de faim !\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION);
			if (rep == JOptionPane.YES_OPTION)
			{
				restart();
			}
			else
			{
				System.exit(0);
			}
		}
		else if (player.getFatigue() >= 100)
		{
			int rep = JOptionPane.showConfirmDialog(window,
					"La fatigue cr�e des l�sions dans votre cerveau et vous �tes intern� dans un centre m�dical jusqu'� votre euthanasie 25 ans plus tard\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION);
			if (rep == JOptionPane.YES_OPTION)
			{
				restart();
			}
			else
			{
				System.exit(0);
			}
		}
		else if (player.getArgent() < (0 - player.getArgentDepart() - player.getLoyer()))
		{
			int rep = JOptionPane.showConfirmDialog(window,
					"La banque saisis tous vos bien et vous vous retrouvez dans la rue, seul. Vous mourrez 8 ans plus tard a cause du sida apr�s avoir vendu votre corps\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION);
			if (rep == JOptionPane.YES_OPTION)
			{
				restart();
			}
			else
			{
				System.exit(0);
			}
		}
		else if (player.getBonheur() < 0)
		{
			int rep = JOptionPane.showConfirmDialog(window,
					"Vous �tes trop malheureux... *Musique Triste* Le suicide est votre seule option a pr�sent, et vous reussissez\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION);
			if (rep == JOptionPane.YES_OPTION)
			{
				restart();
			}
			else
			{
				System.exit(0);
			}
		}
		else if (journee.getAnnee() - 1 > 0 && journee.getJour() == 1 && journee.getMois() == 1)
		{
			if (getPlayer().getFiliaire().getDuree() < journee.getAnnee() && getPlayer().checkSavoirTotal())
			{
				int rep = JOptionPane.showConfirmDialog(window,
						"Et c'est une victoire!!! Bravo, vous avez reussi avec brio votre ann�e scolaire!\n Voulez-vous recommencer une nouvelle partie ?",
						"Oh mon dieu il a gagn�! !", JOptionPane.YES_NO_OPTION);
				if (rep == JOptionPane.YES_OPTION)
				{
					restart();
				}
				else
				{
					System.exit(0);
				}
			}
			else if (getPlayer().checkSavoir())
			{
				getPlayer().prelassage();
				JOptionPane.showMessageDialog(window,
						"Vous avez r�ussi votre premi�re ann�e de " + getPlayer().getFiliaire().getNom() + " !",
						"GG !", JOptionPane.OK_CANCEL_OPTION);
			}
			else
			{
				int rep = JOptionPane.showConfirmDialog(window,
						"Vous avez rat� votre ann�e, passez moins de temps a faire des b�tises la prochaine fois!\n Voulez-vous recommencer une nouvelle partie ?",
						"Perdu !", JOptionPane.YES_NO_OPTION);
				if (rep == JOptionPane.YES_OPTION)
				{
					restart();
				}
				else
				{
					System.exit(0);
				}
			}
		}
	}

	private static void regulation() {

		// Argent
		if (player.getArgent() <= 0)
		{
			window.actualiserEtatArgent(Color.RED);
		}
		else
		{
			window.actualiserEtatArgent(Color.GREEN);
		}

		// Fatigue
		if (player.getFatigue() < 0)
		{
			player.setFatigue(0);
		}
		if (player.getFatigue() >= 50)
		{
			window.actualiserEtatFatigue(Color.YELLOW);
		}
		if (player.getFatigue() >= 70)
		{
			window.actualiserEtatFatigue(Color.ORANGE);
		}
		if (player.getFatigue() >= 90)
		{
			window.actualiserEtatFatigue(Color.RED);
		}

		// Regulation
		if (player.getBonheur() > 100)
		{
			player.setBonheur(100);
		}
		if (player.getFaim() > 100)
		{
			player.setFaim(100);
		}
		if (journee.getTempsLibreJ() < 0)
		{
			journee.setTempsLibreJ(0);
		}
	}

	public static Player getPlayer() {

		return player;
	}

	private static void restart() {

		int filiaireID = -1;
		while (filiaireID == -1)
		{
			FiliaireDialog dialog = new FiliaireDialog(window);
			filiaireID = dialog.showDialog();
		}
		player = new Player(ListeFilieres.getListeFilDebloquees().get(filiaireID), false);
		journee = new Jour();
		window.reinitialiserCalendrier();
		window.mettreMois();
		window.reset();
	}

	public static void saveGame() {

		try
		{
			FileOutputStream file = new FileOutputStream("player.etsim");
			file.write(player.toString().getBytes());
			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static Player loadGame() {

		FileInputStream file;
		String content = "";

		try
		{
			file = new FileInputStream("player.etsim");

			byte[] buffer = new byte[8];

			while (file.read(buffer) >= 0)
			{
				for (byte byt : buffer)
				{
					content += (char) byt;
				}
			}

			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (content != "")
		{
			String[] contentTab = content.split("\n");
			String[] filiareInfo = contentTab[0].split(";");
			return new Player(new Filiere(Integer.parseInt(filiareInfo[0])), Float.parseFloat(contentTab[2]),
					Float.parseFloat(contentTab[3]), Float.parseFloat(contentTab[4]), Float.parseFloat(contentTab[5]),
					Float.parseFloat(contentTab[6]), Integer.parseInt(contentTab[7]), Integer.parseInt(contentTab[8]),
					Integer.parseInt(contentTab[9]), Integer.parseInt(contentTab[10]), Integer.parseInt(contentTab[11]),
					Integer.parseInt(contentTab[12]), Integer.parseInt(contentTab[13]),
					Integer.parseInt(contentTab[14]));
		}
		else
		{
			int filiaireID = -1;
			while (filiaireID == -1)
			{
				FiliaireDialog dialog = new FiliaireDialog(window);
				filiaireID = dialog.showDialog();
			}
			return player = new Player(ListeFilieres.getListeFilDebloquees().get(filiaireID), false);
		}
	}
	
	public static MainWindow getWindow() {
		return window;
	}
}

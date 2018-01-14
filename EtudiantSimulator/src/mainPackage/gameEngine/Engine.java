
package mainPackage.gameEngine;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import mainPackage.gameEngine.event.ModificateurEvent;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;
import mainPackage.gameEngine.jour.Jour;
import mainPackage.gameEngine.objetsMarket.ListeObjets;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.dialog.FiliaireDialog;
import mainPackage.graphicsEngine.window.MainWindow;

public class Engine {

	public static boolean jeuFini = false;

	// Player One
	private static Player player;

	public static Jour journee;

	private static MainWindow window;

	public static void createEngine(boolean newGame, Filiere filiaire) {

		if (newGame) {
			player = new Player(filiaire, false);
			ListeObjets.genererListe();
			journee = new Jour();
		} else {
			loadGame();
		}
		window = new MainWindow();

		window.reinitialiserCalendrier();
		Engine.saveGame();
		gameLoop();
	}

	public static void gameLoop() {

		window.actualiserMagasin();
		while (!jeuFini) {

			verifierFinDeJeu();
			regulation();

			while (!window.isValidated()) {
				regulation();
				window.actualiserBesoins();
			}

			try {
				Thread.sleep(window.getWaitingTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			window.actualiserBesoins();

			journee = new Jour(journee);
			boolean temp = journee.declencherJour(window);
			jeuFini = temp;

			window.mettreJour();
			ListeObjets.refreshListeObjets();
			window.creerContenuJour();
		}
	}

	private static void verifierFinDeJeu() {

		if (player.getFaim() <= 0) {
			ImageIcon icon = new ImageIcon("MortFaim.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"Apr�s avoir battu le record du plus faible poids pour un adulte, vous gagnez un Darwin Award : vous mourrez de faim !\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getFatigue() >= 100) {
			ImageIcon icon = new ImageIcon("MortFatigue.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"La fatigue cr�e des l�sions dans votre cerveau et vous �tes intern� dans un centre m�dical jusqu'� votre euthanasie 25 ans plus tard\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getArgent() < (0 - player.getArgentDepart() - player.getLoyer())) {
			ImageIcon icon = new ImageIcon("MortArgent.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"La banque saisis tous vos bien et vous vous retrouvez dans la rue, seul. Vous mourrez 8 ans plus tard a cause du sida apr�s avoir vendu votre corps\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getBonheur() < 0) {
			ImageIcon icon = new ImageIcon("MortBonheur.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"Vous �tes trop malheureux... *Musique Triste* Le suicide est votre seule option a pr�sent, et vous reussissez\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (journee.getAnnee() - 1 > 0 && journee.getJour() == 1 && journee.getMois() == 1) {
			if (getPlayer().getFiliaire().getDuree() < journee.getAnnee() && getPlayer().checkSavoirTotal()) {
				ImageIcon icon = new ImageIcon("Victoire.png");
				int rep = JOptionPane.showConfirmDialog(window,
						"Et c'est une victoire!!! Bravo, vous avez reussi avec brio votre ann�e scolaire!\n Voulez-vous recommencer une nouvelle partie ?",
						"Oh mon dieu il a gagn�! !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if (rep == JOptionPane.YES_OPTION) {
					restart();
				} else {
					System.exit(0);
				}
			} else if (getPlayer().checkSavoir()) {
				getPlayer().prelassage();
				JOptionPane.showMessageDialog(window,
						"Vous avez r�ussi votre premi�re ann�e de " + getPlayer().getFiliaire().getNom() + " !",
						"GG !", JOptionPane.OK_CANCEL_OPTION);
			} else {
				ImageIcon icon = new ImageIcon("MortSavoir.png");
				int rep = JOptionPane.showConfirmDialog(window,
						"Vous avez rat� votre ann�e, passez moins de temps a faire des b�tises la prochaine fois!\n Voulez-vous recommencer une nouvelle partie ?",
						"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if (rep == JOptionPane.YES_OPTION) {
					restart();
				} else {
					System.exit(0);
				}
			}
		}
	}

	private static void regulation() {

		// Argent
		if (player.getArgent() <= 0) {
			window.actualiserEtatArgent(Color.RED);
		} 
		else if (player.getArgentDepart() > player.getArgent()) {
			window.actualiserEtatArgent(Color.ORANGE);
		}
		else {
			window.actualiserEtatArgent(Color.GREEN);
		}

		// Fatigue
		if (player.getFatigue() >= 90) {
			window.actualiserEtatFatigue(Color.RED);
		}
		else if (player.getFatigue() >= 70) {
			window.actualiserEtatFatigue(Color.ORANGE);
		}
		else if (player.getFatigue() >= 50) {
			window.actualiserEtatFatigue(Color.YELLOW);
		}
		else if (player.getFatigue() < 0) {
			window.actualiserEtatFatigue(Color.BLACK);
			player.setFatigue(0);
		}
		else {
			window.actualiserEtatFatigue(Color.BLACK);
		}

		// Regulation
		if (player.getBonheur() > 100) {
			player.setBonheur(100);
		}
		if (player.getFaim() > 100) {
			player.setFaim(100);
		}
		if (journee.getTempsLibreJ() < 0) {
			journee.setTempsLibreJ(0);
		}
		ModificateurEvent.regulateur();
	}

	public static Player getPlayer() {

		return player;
	}

	private static void restart() {

		int filiaireID = -1;
		while (filiaireID == -1) {
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

		try {
			FileOutputStream file = new FileOutputStream("save.etsim");
			file.write(player.toString().getBytes());
			file.write("\n".getBytes());
			file.write(ListeObjets.staticToString().getBytes());
			file.write("\n".getBytes());
			file.write(journee.toString().getBytes());
			file.write("\n".getBytes());
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadGame() {

		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("save.etsim");

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

		String[] val = content.split("\n");

		Engine.player = new Player(val[0]);
		ListeObjets.genererListe(val[1]);
		Engine.journee = new Jour(val[2]);
	}

	public static MainWindow getWindow() {
		return window;
	}
}

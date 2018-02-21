
package mainPackage.gameEngine;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mainPackage.gameEngine.event.Event;
import mainPackage.gameEngine.event.ListEvent;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;
import mainPackage.gameEngine.jour.Jour;
import mainPackage.gameEngine.objetsMarket.ListeObjets;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.dialog.EventDialog;
import mainPackage.graphicsEngine.dialog.FiliaireDialog;
import mainPackage.graphicsEngine.window.MainWindow;

public class Engine {

	public static boolean jeuFini = false;

	// Player One
	private static Player player;

	public static Jour journee;

	private static MainWindow window;

	public static boolean eventFini = false;

	public static EventDialog eventDialog;

	public static void createEngine(boolean newGame, Filiere filiaire) {

		if (newGame) {
			player = new Player(filiaire, false);
			try {
				ListeObjets.genererListe();
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			journee = new Jour();
		} else {
			loadGame();
		}

		if (window != null) {
			window.resetPanel();
			Engine.reset(filiaire);
		} else {
			window = new MainWindow();
			window.activer();
		}

		window.reinitialiserCalendrier();
		Engine.saveGame();

		try {
			ListEvent.mettreListEvent();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		gameLoop();
	}

	public static void gameLoop() {

		window.actualiserMagasin();
		while (!jeuFini) {

			journee = new Jour(journee);

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

			jeuFini = journee.declencherJour();

			Engine.faireEvent();

			window.mettreJour();
			
			ListeObjets.refreshListeObjets();
			ListeObjets.appliquerUpgrade();
			
			window.creerContenuJour();
		}
	}

	private static void verifierFinDeJeu() {

		if (player.getFaim() <= 0) {
			Engine.deleteSave();
			ImageIcon icon = new ImageIcon("fins\\MortFaim.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"Après avoir battu le record du plus faible poids pour un adulte, vous gagnez un Darwin Award : vous mourrez de faim !\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getFatigue() >= 100) {
			Engine.deleteSave();
			ImageIcon icon = new ImageIcon("fins\\MortFatigue.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"La fatigue crée des lésions dans votre cerveau et vous êtes interné dans un centre médical jusqu'à votre euthanasie 25 ans plus tard\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getArgent() < (0 - player.getArgentDepart() - player.getLoyer())) {
			Engine.deleteSave();
			ImageIcon icon = new ImageIcon("fins\\MortArgent.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"La banque saisis tous vos bien et vous vous retrouvez dans la rue, seul. Vous mourrez 8 ans plus tard a cause du sida après avoir vendu votre corps\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (player.getBonheur() < 0) {
			Engine.deleteSave();
			ImageIcon icon = new ImageIcon("fins\\MortBonheur.png");
			int rep = JOptionPane.showConfirmDialog(window,
					"Vous êtes trop malheureux... *Musique Triste* Le suicide est votre seule option a présent, et vous reussissez\n Voulez-vous recommencer une nouvelle partie ?",
					"Perdu !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
			if (rep == JOptionPane.YES_OPTION) {
				restart();
			} else {
				System.exit(0);
			}
		} else if (journee.getAnnee() - 1 > 0 && journee.getJour() == 1 && journee.getMois() == 1) {
			if (getPlayer().getFiliaire().getDuree() < journee.getAnnee() && getPlayer().checkSavoirTotal()) {
				ImageIcon icon = new ImageIcon("fins\\Victoire.png");
				int rep = JOptionPane.showConfirmDialog(window,
						"Et c'est une victoire!!! Bravo, vous avez reussi avec brio votre année scolaire!\n Voulez-vous recommencer une nouvelle partie ?",
						"Oh mon dieu il a gagné! !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if (rep == JOptionPane.YES_OPTION) {
					restart();
				} else {
					System.exit(0);
				}
			} else if (getPlayer().checkSavoir()) {
				getPlayer().prelassage();
				JOptionPane
						.showMessageDialog(window,
								"Vous avez réussi votre " + (Engine.journee.getAnnee() - 1) + "º année de "
										+ getPlayer().getFiliaire().getNom() + " !",
								"GG !", JOptionPane.OK_CANCEL_OPTION);
			} else {
				Engine.deleteSave();
				ImageIcon icon = new ImageIcon("fins\\MortSavoir.png");
				int rep = JOptionPane.showConfirmDialog(window,
						"Vous avez raté votre année, passez moins de temps a faire des bétises la prochaine fois!\n Voulez-vous recommencer une nouvelle partie ?",
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
		} else if (player.getArgentDepart() > player.getArgent()) {
			window.actualiserEtatArgent(Color.ORANGE);
		} else {
			window.actualiserEtatArgent(Color.GREEN);
		}

		// Fatigue
		if (player.getFatigue() >= 90) {
			window.actualiserEtatFatigue(Color.RED);
		} else if (player.getFatigue() >= 70) {
			window.actualiserEtatFatigue(Color.ORANGE);
		} else if (player.getFatigue() >= 50) {
			window.actualiserEtatFatigue(Color.YELLOW);
		} else if (player.getFatigue() < 0) {
			window.actualiserEtatFatigue(Color.BLACK);
			player.setFatigue(0);
		} else {
			window.actualiserEtatFatigue(Color.BLACK);
		}

		// Regulation
		if (player.getBonheur() > 100) {
			player.setBonheur(100);
		}
		if (player.getFaim() > 100) {
			player.setFaim(100);
		}
		if (journee.getTempsLibre() < 0) {
			journee.setTempsLibre(0);
		}
		
		ListEvent.regulateur();
	}

	public static Player getPlayer() {

		return player;
	}

	private static void faireEvent() {
		ListEvent.createTampon();
		Event choisi = ListEvent.choisisEvent();
		
		if (choisi != null) {
			ListEvent.afficherEvent(choisi);
		} else {
			eventFini = true;
		}

		while (!eventFini) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		eventFini = false;
		window.actualiserBesoins();
	}

	private static void restart() {

		int filiaireID = -1;
		while (filiaireID == -1) {
			FiliaireDialog dialog = new FiliaireDialog(window);
			filiaireID = dialog.showDialog();
		}

		Engine.createEngine(true, ListeFilieres.getListeFilDebloquees().get(filiaireID));
	}

	public static void reset(Filiere filiaire) {
		player = new Player(filiaire, false);
		journee = new Jour();
		ListeObjets.resetListe();
		window.reinitialiserCalendrier();
		window.mettreMois();
		window.reset();
	}

	private static void deleteSave() {
		try {
			FileOutputStream file = new FileOutputStream("saves\\save.etsim");
			file.write("".getBytes());
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveGame() {

		try {
			FileOutputStream file = new FileOutputStream("saves\\save.etsim");
			file.write(player.toString().getBytes());
			file.write("\n".getBytes());
			file.write(journee.toString().getBytes());
			file.write("\n".getBytes());
			file.write(ListeObjets.staticToString().getBytes());
			file.write("\n".getBytes());
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String parseXML(String string) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(string));
		
		String texte = new String(encoded);
		texte = texte.replaceAll("(\\r|\\n)", "");
		texte = texte.replaceAll(">(\\s*+)<", "><");
		
		FileOutputStream file = new FileOutputStream("listes\\temp.xml");
		file.write(texte.getBytes());
		file.close();
		
		return "listes\\temp.xml";
	}

	private static void loadGame() {

		FileInputStream file;
		String content = "";

		try {
			file = new FileInputStream("saves\\save.etsim");

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
		Engine.journee = new Jour(val[1]);
		ListeObjets.genererListe(val[2]);
	}

	public static MainWindow getWindow() {
		return window;
	}
}

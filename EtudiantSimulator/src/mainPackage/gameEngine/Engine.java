
package mainPackage.gameEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import mainPackage.gameEngine.event.Event;
import mainPackage.gameEngine.event.ListEvent;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;
import mainPackage.gameEngine.jour.Jour;
import mainPackage.gameEngine.objetsMarket.ListeObjets;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.state.FinalState;
public class Engine {

	public static boolean jeuFini = false;

	// Player One
	private static Player player ;

	public static Jour journee = new Jour();

	public static boolean eventFini = false;
	
	public static boolean isGameOver = false;
	
	public static float modifierArgent;
	public static float modifierSavoir;
	public static float modifierNourriture;
	public static float modifierSommeil;
	public static float modifierBonheur;
	
	public static boolean faireAfficherEvent;
	public static Event event;
	
	public static void createEngine(boolean newGame, Filiere filiaire) throws SAXException, IOException, ParserConfigurationException {

		if (newGame) {
			player = new Player(filiaire, false);
			ListeObjets.genererListe();
		} else {
			loadGame();
		}

		Engine.saveGame();

		try {
			ListEvent.mettreListEvent();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		verifierFinDeJeu();
		regulation();
		journee = new Jour(Engine.journee);
	}

	public static void gameLoop() {
			verifierFinDeJeu();
			regulation();

			faireEvent();
			jeuFini = journee.declencherJour();
			
			ListeObjets.refreshListeObjets();
			ListeObjets.appliquerUpgrade();
			journee = new Jour(journee);
	}

	
	public static void verifierFinDeJeu() {

		if (player.getFaim() <= 0) {
			FinalState.finJeu("MortFaim","haha",true);
		} else if (player.getFatigue() >= 100) {
			FinalState.finJeu("MortFatigue","haha le noob de merde",true);
		} else if (player.getArgent() < (0 - player.getArgentDepart() - player.getLoyer())) { 
			FinalState.finJeu("MortArgent","haha",true);
		} else if (player.getBonheur() < 0) {
			FinalState.finJeu("MortBonheur","haha",true);
		} else if (journee.getAnnee() - 1 > 0 && journee.getJour() == 1 && journee.getMois() == 1) {
			if (getPlayer().getFiliaire().getDuree() < journee.getAnnee() && getPlayer().checkSavoirTotal()) {
				FinalState.finJeu("Victoire","Vous avez gagnez",true);
				}
			 else if (getPlayer().checkSavoir()) {
				getPlayer().prelassage();
				if(isCrousAttraper())
				{
					int payer= crousPunition();
					Engine.getPlayer().setArgent(Engine.getPlayer().getArgent() - payer);
					FinalState.finJeu("Victoire","GG on maintiens le cap.\nLe crous vous fait rembourser: " + payer ,false);
				}else {
					FinalState.finJeu("Victoire","GG on maintiens le cap" ,false);
				}
			} else {
				FinalState.finJeu("MortSavoir","RekNub",true);
			}
		}
	}
	
	static private int crousPunition()
	{
		int somme=0;
		somme = (int) (Math.random() * ( Jour.listeJoursSecher.size() - 1 )) * 11;
		return somme;
	}
	
	private static boolean isCrousAttraper()
	{
		int rand;
		rand = (int) (Math.random() * ( 120 - 10 ));
		if(Jour.listeJoursSecher.size()>rand)
		{
			return true;
		}
		return false;
	}
	
	public static void regulation() {

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
		if (player.getFatigue() < 0) {
			player.setFatigue(0) ;
		}
		
		ListEvent.regulateur();
	}

	public static Player getPlayer() {

		return player;
	}

	public static void faireEvent() {
		ListEvent.createTampon();
		Event choisi = ListEvent.choisisEvent();
		
		if (choisi != null) {
			Engine.event=choisi;
			Engine.faireAfficherEvent=true;
		} else {
			eventFini = true;
		}
	}

	private static void restart() throws SAXException, IOException, ParserConfigurationException {

		int filiaireID = -1;
		while (filiaireID == -1) {
			/*FiliaireDialog dialog = new FiliaireDialog(window);
			TODO Indiquer a slick de restart
			filiaireID = dialog.showDialog();
			*/
		}

		Engine.createEngine(true, ListeFilieres.getListeFilDebloquees().get(filiaireID));
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
}

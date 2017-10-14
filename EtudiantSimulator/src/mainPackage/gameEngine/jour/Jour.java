
package mainPackage.gameEngine.jour;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

public class Jour {
	
	private Date date;

	private int tempsLibreJ;

	private static Date[] listeJoursFeries;

	public Jour() {
		this.date = new Date();
		genererJoursFeries();
	}

	public Jour(Jour jourPrecedent) {
		//Chaque année, de nouveaux jours feriés!
		if (jourPrecedent.date.getAnnee() < jourPrecedent.date.dateJourSuivant().getAnnee()) {
			genererJoursFeries();
		}
		this.date = jourPrecedent.date.dateJourSuivant();

	}
	
	public Jour(String save) {
		String[] val = save.split(";");
		String[] val2 = val[1].split(",");
		
		this.date = new Date(val[0]);
		
		this.genererJoursFeries();
		for (int i = 0; i < val2.length; i++) {
			Jour.listeJoursFeries[i] = new Date(val2[i]);
		}
	}

	public String toString() {
		String r = this.date.toString() + ";";
		
		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			r += Jour.listeJoursFeries[i].toString();
					
			if (i < Jour.listeJoursFeries.length - 1) {
				r += ",";
			}
		}
		return r + ";";
	}
	
	private boolean isJourFerie() {

		boolean r = false;

		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			if (Jour.listeJoursFeries[i].equals(this.date)) {
				r = true;
			}
		}

		return r;
	}

	private void genererJoursFeries() {
		Jour.listeJoursFeries = new Date[11];
		Random rand = new Random();
		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			Jour.listeJoursFeries[i] = new Date(rand.nextInt(28) + 1, rand.nextInt(10) + 1, this.date.getAnnee());
			
		}
	}

	private boolean isJourVacances() {

		return (this.getJour() < 15 && (this.getMois() == 5 || this.getMois() == 7))
				|| (this.getJour() > 14 && (this.getMois() == 2 || this.getMois() == 9));
	}

	public boolean declencherJour(MainWindow mainWindow) {

		if (this.date.getJour() % 14 == 1) {
			mainWindow.mettreIcone("calendar.gif");
		}
		else if (this.getJour() % 14 == 0) {
			Engine.saveGame();
			mainWindow.mettreIcone("checklist.gif");
			mainWindow.activerButtonSuivant();
			mainWindow.setValider(false);
			mainWindow.actualiserMagasin();
		}

		if (this.getJour() == 28) {
			Engine.getPlayer().paimentEtGainsMois();
		}

		return false;
	}

	public int getMois() {

		return this.date.getMois();
	}

	public int getJour() {

		return this.date.getJour();
	}

	public int getTempsLibreJ() {

		return this.tempsLibreJ;
	}

	public void setTempsLibreJ(int i) {

		this.tempsLibreJ = i;
	}

	public int getAnnee() {

		return this.date.getAnnee();
	}

	public void setTempsLibreJ(Player player, float i) {

		this.tempsLibreJ = 60
				* (24 - (this.getTempsDodo(player, i) + this.getTempsTransport(player) + this.getTempsTravail(player)));
	}

	public int getTempsTravail(Player player) {

		if (this.isJourFerie() || this.isJourVacances()) {
			return 0;
		}
		else {
			return player.getFiliaire().getTempsTravail((this.getJour() - 1) % 7);
		}
	}

	public int getTempsTransport(Player player) {

		if (this.getTempsTravail(player) == 0) {
			return 0;
		}
		else {
			return 2;
		}
	}

	public int getTempsDodo(Player player, float i) {

		if (i <= 50) {
			return (int) (8 * (i / 50f));
		}
		else {
			return (int) (((i * 2) - 50) / 100)
					* (24 - (this.getTempsTransport(player) + this.getTempsTravail(player) + 8));
		}
	}

	public int getMoisQuiArrive() {

		int i;
		if (this.getJour() == 28) {
			if (this.getMois() == 10) {
				i = 1;
			}
			else {
				i = this.getMois() + 1;
			}
		}
		else {
			i = this.getMois();
		}

		return i;
	}

	public Date getDate() {

		return this.date;
	}
}

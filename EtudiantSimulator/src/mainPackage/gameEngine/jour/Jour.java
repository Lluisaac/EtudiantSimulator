
package mainPackage.gameEngine.jour;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

public class Jour {
	
	private Date date;

	private int tempsLibreJ;

	private static int[][] listeJoursFeries;

	public Jour() {
		this.date = new Date();
		genererJoursFeries();
	}

	public Jour(Jour jourPrecedent) {
		// On conserve la liste des jours feries
		this.date = jourPrecedent.date.dateJourSuivant();
	}

	private boolean isJourFerie() {

		boolean r = false;

		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			if (Jour.listeJoursFeries[i][0] == this.date.getJour() && Jour.listeJoursFeries[i][1] == this.date.getMois()) {
				r = true;
			}
		}

		return r;
	}

	private void genererJoursFeries() {

		Jour.listeJoursFeries = new int[11][2];
		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			Random rand = new Random();
			Jour.listeJoursFeries[i][0] = rand.nextInt(28) + 1;
			Jour.listeJoursFeries[i][1] = rand.nextInt(12) + 1;
		}
	}

	private boolean isJourVacances() {

		return (this.getJour() < 15 && (this.getMois() == 5 || this.getMois() == 7))
				|| (this.getJour() > 14 && (this.getMois() == 2 || this.getMois() == 9));
	}

	public boolean declencherJour(MainWindow mainWindow) {

		if (this.date.getJour() % 14 == 1) {
			mainWindow.mettreIcone("calendar.gif");
			Engine.saveGame();
		}
		else if (this.getJour() % 14 == 0) {
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

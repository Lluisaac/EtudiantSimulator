
package mainPackage.gameEngine.jour;

import java.util.Random;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.player.Player;
import mainPackage.graphicsEngine.window.MainWindow;

public class Jour {

	private Date date;

	private int tempsLibre; // Temps libre d'aujourd'hui

	private int tempsLibreJ; // Temps libre a modifier pour chaque jour

	private int buffer;

	private static Date[] listeJoursFeries;

	public Jour() {
		this.date = new Date();
		this.tempsLibre = 0;
		this.tempsLibreJ = 0;
		this.buffer = 0;
		genererJoursFeries();
	}

	public Jour(Jour jourPrecedent) {
		// Chaque annee, de nouveaux jours feries!
		this.date = jourPrecedent.date.dateJourSuivant();
		if (jourPrecedent.date.getAnnee() < jourPrecedent.date.dateJourSuivant().getAnnee()) {
			genererJoursFeries();
		}
		this.buffer = jourPrecedent.buffer;
		this.tempsLibreJ = jourPrecedent.tempsLibreJ;

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

	public static void addJourFerie(Date date) {
		Date[] temp = new Date[Jour.listeJoursFeries.length + 1];
		for (int i = 0; i < Jour.listeJoursFeries.length; i++) {
			temp[i] = Jour.listeJoursFeries[i];
		}
		temp[Jour.listeJoursFeries.length] = date;
	}

	private boolean isJourVacances() {

		return (this.getJour() < 15 && (this.getMois() == 5 || this.getMois() == 7))
				|| (this.getJour() > 14 && (this.getMois() == 2 || this.getMois() == 9));
	}

	public boolean declencherJour() {

		if (this.date.getJour() % 14 == 1) {
			Engine.getWindow().mettreIcone("misc\\calendar.gif");
		} else if (this.getJour() % 14 == 0) {
			Engine.getWindow().mettreIcone("misc\\checklist.gif");
			Engine.getWindow().activerButtonSuivant();
			Engine.getWindow().setValider(false);
			Engine.getWindow().actualiserMagasin();
			Engine.saveGame();
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

	public int getTempsLibre() {

		return this.tempsLibre;
	}

	public void setTempsLibre(int i) {

		this.tempsLibre = i;

		if (this.tempsLibre < 0) {
			this.setBuffer(this.tempsLibre);
			this.tempsLibre = 0;
		}

	}

	public int getAnnee() {

		return this.date.getAnnee();
	}

	public void setTempsLibre(Player player, float i) {

		this.tempsLibre = 60
				* (24 - (this.getTempsDodo(player, i) + this.getTempsTransport(player) + this.getTempsTravail(player)))
				+ this.buffer + this.tempsLibre;
	}

	public int getTempsTravail(Player player) {

		if (this.isJourFerie() || this.isJourVacances()) {
			return 0;
		} else {
			return player.getFiliaire().getTempsTravail((this.getJour() - 1) % 7);
		}
	}

	public int getTempsTransport(Player player) {

		if (this.getTempsTravail(player) == 0) {
			return 0;
		} else {
			return 2;
		}
	}

	public int getTempsDodo(Player player, float i) {

		if (i <= 50) {
			return (int) (8 * (i / 50f));
		} else {
			return (int) (((i * 2) - 50) / 100)
					* (24 - (this.getTempsTransport(player) + this.getTempsTravail(player) + 8));
		}
	}

	public int getMoisQuiArrive() {

		int i;
		if (this.getJour() == 28) {
			if (this.getMois() == 10) {
				i = 1;
			} else {
				i = this.getMois() + 1;
			}
		} else {
			i = this.getMois();
		}

		return i;
	}

	public Date getDate() {

		return this.date;
	}

	public int getBuffer() {
		return buffer;
	}

	public void setBuffer(int buffer) {
		this.buffer = buffer;
	}

	public void setTempsLibreJ(int i) {
		this.tempsLibreJ = i;

	}

	public float getTempsLibreJ() {
		return this.tempsLibreJ;
	}
}

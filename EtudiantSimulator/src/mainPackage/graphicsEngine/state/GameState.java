package mainPackage.graphicsEngine.state;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Date;
import mainPackage.gameEngine.jour.Jour;
import mainPackage.gameEngine.objetsMarket.ListeObjets;

public class GameState extends BasicGameState {
	// ID
	public static final int ID = 1;

	private GameContainer container;
	private StateBasedGame game;

	private Image background;
	private Image boutonVert;
	private Image boutonRouge;
	private Image flecheGauche;
	private Image flecheDroite;
	
	private Image boutonEvent;

	private Image[] clickables;
	private ArrayList<Image> objectsMarket = new ArrayList<Image>();
	private int[][] coordinatesObjectClickable;
	private ArrayList<Image> archetypes;

	private Image[] popups;
	private int xPopup, yPopup;
	private boolean showingPopup = false;
	private int popupId;
	private int pageMarket = 1;

	private Image cursor;
	private int xSlider, ySlider, widthSlider, heightSlider;
	private float xCursor, yCursor;
	private int sliderAgenda=1;

	private float devoirsP = 0;
	private float nourritureP = 0;
	private float sommeilP = 0;
	private float argentP = 0;
	private float bonheurP = 0;
	private double speedP = 0.14;

	private float nourritureC = 6;
	private float sommeilC = 6;
	private float devoirsC = 6;
	private float speedC = 0;

	boolean isValidate = false;
	boolean finSemaine = true;
	private int jourActuel = 1;
	float[] valeurSemainePrecedente;
	String[] mois = { "Septembre", "Octobre", "Novembre", "Decembre", "Janvier", "Fevrier", "Mars", "Avril", "Mai",
			"Juin", "Juillet", "Aout" };

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game;
		this.valeurSemainePrecedente = new float[14 * 6];
		this.valeurSemainePrecedente = new float[16 * 5];

		this.container = container;

		this.boutonEvent = new Image("assets/popup/boutonEvent.png");

		this.background = new Image("assets/image/game.png");
		this.boutonVert = new Image("assets/ImageObjets/boutonVert.png");
		this.boutonRouge = new Image("assets/ImageObjets/boutonRouge.png");
		this.flecheGauche = new Image("assets/popup/flecheGauche.png");
		this.flecheDroite = new Image("assets/popup/flecheDroite.png");
		this.coordinatesObjectClickable = new int[7][2];
		this.clickables = new Image[7];
		this.archetypes = new ArrayList<Image>();

		this.clickables[0] = new Image("assets/elementClickable/diary.png");
		int[] diaryCoord = { 1165, 272 };
		this.coordinatesObjectClickable[0] = diaryCoord;

		this.clickables[1] = new Image("assets/elementClickable/bookshelf.png");
		int[] bookShelfCoord = { 845, 366 };
		this.coordinatesObjectClickable[1] = bookShelfCoord;

		this.clickables[2] = new Image("assets/elementClickable/fridge.png");
		int[] fridgeCoord = { 470, 212 };
		this.coordinatesObjectClickable[2] = fridgeCoord;

		this.clickables[3] = new Image("assets/elementClickable/bed.png");
		int[] bedCoord = { 1516, 631 };
		this.coordinatesObjectClickable[3] = bedCoord;

		this.clickables[4] = new Image("assets/elementClickable/book.png");
		int[] bookCoord = { 1420, 674 };
		this.coordinatesObjectClickable[4] = bookCoord;

		this.clickables[5] = new Image("assets/elementClickable/clock.png");
		int[] clockCoord = { 713, 265 };
		this.coordinatesObjectClickable[5] = clockCoord;

		this.clickables[6] = new Image("assets/elementClickable/magazine.png");
		int[] magazineCoord = { 462, 539 };
		this.coordinatesObjectClickable[6] = magazineCoord;

		this.popups = new Image[9];

		this.popups[0] = new Image("assets/popup/timer.png");
		this.popups[1] = new Image("assets/popup/devoir.png");
		this.popups[2] = new Image("assets/popup/nourriture.png");
		this.popups[3] = new Image("assets/popup/sommeil.png");
		this.popups[4] = new Image("assets/popup/diary.png");
		this.popups[5] = new Image("assets/popup/vitesse.png");
		this.popups[6] = new Image("assets/popup/magasin.png");
		this.popups[7] = new Image("assets/popup/eventPopup.png");
		this.popups[8] = new Image("assets/popup/calendar.png");

		this.xPopup = (container.getWidth() / 2) - (this.popups[1].getWidth() / 2);
		this.yPopup = (container.getHeight() / 2) - (this.popups[1].getHeight() / 2);

		this.cursor = new Image("assets/popup/cursor.png");
		this.xSlider = this.xPopup + 221;
		this.ySlider = this.yPopup + 316;
		this.widthSlider = 390;
		this.heightSlider = 12;

		this.xCursor = this.xSlider;
		this.yCursor = this.ySlider - (this.cursor.getHeight() / 2) + (this.heightSlider / 2);
		placeObjectInArray();
		placeArchetypesInArray();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawImage(this.background, 0, 0);

		for (int i = 0; i < this.clickables.length; i++) {
			g.drawImage(this.clickables[i], this.coordinatesObjectClickable[i][0],
					this.coordinatesObjectClickable[i][1]);
		}

		if (this.showingPopup) {
			g.drawImage(this.popups[this.popupId], this.xPopup, this.yPopup);

			if (isSliderPopup()) {
				g.drawImage(this.cursor, this.xCursor, this.yCursor);
			}
			if (this.popupId == 6) {
				placeObjectInMarket(g);
			}
			if (this.popupId == 0) {
				if (Engine.faireAfficherEvent) {
					afficherEvent(g);
				} else if (this.isValidate) {
					if (!finSemaine) {
						percent();
						Engine.modifierSommeil = Engine.getPlayer().modifierFatigue(this.sommeilP);
						Engine.modifierArgent = Engine.getPlayer().modifierArgent(this.argentP);
						Engine.modifierNourriture = Engine.getPlayer().modifierFaim(this.nourritureP);
						Engine.modifierBonheur = Engine.getPlayer().modifierBonheur(this.bonheurP);
						Engine.modifierSavoir = Engine.getPlayer().modifierSavoir(this.devoirsP);
						remplirValeurSemainePrecedente(Engine.modifierSommeil, Engine.modifierArgent,
								Engine.modifierNourriture, Engine.modifierBonheur, Engine.modifierSavoir);
						afficherContenuJour(g);
						Engine.gameLoop();
						if (Engine.journee.getJour() % 14 == 1) {
							finSemaine = true;
						}
					} else {
						afficherContenuJour(g);
					}
				}
				afficherMois(g);
			}
			if (this.popupId == 4) {
				if(this.sliderAgenda==1)
				{
					afficherInfo(g);
				}else {
					afficherCalendar(g);
				}
				this.flecheGauche.draw(350 + this.xPopup - 2 - this.flecheGauche.getWidth() / 2,30 + this.popups[4].getHeight() + this.yPopup);
				this.flecheDroite.draw(350 + this.xPopup + 2 + this.flecheDroite.getWidth() / 2,30 + this.popups[4].getHeight() + this.yPopup);
			}
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (Engine.isGameOver) {
			this.game.enterState(3);
		}
		percent();
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE) {
			Option.stateBefore=this.getID();
			this.game.enterState(4);
		}
	}

	private void placeArchetypesInArray() throws SlickException {
		File[] f = new File("assets/archetypes").listFiles();
		int r = 0;
		for (int i = 0; i < f.length; i++) {
			if (f[i].getName().matches(".*png")) {
				String name = f[i].getName();
				this.archetypes.add(new Image("assets/archetypes/" + name));
				name = name.substring(0, name.length() - 4);
				this.archetypes.get(r).setName(name);
				r += 1;
			}
		}
	}

	public float arrondit(float x) {
		int y;
		y = (((int) (x * 100))) / 100;
		return y;
	}

	void couleurInfo(Graphics g, float x, float min, float max) {
		if (x < min) {
			g.setColor(Color.red);
		} else if (x < max) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.blue);
		}
	}

	private void afficherInfo(Graphics g) {
		g.setColor(Color.black);
		int y = this.yPopup + 60;
		int x = this.xPopup + 50;
		g.drawString("Argent actuel: " + arrondit(Engine.getPlayer().getArgent()), x, y);
		g.drawString("Gain d'argent: " + arrondit(Engine.getPlayer().getGainParMois()), x + 400, y);
		g.drawString("Loyer: " + arrondit(Engine.getPlayer().getLoyer()), x + 400, y + 50);
		g.drawString("Argent de depart: " + arrondit(Engine.getPlayer().getArgentDepart()), x + 400, y + 150);
		g.drawString("Savoir obtenu: " + arrondit(Engine.getPlayer().getSavoir()), x + 400, y + 250);
		if (Engine.getPlayer().getArgent() - Engine.getPlayer().getArgentDepart() > 0) {
			g.setColor(Color.blue);
		} else if (Engine.getPlayer().getArgent() - Engine.getPlayer().getArgentDepart() < 0) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.black);
		}
		g.drawString(
				"Argent obtenu: " + arrondit((Engine.getPlayer().getArgent() - Engine.getPlayer().getArgentDepart())),
				x + 400, y + 300);
		couleurInfo(g, Engine.getPlayer().getFaim(), 0, 100);
		g.drawString("nourriture actuel: " + arrondit(Engine.getPlayer().getFaim()), x, y + 50);
		couleurInfo(g, Engine.getPlayer().getFaim(), 0, 100);
		g.drawString("bonheur actuel: " + arrondit(Engine.getPlayer().getBonheur()), x, y + 100);
		couleurInfo(g, map(Engine.getPlayer().getFatigue(), 0, 100, 100, 0), 100, 0);
		g.drawString("fatigue actuel: " + arrondit(Engine.getPlayer().getFatigue()), x, y + 150);
	}

	private void afficherCalendar(Graphics g) {
		Date date = new Date();
		g.drawImage(this.popups[8], this.xPopup, this.yPopup);
		int x=this.xPopup + 14;
		int y=this.yPopup + 64;
		for(int m=1;m<=12;m++)
		{
			for(int j=1;j<=28;j++)
			{
				date.setJour(j);
				date.setMois(m);
				date.setAnnee(Engine.journee.getAnnee());
				if(Jour.isJourEcole(date))
				{
					g.setColor(Color.green);
				}else {
					g.setColor(Color.blue);
				}
				if(j%7==1 &&  j!=1)
				{
					x=this.xPopup + 14 + ((m-1)%4)*203;;
					y+=25;
				}
				g.drawRect(x, y, 21, 22);
				System.out.println("x:" + x + " y: " + y + " date: " + date.getDate());
				x+=25;
			}
			y=this.yPopup + 64 +(m/4)*219;
			x=this.xPopup + 14 + ((m)%4)*203;
		}
	}
	
	private void afficherMois(Graphics g) {
		int month = 10;
		int year = 1;
		int semaine = 1;
		int x = 0;
		int y = 0;
		Date date = new Date("1/1/1");

		year = Engine.journee.getAnnee();
		if (Engine.journee.getJour() == 1) {
			month = ((Engine.journee.getMois() - 2) + 10) % 10;
		} else {
			month = Engine.journee.getMois() - 1;

			semaine = (Engine.journee.getJour() - 2) / 7 + 1;
		}
		if (this.mois[month].equals("Juin") && semaine == 2) {
			year--;
		}
		if (Engine.journee.getJour() == 1 && Engine.journee.getMois() != 1) {
			semaine = 4;
		}
		if (!this.isValidate) {
			semaine = (semaine + 1) % 4;
			if (semaine == 1) {
				month = Engine.journee.getMois() - 1;
			}
		}
		if (Engine.journee.getDate().toString().equals("1/1/1")) {
			month = 0;
			semaine = 1;
			year = 1;
		}
		String str = this.mois[month] + " " + "semaine: " + semaine + " " + "année:" + year;
		g.setColor(Color.black);
		g.drawString(str, this.container.getWidth() / 2 - 100, this.yPopup);

		if (!Engine.faireAfficherEvent) {
			for (int i = 1; i <= 14; i++) {
				if (semaine == 3 || semaine == 4) {
					date.setJour(map(i, 1, 14, 15, 28));
					date.setMois(month + 1);
					date.setAnnee(year);
				} else {
					date.setJour(i);
					date.setMois(month + 1);
					date.setAnnee(year);
				}

				if ((i > 7 && i <= 14) || (i > 21 && i <= 28)) {
					x = this.xPopup + 16 + (i - 8) * 110;
					y = this.yPopup + 88 + 215;
				} else {
					x = this.xPopup + 16 + (i - 1) * 110;
					y = this.yPopup + 88;
				}
				if (Jour.isJourEcole(date)) {
					g.setColor(Color.green);
					g.drawRect(x, y, 107, 214);
				} else {
					g.setColor(Color.blue);
					g.drawRect(x, y, 107, 214);
				}
			}
		}

	}

	public void remplirValeurSemainePrecedente(float fatigue, float argent, float faim, float bonheur, float savoir) {
		this.valeurSemainePrecedente[((Engine.journee.getJour() - 1) % 14 * 5)] = fatigue;
		this.valeurSemainePrecedente[((Engine.journee.getJour() - 1) % 14 * 5) + 1] = argent;
		this.valeurSemainePrecedente[((Engine.journee.getJour() - 1) % 14 * 5) + 2] = faim;
		this.valeurSemainePrecedente[((Engine.journee.getJour() - 1) % 14 * 5) + 3] = bonheur;
		this.valeurSemainePrecedente[((Engine.journee.getJour() - 1) % 14 * 5) + 4] = savoir;
	}

	public void placeObjectInArray() throws SlickException {
		File[] f = new File("assets/ImageObjets").listFiles();
		int r = 0;
		for (int i = 0; i < f.length; i++) {
			if (f[i].getName().matches(".*png") && !f[i].getName().equals("boutonRouge.png")
					&& !f[i].getName().equals("boutonVert.png")) {
				String name = f[i].getName();
				this.objectsMarket.add(new Image("assets/ImageObjets/" + name));
				name = name.substring(0, name.length() - 4);
				this.objectsMarket.get(r).setName(name);
				r += 1;
			}
		}
	}

	public void placeObjectInMarket(Graphics g) {
		int objectsToPlace = objectsMarket.size();
		int x = this.xSlider - 150;
		int y = this.ySlider - 200;
		this.flecheGauche.draw(350 + this.xPopup - 2 - this.flecheGauche.getWidth() / 2, 550 + this.yPopup);
		this.flecheDroite.draw(350 + this.xPopup + 2 + this.flecheDroite.getWidth() / 2, 550 + this.yPopup);

		for (int i = (this.pageMarket - 1) * 10; i < (this.pageMarket) * 10 && i < objectsToPlace; i++) {
			if (ListeObjets.trouveObjet(objectsMarket.get(i).getName()).isDebloque()) {
				this.boutonVert.draw(x - 45, y - 30);
			} else {
				this.boutonRouge.draw(x - 45, y - 30);
			}
			g.setColor(Color.black);
			g.drawString(this.objectsMarket.get(i).getName(), x + 15 - this.objectsMarket.get(i).getName().length() * 3,
					y + 70);
			this.objectsMarket.get(i).draw(x, y);
			x += 150;
			if ((i + 1) % 5 == 0 && i != 0) {
				y += 200;
				x = this.xSlider - 150;
			}
		}
	}

	public void clicOnObjectMarket(int x, int y) {
		int a = this.xSlider - 150 - 45;
		int b = this.ySlider - 200 - 30;

		for (int i = (this.pageMarket - 1) * 10; i < (this.pageMarket) * 10 && i < objectsMarket.size(); i++) {
			if (x > a && x < a + 150 && y > b && y < b + 150) {
				ListeObjets.trouveObjet(objectsMarket.get(i).getName()).acheter();
			}
			a += 150;
			if ((i + 1) % 5 == 0 && i != 0) {
				b += 200;
				a = this.xSlider - 150 - 45;
			}
		}
	}

	int map(float x, float in_min, float in_max, float out_min, float out_max) {
		return (int) ((x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
	}

	public void percent() // methode qui lie slick a engine
	{
		this.devoirsP = map(this.devoirsC, 0, 12, 0, 100);
		this.bonheurP = map(this.devoirsC, 0, 12, 0, 100);

		this.nourritureP = map(this.nourritureC, 0, 12, 0, 100);
		this.argentP = map(this.nourritureC, 0, 12, 0, 100);

		this.sommeilP = map(this.sommeilC, 0, 12, 0, 100);
		this.speedP = map(this.speedC, 0, 12, 50, 5000);
	}

	public void afficherContenuJour(Graphics g) {
		int x = 0;
		int y = 0;
		g.setColor(Color.black);
		for (int i = 0; i <= (this.jourActuel - 1) % 14; i++) {
			x = this.xPopup + 20 + (i % 7) * 110;
			y = this.yPopup + (i / 7) * 220 + 95;
			g.drawString("sommeil", x, y );
			g.drawString(":" + this.valeurSemainePrecedente[i * 5], x, y + 15);
			g.drawString("nourriture", x, y + 35);
			g.drawString(":" + this.valeurSemainePrecedente[i * 5 + 2], x, y + 50);
			g.drawString("devoirs", x, y + 70);
			g.drawString(":" + this.valeurSemainePrecedente[i * 5 + 4], x, y + 85);
			g.drawString("bonheur", x, y + 105);
			g.drawString(":" + this.valeurSemainePrecedente[i * 5 + 3], x, y + 120);
			g.drawString("argent", x, y + 140);
			g.drawString(":" + (float) ((int) (this.valeurSemainePrecedente[i * 5 + 1] * 100)) / 100, x, y + 155);
		}
		if (this.jourActuel < 15 && this.jourActuel > 1) {
			try {
				Thread.sleep(100000 / (int) (this.speedP));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (this.jourActuel < 14) {
			this.jourActuel++;
		}
	}

	public Image findArchetype(String nom) {
		Image image = null;
		for (int i = 0; i < this.archetypes.size(); i++) {
			if (this.archetypes.get(i).getName().equals(nom)) {
				image = this.archetypes.get(i);
			}
		}
		return image;
	}

	public static String sautLigne(String texte) {
		for (int i = 0; i + 20 < texte.length(); i = i + 20) {
			texte = texte.substring(0, i + 20) + "\n" + texte.substring(i + 20, texte.length());
		}
		return texte;
	}

	private void afficherEvent(Graphics g) {
		int x = this.xPopup + 100;
		int y = this.yPopup + 100;
		g.drawImage(this.popups[7], x, y);
		g.setColor(Color.green);
		g.drawString(Engine.event.getNom(), x + 270, y + 50);
		g.drawImage(findArchetype(Engine.event.getArchetype()), x + 275, y + 115);
		g.setColor(Color.black);
		g.drawString(sautLigne(Engine.event.getResume()), x + 220, y + 180);
		x = this.container.getWidth() / 2 - (Engine.event.getAccesChoix().size() * (15 + 45)) / 2 - 10;
		y = y + 330;
		if (Engine.event.getAccesChoix().size() == 0) {
			g.drawImage(boutonEvent, x, y);
			g.drawString("OK", x + 10, y + 14);
		}
		for (int i = 0; i < Engine.event.getAccesChoix().size(); i++) {
			g.drawImage(boutonEvent, x + i * (60), y);
			g.drawString(Engine.event.getAccesChoix().get(i).getNom(), x + 10 + i * (60), y + 14);
		}
	}

	private void appuyerBoutonEvent(int x, int y) {
		int a = this.container.getWidth() / 2 - (Engine.event.getAccesChoix().size() * (15 + 45)) / 2 - 10;
		int b = this.yPopup + 100 + 330;
		if (Engine.event.getAccesChoix().size() == 0 && ((x < a + 45 && x > a) && (y < b + 45 && y > b))) {
			// ne rien faire convient on ne fait que passer l'evenement
			Engine.faireAfficherEvent = false;
		}
		for (int i = 0; i < Engine.event.getAccesChoix().size(); i++) {
			if ((x < (a + 45) + i * 60 && x > a + i * 60) && (y < b + 45 && y > b)) {
				Engine.event.executer(i);
				Engine.faireAfficherEvent = false;
			}
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (button == 0) {
			if (showingPopup) {
				if (x > this.xPopup + 8 && x < this.xPopup + 8 + 66 && y > this.yPopup + 529
						&& y < this.yPopup + 529 + this.popups[this.popupId].getHeight() && this.sliderAgenda!=2) {
					this.showingPopup = false;

					switch (this.popupId) {
					case 1:
						this.devoirsC = ((this.xCursor - this.xSlider) * (this.widthSlider / 100)) / 90;
						if (devoirsC > 12) {
							devoirsC = 12;
						}
						if (devoirsC < 0) {
							devoirsC = 0;
						}
						break;

					case 2:
						this.nourritureC = ((this.xCursor - this.xSlider) * (this.widthSlider / 100)) / 90;
						if (nourritureC > 12) {
							nourritureC = 12;
						}
						if (nourritureC < 0) {
							nourritureC = 0;
						}
						break;

					case 3:
						this.sommeilC = ((this.xCursor - this.xSlider) * (this.widthSlider / 100)) / 90;
						if (sommeilC > 12) {
							sommeilC = 12;
						}
						if (sommeilC < 0) {
							sommeilC = 0;
						}
						break;

					case 5:
						this.speedC = ((this.xCursor - this.xSlider) * (this.widthSlider / 100)) / 90;
						if (speedC > 12) {
							speedC = 12;
						}
						if (speedC < 0) {
							speedC = 0;
						}
						break;
					}
				}

				if (isSliderPopup()) {
					if (x > this.xSlider && x < this.xSlider + this.widthSlider && y > this.ySlider
							&& y < this.ySlider + this.heightSlider) {
						if (this.container.getInput().isKeyDown(Keyboard.KEY_LSHIFT)) {
							this.xCursor = x;
						} else {
							if (this.xCursor < x
									&& xCursor + (this.widthSlider / 100) * 10 < this.xSlider + this.widthSlider) {
								this.xCursor += (this.widthSlider / 100) * 10;
							} else if ((this.xCursor) > x) {
								this.xCursor -= (this.widthSlider / 100) * 10;
							}
						}
					}
				}
				if (this.popupId == 4) {
					if (x > 350 + this.xPopup - 2 - this.flecheGauche.getWidth() / 2
							&& x < this.xPopup - 2 - this.flecheGauche.getWidth() / 2 + 86 + 350
							&& y > 30 + this.popups[4].getHeight() + this.yPopup && y < 30 + this.popups[4].getHeight() + this.yPopup + this.flecheDroite.getHeight()) {
						if (this.sliderAgenda == 2) {
							this.sliderAgenda = 1;
						}
					} else if (x > 350 + this.xPopup + 2 + this.flecheDroite.getWidth() / 2
							&& x < this.xPopup + 2 + this.flecheDroite.getWidth() / 2 + 86 + 350
							&& y > 30 + this.popups[4].getHeight() + this.yPopup && y < 30 + this.popups[4].getHeight() + this.yPopup + this.flecheDroite.getHeight()) {
						if (this.sliderAgenda==1) {
							this.sliderAgenda=2;
						}
					}
				}
				if(this.popupId==6)
				{
					if (x > 350 + this.xPopup - 2 - this.flecheGauche.getWidth() / 2
							&& x < this.xPopup - 2 - this.flecheGauche.getWidth() / 2 + 86 + 350
							&& y > this.yPopup + 550 && y < this.yPopup + 550 + this.popups[this.popupId].getHeight()) {
						if (this.pageMarket != 1) {
							this.pageMarket -= 1;
						}
					} else if (x > 350 + this.xPopup + 2 + this.flecheDroite.getWidth() / 2
							&& x < this.xPopup + 2 + this.flecheDroite.getWidth() / 2 + 86 + 350
							&& y > this.yPopup + 550 && y < this.yPopup + 550 + this.popups[this.popupId].getHeight()) {
						if (this.pageMarket <= this.objectsMarket.size() / 10) {
							this.pageMarket += 1;
						}
					}
					clicOnObjectMarket(x, y);
				}
				if (this.popupId == 0) {
					// Vérifie que ce soit le bouton validate pour afficher la semaine
					if (x > 530 + this.xPopup && x < 790 + this.xPopup && y > 530 + this.yPopup && y < 592 + this.yPopup
							&& this.finSemaine) {
						this.isValidate = !isValidate;// renvoie sur le render
						this.jourActuel = 1;
						if (this.isValidate) {
							this.finSemaine = false;
						}
					}
					if (Engine.faireAfficherEvent) {
						appuyerBoutonEvent(x, y);
					}
				}
			} else {
				for (int i = 0; i < this.clickables.length; i++) {
					if (x > this.coordinatesObjectClickable[i][0]
							&& x < this.coordinatesObjectClickable[i][0] + this.clickables[i].getWidth()
							&& y > this.coordinatesObjectClickable[i][1]
							&& y < this.coordinatesObjectClickable[i][1] + this.clickables[i].getHeight()) {
						this.showingPopup = true;
						this.popupId = i;

						if (isSliderPopup()) {
							switch (i) {
							case 1:
								this.xCursor = (this.devoirsC * 90) / (this.widthSlider / 100) + this.xSlider;
								break;

							case 2:
								this.xCursor = (this.nourritureC * 90) / (this.widthSlider / 100) + this.xSlider;
								break;

							case 3:
								this.xCursor = (this.sommeilC * 90) / (this.widthSlider / 100) + this.xSlider;
								break;

							case 5:
								this.xCursor = (this.speedC * 90) / (this.widthSlider / 100) + this.xSlider;
								break;

							default:
								this.xCursor = this.xSlider;
							}
							this.yCursor = this.ySlider - (this.cursor.getHeight() / 2) + (this.heightSlider / 2);
						}
					}
				}
			}
		}
	}

	private boolean isSliderPopup() {
		return this.popupId == 1 || this.popupId == 2 || this.popupId == 3 || this.popupId == 5;
	}

	public float getArgentP() {
		return argentP;
	}

	public void setArgentP(float argentP) {
		this.argentP = argentP;
	}

	public float getBonheurP() {
		return bonheurP;
	}

	public void setBonheurP(float bonheurP) {
		this.bonheurP = bonheurP;
	}
}

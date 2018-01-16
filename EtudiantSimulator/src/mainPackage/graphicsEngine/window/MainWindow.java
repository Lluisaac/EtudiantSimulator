
package mainPackage.graphicsEngine.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.objetsMarket.ListeObjets;
import mainPackage.gameEngine.objetsMarket.ObjetGeneral;
import mainPackage.graphicsEngine.component.JPanelJour;
import mainPackage.graphicsEngine.component.JPanelMarketElement;
import mainPackage.graphicsEngine.component.JPanelModifier;

@SuppressWarnings("serial")
public class MainWindow extends JFrame implements ActionListener, WindowListener {
	// Modifier
	JPanelModifier panelModifieur = new JPanelModifier();

	// Besoins
	JPanel panelBesoins;

	private JLabel labelArgent = new JLabel("0");
	private JLabel labelSavoir = new JLabel("0");
	private JLabel labelFaim = new JLabel("0");
	private JLabel labelFatigue = new JLabel("0");
	private JLabel labelBonheur = new JLabel("0");

	// Animation
	private ImageIcon checklistIcon = new ImageIcon("misc\\checklist.gif");
	private ImageIcon calendarIcon = new ImageIcon("misc\\calendar.gif");

	private JLabel labelAnimation = new JLabel(checklistIcon);

	// Vitesse
	private JSlider sliderVitesse = new JSlider();

	// Budget
	private JSlider sliderDevoirs = new JSlider();
	private JSlider sliderNourriture = new JSlider();
	private JSlider sliderSommeil = new JSlider();
	private JButton buttonValider = new JButton("Valider!");

	// Central
	JPanel panelCalendrier;
	private String stringMois = "Mois 1";
	private TitledBorder borderCentral = BorderFactory.createTitledBorder(stringMois);

	private JPanelJour[] panelJourTab = { new JPanelJour(), new JPanelJour(), new JPanelJour(), new JPanelJour(),
			new JPanelJour(), new JPanelJour(), new JPanelJour(), new JPanelJour(), new JPanelJour(), new JPanelJour(),
			new JPanelJour(), new JPanelJour(), new JPanelJour(), new JPanelJour() };

	private JButton buttonSuivant = new JButton("Suivant");

	// Sliders
	private int devoirs;
	private int nourriture;
	private int sommeil;

	// Magasin
	private JPanel panelMagasin;

	private boolean valider = false;

	public MainWindow() {
		// Window settings
		this.setTitle("Etudiant Simulator");
		this.setSize(1280, 520);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setIconImage(logoIcon.getImage());
		this.addWindowListener(this);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowClosed(WindowEvent e) {

				System.exit(0);
			}

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
	}
	
	public void activer() {
		this.buildMainWindow();
		this.setVisible(true);
	}

	public void buildMainWindow() {

		// Besoins
		panelBesoins = new JPanel();
		Box boxBesoins = Box.createVerticalBox();

		boxBesoins.add(labelArgent);
		boxBesoins.add(labelSavoir);
		boxBesoins.add(labelFaim);
		boxBesoins.add(labelFatigue);
		boxBesoins.add(labelBonheur);

		panelBesoins.add(boxBesoins);
		panelBesoins.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Besoins")));

		actualiserBesoins();

		// Animation
		JPanel panelAnimation = new JPanel();
		panelAnimation.add(labelAnimation);
		panelAnimation.setBorder(
				new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Animation !")));

		// Vitesse
		Box boxVitesse = Box.createVerticalBox();

		this.sliderVitesse.setMinimum(50);
		this.sliderVitesse.setMaximum(500);
		this.sliderVitesse.setValue(100);
		this.sliderVitesse.setPaintLabels(true);

		boxVitesse.add(new JLabel("Vitesse : x = 50% -> 500%, x^2"));
		boxVitesse.add(this.sliderVitesse);

		// Budget
		JPanel panelBudget = new JPanel();
		Box boxBudget = Box.createVerticalBox();

		this.sliderDevoirs.setMinimum(0);
		this.sliderDevoirs.setMaximum(100);
		this.sliderDevoirs.setValue(50);
		boxBudget.add(new JLabel("Devoir : Bonheur -> Savoir"));
		boxBudget.add(sliderDevoirs);
		this.sliderNourriture.setMinimum(0);
		this.sliderNourriture.setMaximum(100);
		this.sliderNourriture.setValue(50);
		boxBudget.add(new JLabel("Nourriture : Argent -> Faim"));
		boxBudget.add(sliderNourriture);
		this.sliderSommeil.setMinimum(0);
		this.sliderSommeil.setMaximum(100);
		this.sliderSommeil.setValue(50);
		boxBudget.add(new JLabel("Sommeil : Fatigue + -> Fatigue -"));
		boxBudget.add(sliderSommeil);
		this.buttonValider.setEnabled(false);
		this.buttonValider.addActionListener(this);
		boxBudget.add(buttonValider);

		panelBudget.add(boxBudget);
		panelBudget
				.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Budget")));

		// Interface Droite
		JPanel panelDroit = new JPanel();
		Box boxDroite = Box.createVerticalBox();

		boxDroite.add(panelBudget);
		boxDroite.add(panelModifieur);
		panelDroit.add(boxDroite);

		// Central
		panelCalendrier = new JPanel();
		panelCalendrier.setPreferredSize(new Dimension(700, 300));
		panelCalendrier.setLayout(new GridLayout(2, 7));
		for (int i = 0; i < this.panelJourTab.length; i++) {
			panelCalendrier.add(this.panelJourTab[i]);
		}

		panelCalendrier.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5),
				new CompoundBorder(borderCentral, new EmptyBorder(5, 5, 5, 5))));

		JPanel panelCentral = new JPanel();
		Box boxCentral = Box.createVerticalBox();

		boxCentral.add(panelCalendrier);
		this.buttonSuivant.setEnabled(false);
		this.buttonSuivant.addActionListener(this);
		boxCentral.add(buttonSuivant);
		panelCentral.add(boxCentral);

		// Interface Gauche
		JPanel panelGauche = new JPanel();
		Box boxGauche = Box.createVerticalBox();

		boxGauche.add(panelBesoins);
		boxGauche.add(panelAnimation);
		boxGauche.add(boxVitesse);

		panelGauche.add(boxGauche);

		// Magasin
		panelMagasin = new JPanel();
		panelMagasin.setBorder(BorderFactory.createTitledBorder("Boutique"));
		panelMagasin.setLayout(new FlowLayout());

		// Interface Generale
		this.setLayout(new BorderLayout());

		this.add(panelGauche, BorderLayout.WEST);
		this.add(panelCentral, BorderLayout.CENTER);
		this.add(panelDroit, BorderLayout.EAST);
		this.add(new JScrollPane(panelMagasin), BorderLayout.SOUTH);
		
		this.panelModifieur.addModifieur("Gain par mois : " + Engine.getPlayer().getGainParMois() + "�");
		this.panelModifieur.addModifieur("Loyer: " + Engine.getPlayer().getLoyer() + "�");

		this.buttonValider.setEnabled(true);
	}
	
	public void resetPanel() {
		this.getContentPane().removeAll();
		this.buildMainWindow();
	}

	public void mettreMois() {

		this.stringMois = "Mois " + Engine.journee.getMoisQuiArrive();
		this.borderCentral.setTitle(stringMois);
		this.panelCalendrier.repaint();
	}

	public void mettreJour() {

		if (Engine.journee.getJour() > 14) {
			this.panelJourTab[Engine.journee.getJour() - 15].setJour(Engine.journee.getJour());
		} else {
			this.panelJourTab[Engine.journee.getJour() - 1].setJour(Engine.journee.getJour());
		}
	}

	public void mettreIcone(String icone) {

		this.labelAnimation.setIcon(new ImageIcon(icone));
	}

	public void activerButtonSuivant() {

		this.buttonSuivant.setEnabled(true);
	}

	public void actualiserMagasin() {

		this.panelMagasin.removeAll();
		ArrayList<ObjetGeneral> temp = ListeObjets.getlisteObjetsDebloques();
		if (temp.size() > 0) {
			for (int i = 0; i < temp.size(); i++) {
				this.panelMagasin.add(new JPanelMarketElement(temp.get(i), Engine.getPlayer()));
			}
		}

		this.panelMagasin.repaint();
	}

	public void actualiserEtatArgent(Color color) {
		this.labelArgent.setForeground(color);
	}

	public void actualiserEtatFatigue(Color color) {
		this.labelFatigue.setForeground(color);
	}

	public void actualiserBesoins() {

		this.labelArgent.setText("Argent: " + (int) Engine.getPlayer().getArgent() + "�");
		this.labelSavoir.setText("Savoir: " + (int) Engine.getPlayer().getSavoir());
		this.labelFaim.setText("Faim: " + (int) Engine.getPlayer().getFaim() + "%");
		this.labelFatigue.setText("Fatigue: " + (int) Engine.getPlayer().getFatigue() + "%");
		this.labelBonheur.setText("Bonheur: " + (int) Engine.getPlayer().getBonheur() + "%");
	}

	public void setValider(boolean b) {

		this.valider = b;
	}

	public void reinitialiserCalendrier() {

		for (int i = 0; i < this.panelJourTab.length; i++) {
			this.panelJourTab[i].effacerContenu();
		}

		for (int i = 0; i < this.panelJourTab.length; i++) {
			if (Engine.journee.getJour() == 28) {
				this.panelJourTab[i].setJour(Engine.journee.getJour() + i - 27);
			} else {
				this.panelJourTab[i].setJour(Engine.journee.getJour() + i + 1);
			}
		}
	}

	public void creerContenuJour() {

		if (Engine.journee.getJour() < 15) {
			this.panelJourTab[Engine.journee.getJour() - 1]
					.setLabelFatigue(Engine.getPlayer().modifierFatigue(this.sommeil));
			this.panelJourTab[Engine.journee.getJour() - 1]
					.setLabelArgent(Engine.getPlayer().modifierArgent(this.nourriture));
			this.panelJourTab[Engine.journee.getJour() - 1]
					.setLabelFaim(Engine.getPlayer().modifierFaim(this.nourriture));
			this.panelJourTab[Engine.journee.getJour() - 1]
					.setLabelSavoir(Engine.getPlayer().modifierSavoir(this.devoirs));
			this.panelJourTab[Engine.journee.getJour() - 1]
					.setLabelBonheur(Engine.getPlayer().modifierBonheur(this.devoirs));
		} else {
			this.panelJourTab[Engine.journee.getJour() - 15]
					.setLabelFatigue(Engine.getPlayer().modifierFatigue(this.sommeil));
			this.panelJourTab[Engine.journee.getJour() - 15]
					.setLabelArgent(Engine.getPlayer().modifierArgent(this.nourriture));
			this.panelJourTab[Engine.journee.getJour() - 15]
					.setLabelFaim(Engine.getPlayer().modifierFaim(this.nourriture));
			this.panelJourTab[Engine.journee.getJour() - 15]
					.setLabelSavoir(Engine.getPlayer().modifierSavoir(this.devoirs));
			this.panelJourTab[Engine.journee.getJour() - 15]
					.setLabelBonheur(Engine.getPlayer().modifierBonheur(this.devoirs));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.buttonValider) {
			this.labelAnimation.setIcon(calendarIcon);
			this.devoirs = this.sliderDevoirs.getValue();
			this.nourriture = this.sliderNourriture.getValue();
			this.sommeil = this.sliderSommeil.getValue();

			this.buttonValider.setEnabled(false);
			this.valider = true;

			mettreIcone("misc\\calendar.gif");
		} else if (e.getSource() == this.buttonSuivant) {
			this.buttonSuivant.setEnabled(false);
			this.buttonValider.setEnabled(true);
			reinitialiserCalendrier();
			mettreMois();
		}
	}

	public int getWaitingTime() {
		return (int) (750.0 / Math.pow(((float) this.sliderVitesse.getValue() / 100.0), 2));
	}

	public void reset() {
		this.valider = false;
		this.buttonValider.setEnabled(true);
		this.buttonSuivant.setEnabled(false);

		this.labelArgent.setForeground(Color.GREEN);
		this.labelFatigue.setForeground(Color.BLACK);
		this.panelModifieur.cleanModifier();
		this.panelModifieur.addModifieur("Gain par mois : " + Engine.getPlayer().getGainParMois() + "�");
		this.panelModifieur.addModifieur("Loyer: " + Engine.getPlayer().getLoyer() + "�");
	}

	@Override
	public void windowClosed(WindowEvent e) {

		Engine.jeuFini = true;
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	public boolean isValidated() {
		return this.valider;
	}
}

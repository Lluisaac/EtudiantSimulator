
package mainPackage.graphicsEngine.component;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class JPanelJour extends JPanel {

	private JLabel labelJour = new JLabel(" ");

	private JLabel labelArgent = new JLabel(" ");
	private JLabel labelSavoir = new JLabel("                  ");
	private JLabel labelFaim = new JLabel(" ");
	private JLabel labelFatigue = new JLabel(" ");
	private JLabel labelBonheur = new JLabel(" ");

	public JPanelJour() {
		Box box = Box.createVerticalBox();

		box.add(labelJour);
		box.add(labelArgent);
		box.add(labelSavoir);
		box.add(labelFaim);
		box.add(labelFatigue);
		box.add(labelBonheur);
		this.add(box);

		this.setBorder(new LineBorder(Color.BLACK));

	}

	public void effacerContenu() {

		this.labelJour.setText(" ");
		this.labelArgent.setText(" ");
		this.labelSavoir.setText("                  ");
		this.labelFaim.setText(" ");
		this.labelFatigue.setText(" ");
		this.labelBonheur.setText(" ");
	}

	public float round1Decimal(float nb) {

		DecimalFormat df = new DecimalFormat("#.#");
		return Float.parseFloat(df.format(nb).replace(",", "."));
	}

	public void setJour(int jour) {

		this.labelJour.setText("Jour " + jour);
	}

	public void setLabelArgent(float argent) {

		this.labelArgent.setText("Argent: " + round1Decimal(argent));
	}

	public void setLabelSavoir(float savoir) {

		this.labelSavoir.setText("Savoir: " + round1Decimal(savoir));
	}

	public void setLabelFaim(float faim) {

		this.labelFaim.setText("Faim: " + round1Decimal(faim));
	}

	public void setLabelFatigue(float fatigue) {

		this.labelFatigue.setText("Fatigue: " + round1Decimal(fatigue));
	}

	public void setLabelBonheur(float bonheur) {

		this.labelBonheur.setText("Bonheur: " + round1Decimal(bonheur));
	}

}

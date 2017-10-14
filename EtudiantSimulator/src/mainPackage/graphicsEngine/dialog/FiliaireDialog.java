
package mainPackage.graphicsEngine.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;

@SuppressWarnings("serial")
public class FiliaireDialog extends JDialog implements ActionListener {

	private ButtonGroup group = new ButtonGroup();

	private JRadioButton[] buttons = {new JRadioButton(), new JRadioButton(), new JRadioButton()};
	private int[] filiaireIds = new int[3];

	private JButton buttonSelectionner = new JButton("Selectionner");

	public FiliaireDialog(JFrame parent) {
		super(parent, true);
		// Window settings
		this.setTitle("Etudiant Simulator");
		this.setSize(150, 150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.buttonSelectionner.addActionListener(this);

		ArrayList<Filiere> filiaires = ListeFilieres.getListeFilDebloquees();
		filiaires.add(null);

		Random rand = new Random();
		for (int j = 0; j < this.filiaireIds.length; j++) {
			int i = filiaires.size() - 1;
			while (filiaires.get(i) == null) {
				i = rand.nextInt(filiaires.size());
			}
			this.buttons[j].setText(filiaires.get(i).getNom());
			this.filiaireIds[j] = i;
			filiaires.set(i, null);
		}
		for (int i = 0; i < this.buttons.length; i++) {
			group.add(this.buttons[i]);
		}

		Box box = Box.createVerticalBox();
		box.add(new JLabel("Sélectionner votre filiaire : "));
		for (int i = 0; i < this.buttons.length; i++) {
			box.add(this.buttons[i]);
		}
		box.add(buttonSelectionner);

		this.add(box);
	}

	public int showDialog() {

		this.setVisible(true);

		for (int i = 0; i < this.buttons.length; i++) {
			if (this.buttons[i].isSelected()) {
				return this.filiaireIds[i];
			}
		}

		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (this.group.getSelection() != null) {
			this.setVisible(false);
		}
	}

}

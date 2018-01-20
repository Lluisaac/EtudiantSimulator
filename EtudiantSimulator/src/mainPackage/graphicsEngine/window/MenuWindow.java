
package mainPackage.graphicsEngine.window;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MenuWindow extends JDialog implements ActionListener {

	private int isNewGame = -1;

	private JLabel labelTitre = new JLabel("Etudiant Simulator");
	private JLabel labelVersion = new JLabel("Version Alpha 1.0");
	private JLabel labelCredit1 = new JLabel("Programmé par Louis Parent, Isaac LLuis et Kevin Villaroya");
	private JLabel labelCredit2 = new JLabel("Une idée originale de Isaac LLuis et Kevin Villaroya\n");
	private JLabel labelCredit3 = new JLabel("Images par Elisa Juin");

	private JButton buttonContinuer = new JButton("Continuer");
	private JButton buttonNouvellePartie = new JButton("Nouvelle Partie");
	private JButton buttonOption = new JButton("Options");
	private JButton buttonQuitter = new JButton("Quitter");

	public MenuWindow(JFrame parent) {
		super(parent, true);
		// Window settings
		this.setTitle("Etudiant Simulator");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// this.setIconImage(logoIcon.getImage());
		this.buttonContinuer.setEnabled(false);
		buildDialog();
	}

	private void buildDialog() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		this.labelVersion.setVerticalAlignment(SwingConstants.TOP);
		this.labelVersion.setHorizontalAlignment(SwingConstants.LEFT);

		this.labelTitre.setFont(new Font("Arial", Font.BOLD, 25));

		Box boxButton = Box.createVerticalBox();

		this.buttonContinuer.addActionListener(this);
		boxButton.add(buttonContinuer);
		this.buttonNouvellePartie.addActionListener(this);
		boxButton.add(buttonNouvellePartie);
		this.buttonOption.addActionListener(this);
		boxButton.add(buttonOption);
		this.buttonQuitter.addActionListener(this);
		boxButton.add(buttonQuitter);

		// Ligne 1
		constraints.gridx = 0;
		constraints.gridy = 0;

		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(labelVersion, constraints);

		// Ligne 2
		constraints.gridx = 0;
		constraints.gridy = 2;

		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(labelTitre, constraints);

		// Ligne 3
		constraints.gridx = 1;
		constraints.gridy = 3;

		constraints.gridheight = 2;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(boxButton, constraints);

		// Ligne 4
		constraints.gridx = 3;
		constraints.gridy = 5;

		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(labelCredit1, constraints);
		
		// Ligne 5
		constraints.gridx = 5;
		constraints.gridy = 7;

		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(labelCredit2, constraints);
		
		// Ligne 6
		constraints.gridx = 7;
		constraints.gridy = 9;

		constraints.gridheight = 1;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		this.add(labelCredit3, constraints);
	}

	public int showDialog() {

		this.setVisible(true);
		return this.isNewGame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.buttonContinuer) {
			this.isNewGame = 0;
			this.setVisible(false);
		}
		else if (e.getSource() == this.buttonNouvellePartie) {
			this.isNewGame = 1;
			this.setVisible(false);
		}
		else if (e.getSource() == this.buttonOption) {
			JOptionPane.showMessageDialog(this, "Work In Progress", "Option", JOptionPane.INFORMATION_MESSAGE);
		}
		else if (e.getSource() == this.buttonQuitter) {
			System.exit(0);
		}

	}
}

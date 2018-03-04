
package mainPackage.graphicsEngine.window;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

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
	private JLabel labelVersion = new JLabel();
	private ArrayList<JLabel> labelCredit = new ArrayList<JLabel>();

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

	@SuppressWarnings("resource")
	private void buildDialog() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		try {
			this.labelVersion.setText(new Scanner(new URL("http://lirmalys.ovh/EtudiantSimulator/GameFiles/update.txt").openStream(), "UTF-8").useDelimiter("\\A").next());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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

		String[] str = {};
		
		try {
			str = new Scanner(new URL("http://lirmalys.ovh/EtudiantSimulator/GameFiles/credits.txt").openStream(), "UTF-8").useDelimiter("\\A").next().split("\n");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < str.length; i++) {
			this.labelCredit.add(new JLabel(str[i]));
			
			constraints.gridx = 3 + (i * 2);
			constraints.gridy = 5 + (i * 2);
			
			constraints.gridheight = 1;
			
			constraints.gridwidth = GridBagConstraints.REMAINDER;
			this.add(labelCredit.get(i), constraints);
		}
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

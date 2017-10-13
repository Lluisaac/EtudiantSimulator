
package mainPackage.graphicsEngine.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.objetsMarket.ObjetGeneral;
import mainPackage.gameEngine.player.Player;

@SuppressWarnings({ "serial" })
public class JPanelMarketElement extends JPanel implements ActionListener {

	private JLabel labelNom = new JLabel();
	private ArrayList<JLabel> listeLabelAttributs = new ArrayList<JLabel>();
	private JButton buttonAcheter = new JButton();
	private ObjetGeneral objG;
	private Player player;

	public JPanelMarketElement(ObjetGeneral objG, Player player) {
		this.objG = objG;
		this.player = player;

		Box box = Box.createVerticalBox();

		labelNom.setText(objG.getNom() + ":");
		box.add(labelNom);

		for (int i = 0; i < objG.toTabString().size(); i++) {
			listeLabelAttributs.add(new JLabel(objG.toTabString().get(i)));
			box.add(listeLabelAttributs.get(i));
		}
		buttonAcheter.setText("Acheter!");
		buttonAcheter.addActionListener(this);
		box.add(buttonAcheter);

		this.add(box);

		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.buttonAcheter) {
			this.objG.setDebloque(false);
			Engine.getWindow().actualiserMagasin();
			this.objG.affectation(this.player);
		}
	}
}

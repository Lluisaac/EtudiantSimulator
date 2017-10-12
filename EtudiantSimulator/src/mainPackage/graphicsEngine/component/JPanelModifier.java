
package mainPackage.graphicsEngine.component;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JPanelModifier extends JPanel {

	private Vector<JLabel> modifieurs = new Vector<JLabel>();
	private Box box = Box.createVerticalBox();

	public JPanelModifier() {
		this.setBorder(BorderFactory.createTitledBorder("Modifieurs"));
	}

	public void addModifieur(String modifieur) {

		this.modifieurs.addElement(new JLabel("- " + modifieur));
		updatePanel();
	}

	public void updatePanel() {

		if (this.modifieurs.size() > 0) {
			this.remove(box);
			box = Box.createVerticalBox();
			for (int i = 0; i < this.modifieurs.size(); i++) {
				box.add(this.modifieurs.get(i));
			}
			this.add(box);
		}
	}

	public void cleanModifier() {

		modifieurs = new Vector<JLabel>();
		this.remove(box);
	}
}

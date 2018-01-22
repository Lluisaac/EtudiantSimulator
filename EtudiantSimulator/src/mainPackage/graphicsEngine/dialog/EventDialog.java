package mainPackage.graphicsEngine.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.event.Event;

@SuppressWarnings("serial")
public class EventDialog extends JDialog {

	private JPanel panelImage = new JPanel();
	private JPanel panelButton = new JPanel();
	private JPanel panelResume = new JPanel();

	private JLabel labelImage = new JLabel();
	private JLabel labelResume = new JLabel();

	private JButton button1;

	private ArrayList<JButton> buttons;

	private Event event;

	public EventDialog() {
		this.setLayout(new BorderLayout());
		this.panelImage.setLayout(new GridLayout(1, 1));

		this.panelImage.add(labelImage);

		this.panelResume.add(labelResume);
		this.labelResume.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.panelButton.add(button1);

		this.add(this.panelImage, BorderLayout.NORTH);
		this.add(this.panelResume, BorderLayout.CENTER);
		this.add(this.panelButton, BorderLayout.SOUTH);

		this.setSize(300, 300);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public EventDialog(Event event) {
		this(event.getNom(), event.getResume(), event.getArchetype());
		this.event = event;

		if (event.getSizeAccesChoix() == 0) {
			this.button1 = new JButton("Ok");
			this.button1.addActionListener(new ItemActionChange());
		} else {
			for (int i = 0; i < event.getSizeAccesChoix(); i++) {
				if (!event.getAccesChoix().get(i).getNom().contains("noDefault_")) {
					this.buttons.add(new JButton(event.getAccesChoix().get(i).getNom()));
				} else {
					this.buttons.add(new JButton(event.getAccesChoix().get(i).getNom().substring(10)));
				}
				this.button1.addActionListener(new ItemActionChange());
			}
		}
	}

	public EventDialog(String nom, String resume, String archetype) {
		this();
		this.setTitle(nom);

		BufferedImage myPicture;

		try {
			myPicture = ImageIO.read(new File("archetypes\\" + archetype + ".png"));

			BufferedImage scaledImage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(myPicture, 0, 0, 32, 32, null);

			graphics2D.dispose();

			myPicture = scaledImage;

			this.labelImage.setIcon(new ImageIcon(myPicture));

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.labelResume.setText("<html><div style='text-align: center;'><h3>" + sautLigne(resume) + "</div></html>");
	}

	public String sautLigne(String str) {
		String string = "";

		String[] tab = str.split(" ");
		int taille = 0;

		for (int i = 0; i < tab.length; i++) {
			taille += tab[i].length() + 1;
			if (taille < 38 && !tab[i].equals("&")) {
				string += tab[i] + " ";
			} else {
				if (!tab[i].equals("&")) {
					i--;
				}
				string += "<br>";
				taille = 0;
			}
		}

		return string;
	}

	class ItemActionChange implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == button1) {
				event.executerDefault();
			} else {
				for (int i = 0; i < buttons.size(); i++) {
					if (e.getSource() == buttons.get(i)) {
						event.executer(i);
					}
				}
			}

			if (event.getOccurence() != -1) {
				event.setOccurence(event.getOccurence() - 1);
			}
			Engine.eventFini = true;
			Engine.eventDialog.dispose();
		}
	}
}

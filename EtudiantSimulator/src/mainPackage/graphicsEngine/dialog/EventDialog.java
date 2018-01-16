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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainPackage.gameEngine.Engine;

@SuppressWarnings("serial")
public class EventDialog extends JDialog {

	private JPanel panelImage = new JPanel();
	private JPanel panelButton = new JPanel();
	private JPanel panelResume = new JPanel();
	
	private JLabel labelImage = new JLabel();
	private JLabel labelResume = new JLabel();
	
	private JButton button1 = new JButton("Oui");
	private JButton button2 = new JButton("Non");
	
	public EventDialog() {
		this.setLayout(new BorderLayout());
		this.panelImage.setLayout(new GridLayout(1,1));
		
		this.panelImage.add(labelImage);
		
		this.panelResume.add(labelResume);
		this.labelResume.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.panelButton.add(button1);
		this.panelButton.add(button2);
		
		this.add(this.panelImage, BorderLayout.NORTH);
		this.add(this.panelResume, BorderLayout.CENTER);
		this.add(this.panelButton, BorderLayout.SOUTH);
		
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.button1.addActionListener(new ItemActionChange());
		this.button2.addActionListener(new ItemActionChange());
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
			if (taille < 38) {
				string += tab[i] + " ";
			} else {
				string += "<br>";
				i--;
				taille = 0;
			}
		}
		
		return string;
	}
	
	class ItemActionChange implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Engine.eventFini = true;
			Engine.eventDialog.dispose();
		}
	}

}

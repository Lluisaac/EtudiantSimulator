package mainPackage.graphicsEngine.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.xml.sax.SAXException;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.filiere.Filiere;
import mainPackage.gameEngine.filiere.ListeFilieres;

public class FiliereState extends BasicGameState
{
	public static final int ID = 2;
	
	private GameContainer container;
	private StateBasedGame game;

	private Image background;
	
	private ArrayList<Filiere> filieres = new ArrayList<Filiere>();
	private ArrayList<Image> buttons = new ArrayList<Image>();
	private ArrayList<int[]> origins = new ArrayList<int[]>();
	
	private UnicodeFont titleFont;
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.container = container;
		this.game = game;
		
		try 
		{
			Font UIFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/font/AlexBrush.ttf"));
			UIFont = UIFont.deriveFont(Font.PLAIN, 50.0f);
			this.titleFont = new UnicodeFont(UIFont);
			
			this.titleFont.addAsciiGlyphs();
			
			ColorEffect a = new ColorEffect();
			a.setColor(Color.blue);
			this.titleFont.getEffects().add(a);
			
			this.titleFont.loadGlyphs();
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.background = new Image("assets/image/background.png");
		
		ListeFilieres.mettreFillieres();
		int[] intFiliere = new int[3];
		for(int i=0;i<3;i++)
		{
			intFiliere[i]=-1;
		}
		
		int x;
		for(int i=0;i<3;i++)
		{
			x=(int) Math.floor(Math.random() * Math.floor(ListeFilieres.getListeFilDebloquees().size()));
			while(appartient(intFiliere,x))
			{
				x=(int) Math.floor(Math.random() * Math.floor((ListeFilieres.getListeFilDebloquees().size())));
			}
			this.filieres.add(ListeFilieres.getListeFilDebloquees().get(x));
			intFiliere[i]=x;
		}
		
						
		for(int i = 0; i < this.filieres.size(); i++)
		{
			this.buttons.add(new Image("assets/menu/button.png"));
			int[] coords = {(container.getWidth() / 2) - (this.buttons.get(i).getWidth() / 2), (i + 1) * container.getHeight() / (this.filieres.size() + 1)};
			this.origins.add(coords);
		}
		
	}
	
	public boolean appartient(int[] tableau,int x)
	{
		for(int i=0;i<tableau.length;i++)
		{
			if(tableau[i]==x)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		g.setFont(this.titleFont);

		this.background.draw(0, 0, container.getWidth(), container.getHeight());
				
		for(int i = 0; i < this.filieres.size(); i++)
		{
			g.drawImage(this.buttons.get(i), this.origins.get(i)[0], this.origins.get(i)[1]);
			g.drawString(this.filieres.get(i).getNom(), this.origins.get(i)[0] + (this.titleFont.getWidth(this.filieres.get(i).getNom()) / 2), this.origins.get(i)[1] + (this.titleFont.getHeight(this.filieres.get(i).getNom()) / 3));
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		
	}

	@Override
	public int getID()
	{
		return ID;
	}
	
	@Override
	public void keyPressed(int key, char c) 
	{
		if(key == Keyboard.KEY_ESCAPE)
		{
			if (key == Keyboard.KEY_ESCAPE) {
				Option.stateBefore=this.getID();
				this.game.enterState(4);
			}
		}
	}

	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
		if(button == 0)
		{
			for(int i = 0; i < this.filieres.size(); i++)
			{
				if(x > this.origins.get(i)[0] && x < this.origins.get(i)[0] + this.buttons.get(i).getWidth() && y > this.origins.get(i)[1] && y < this.origins.get(i)[1] + this.buttons.get(i).getHeight())
				{
					this.game.enterState(GameState.ID);
					try {
						Engine.createEngine(true, this.filieres.get(i));
					} catch (SAXException | IOException | ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
}

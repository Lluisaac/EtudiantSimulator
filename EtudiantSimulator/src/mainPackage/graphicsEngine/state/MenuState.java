package mainPackage.graphicsEngine.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

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

import mainPackage.gameEngine.Engine;

public class MenuState extends BasicGameState
{
	public static final int ID = 0;
	
	private GameContainer container;
	private StateBasedGame game;
	
	private Image background;
	private Image newGameButton, continueButton, optionButton, quitButton;
	
	private UnicodeFont infoFont, titleFont;
	private String title = "Etudiant Simulator";
	private String credit = "Programmé par Louis Parent, Isaac Lluis et Kevin Villaroya\nUne idée originale de Isaac Lluis et Kevin Villaroya\nImages par Elisa Juin";
	private String version = "Version Alpha 1.1";
	
	private int[][] buttonCoords = new int[4][2];
	private int[][] stringCoords = new int[3][2];

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{		
		//Container
		this.container = container;
		this.game = game;
		
		//Import img
		this.background = new Image("assets/image/background.png");
		
		this.newGameButton = new Image("assets/menu/new_game_button.png");
		this.continueButton = new Image("assets/menu/continue_button.png");
		this.optionButton = new Image("assets/menu/option_button.png");
		this.quitButton = new Image("assets/menu/quit_button.png");
		
		//Import font
		try 
		{
			Font UIFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/font/Exo2.otf"));
			UIFont = UIFont.deriveFont(Font.PLAIN, 24.0f);
			this.infoFont = new UnicodeFont(UIFont);
			
			this.infoFont.addAsciiGlyphs();
			
			ColorEffect a = new ColorEffect();
			a.setColor(Color.blue);
			this.infoFont.getEffects().add(a);
			
			this.infoFont.loadGlyphs();
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try 
		{
			Font UIFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("assets/font/AlexBrush.ttf"));
			UIFont = UIFont.deriveFont(Font.PLAIN, 100.0f);
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
		
		//Button coords
		for(int i = 0; i < buttonCoords.length; i++)
		{
			buttonCoords[i][0] = (container.getWidth() / 2) - (this.newGameButton.getWidth() / 2);
			buttonCoords[i][1] = (container.getHeight() / 2) +  i * (this.newGameButton.getHeight() + 10);
		}
		
		//String coords
		stringCoords[0][0] = 0;
		stringCoords[0][1] = 0;
		
		stringCoords[1][0] = (container.getWidth() / 2) - (this.titleFont.getWidth(this.title) / 2);
		stringCoords[1][1] = (container.getHeight() / 2) - this.titleFont.getHeight(this.title) - 40;
		
		stringCoords[2][0] = container.getWidth() - this.infoFont.getWidth(this.credit);
		stringCoords[2][1] = container.getHeight() - this.infoFont.getHeight(this.credit);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		//Button drawing
		this.background.draw(0, 0, this.container.getWidth(), this.container.getHeight());
		//Button drawing
		this.newGameButton.draw(this.buttonCoords[0][0], this.buttonCoords[0][1]);
		this.continueButton.draw(this.buttonCoords[1][0], this.buttonCoords[1][1]);
		this.optionButton.draw(this.buttonCoords[2][0], this.buttonCoords[2][1]);
		this.quitButton.draw(this.buttonCoords[3][0], this.buttonCoords[3][1]);	
		
		//String drawing
		g.setFont(this.infoFont);
		g.drawString(this.version, stringCoords[0][0], stringCoords[0][1]);
		g.drawString(this.credit, stringCoords[2][0], stringCoords[2][1]);
		
		g.setFont(this.titleFont);
		g.drawString(this.title, stringCoords[1][0], stringCoords[1][1]);

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
			this.container.exit();
		}
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) 
	{
		if(button == 0)
		{
			if(x > this.buttonCoords[0][0] && x < this.buttonCoords[0][0] + this.newGameButton.getWidth() && y > this.buttonCoords[0][1] && y < this.buttonCoords[0][1] + this.newGameButton.getHeight())
			{
				this.game.enterState(FiliereState.ID);
			}
			else if(x > this.buttonCoords[1][0] && x < this.buttonCoords[1][0] + this.continueButton.getWidth() && y > this.buttonCoords[1][1] && y < this.buttonCoords[1][1] + this.continueButton.getHeight())
			{
				this.game.enterState(GameState.ID);
			}
			else if(x > this.buttonCoords[2][0] && x < this.buttonCoords[2][0] + this.optionButton.getWidth() && y > this.buttonCoords[2][1] && y < this.buttonCoords[2][1] + this.optionButton.getHeight())
			{
				this.game.enterState(Option.ID);
				
			}
			else if(x > this.buttonCoords[3][0] && x < this.buttonCoords[3][0] + this.quitButton.getWidth() && y > this.buttonCoords[3][1] && y < this.buttonCoords[3][1] + this.quitButton.getHeight())
			{
				this.container.exit();
			}
		}
	}
}

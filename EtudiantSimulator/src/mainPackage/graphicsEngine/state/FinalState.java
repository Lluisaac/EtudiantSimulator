package mainPackage.graphicsEngine.state;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import mainPackage.gameEngine.Engine;
import mainPackage.gameEngine.jour.Jour;

public class FinalState extends BasicGameState
{
	public static final int ID = 3;
	
	private GameContainer container;
	private StateBasedGame game;
	
	private ArrayList<Image> fins = new ArrayList<Image>();
	private static String typeFin = "MortArgent";
	private static String commentaireFin = "va voir la fonction sur Final State remplie la";
	private int x;
	private int y;
	private boolean finAfficher=false;
	Image image;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		this.game=arg1;
		placeFinInArray();
		image = new Image("assets/fins/MortArgent.png");
		this.container=arg0;
		this.y=arg0.getHeight()/2;
		this.x=arg0.getWidth()/2;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setColor(Color.white);
		for(int i=0;i<fins.size();i++)
		{
			if(fins.get(i).getName().equals(typeFin))
			{
				this.image=fins.get(i);
				this.finAfficher=true;
			}
		}
		
		g.drawImage(image, this.x - this.image.getWidth()/2, this.y - this.image.getHeight()/2);
		g.drawString(this.commentaireFin, this.x - this.commentaireFin.length()*5, this.y + this.image.getHeight()/2+20);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		if(this.commentaireFin.equals("GG on maintiens le cap") && this.finAfficher)
		{
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(this.commentaireFin.equals("GG on maintiens le cap"))
			{
				Engine.isGameOver=false;
			}
			this.finAfficher=false;
			this.game.enterState(GameState.ID);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	public static void finJeu(String nom, String texte)
	{
		typeFin=nom;
		commentaireFin=texte;
		Engine.isGameOver=true;
	}
	
	public void placeFinInArray() throws SlickException
	{
		File[] f= new File("assets/fins").listFiles();
		for(int i=0;i<f.length;i++)
		{
			if(f[i].getName().matches(".*png"))
			{
				String name=f[i].getName();
				this.fins.add(new Image("assets/fins/" + name));
				name = name.substring(0, name.length()-4);
				this.fins.get(i).setName(name);
			}
		}
	}
}

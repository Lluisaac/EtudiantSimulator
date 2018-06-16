package mainPackage.graphicsEngine.state;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Option extends BasicGameState
{
	public static final int ID = 4;
	
	private GameContainer container;
	private StateBasedGame game;
	private Image background;
	
	private Image fullscreenImage;
	private Image volumeImage;
	private Image retour;
	private Image check;
	private Image barreNoir;
	private Image barreJaune;
	private Image quitter;
	
	public static boolean fullscreen=false;
	public static float volume=100;
	public static Music backgroundMusique;
	
	public static int stateBefore=0;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		backgroundMusique = new Music("assets/music/background.ogg");
		fullscreenImage=new Image("assets/option/fullscreen.png");
		volumeImage=new Image("assets/option/volume.png");
		retour=new Image("assets/option/retour.png");
		check=new Image("assets/option/check.png");
		barreNoir= new Image("assets/option/barreNoir.png");
		barreJaune= new Image("assets/option/barreJaune.png");
		quitter=new Image("assets/option/quitter.png");
		this.container = container;
		this.game = game;
		
		
		this.background = new Image("assets/image/background.png");
		backgroundMusique.loop();
		Option.backgroundMusique.setVolume(Option.volume/100);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		this.background.draw(0, 0, this.container.getWidth(), this.container.getHeight());
		g.setColor(Color.blue);
		g.drawImage(retour,10,this.container.getHeight()- this.retour.getHeight() - 10);
		g.drawImage(fullscreenImage, 150, 100);
		g.drawRect(this.fullscreenImage.getWidth() + 160, 100 + this.fullscreenImage.getHeight()/2 - 25, 50, 50);
		g.drawImage(volumeImage, 150, 200);
		if(Option.fullscreen)
		{
			g.drawImage(check, this.fullscreenImage.getWidth() + 155, 100 + this.fullscreenImage.getHeight()/2 - 30);
		}
		g.drawImage(barreNoir, 160 + this.volumeImage.getWidth(), 200 + this.volumeImage.getHeight()/2 - this.barreNoir.getHeight()/2);
		g.drawImage(barreJaune, 160 + this.volumeImage.getWidth() + Option.volume*2, 200 + this.volumeImage.getHeight()/2 - this.barreJaune.getHeight()/2);
		g.drawImage(quitter, this.container.getWidth()-this.quitter.getWidth()-10, this.container.getHeight()-this.quitter.getHeight() - 10);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		backgroundMusique.setVolume(Option.volume/100);
		try {
			FileOutputStream config = new FileOutputStream("saves/config.etsim");
			config.write(("fullscreen:" + fullscreen +"\r\n").getBytes());
			config.write(("volume:" + volume).getBytes());
			config.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(button==0)
		{
			if(x>10 && x<250 && y>this.container.getHeight()- this.retour.getHeight() - 10 && y<this.container.getHeight()- this.retour.getHeight() - 10+80)
			{
				game.enterState(this.stateBefore);
			}
			if(x>this.fullscreenImage.getWidth() + 160 && x<this.fullscreenImage.getWidth() + 160+50 && y>100 + this.fullscreenImage.getHeight()/2 - 25 && y<100 + this.fullscreenImage.getHeight()/2 - 25+50)
			{
				Option.fullscreen=!fullscreen;
			}
			if(x>160 + this.volumeImage.getWidth() && x<160 + this.volumeImage.getWidth()+200 && y>200 + this.volumeImage.getHeight()/2 - this.barreNoir.getHeight()/2 && y<200 + this.volumeImage.getHeight()/2 - this.barreNoir.getHeight()/2+17)
			{
				Option.volume=(x-(160 + this.volumeImage.getWidth()))/2;
			}
			if(x>this.container.getWidth()-this.quitter.getWidth()-10 && x<this.container.getWidth()-this.quitter.getWidth()+230 && y>this.container.getHeight()-this.quitter.getHeight() - 10 && y<this.container.getHeight()-this.quitter.getHeight() + 70)
			{
				this.container.exit();
			}
		}
	}
	
}
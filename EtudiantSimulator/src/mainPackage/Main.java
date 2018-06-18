package mainPackage;
import mainPackage.graphicsEngine.state.Option;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import mainPackage.graphicsEngine.StateGame;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		// Nettoyage de la console
		System.out.flush();
		System.err.flush();
		try 
		{
			try {
				FileInputStream config = new FileInputStream("saves/config.etsim");
				int content;
				String inConfig="";
				while ((content = config.read()) != -1) {
					inConfig= inConfig + (char) content;
				}
				Option.fullscreen=Boolean.parseBoolean(inConfig.split("fullscreen:")[1].split("\r\n")[0]);
				Option.volume=Float.parseFloat(inConfig.split("volume:")[1]);
				config.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new AppGameContainer(new StateGame(), 1920, 1080, Option.fullscreen).start();
		} catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}
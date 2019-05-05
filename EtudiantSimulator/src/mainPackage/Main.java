package mainPackage;

import mainPackage.graphicsEngine.state.Option;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import mainPackage.graphicsEngine.StateGame;

public class Main {

	public static void main(String[] args) throws IOException {
		nettoyerConsole();
		disableWarning();
		chargerPartie();
	}

	private static void chargerPartie() throws IOException {
		try {
			chargerSave();
			new AppGameContainer(new StateGame(), 1920, 1080, Option.fullscreen).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private static void chargerSave() throws IOException {
		try {
			FileInputStream config = new FileInputStream("saves/config.etsim");
			int content;
			String inConfig = "";
			while ((content = config.read()) != -1) {
				inConfig = inConfig + (char) content;
			}
			faireOptions(inConfig);
			config.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void faireOptions(String inConfig) {
		Option.fullscreen = Boolean.parseBoolean(inConfig.split("fullscreen:")[1].split("\r\n")[0]);
		Option.volume = Float.parseFloat(inConfig.split("volume:")[1]);
	}

	private static void nettoyerConsole() {
		System.out.flush();
		System.err.flush();
	}

	public static void disableWarning() {
		System.err.close();
		System.setErr(System.out);
	}
}
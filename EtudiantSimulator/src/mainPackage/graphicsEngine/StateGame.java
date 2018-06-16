package mainPackage.graphicsEngine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import mainPackage.graphicsEngine.state.FiliereState;
import mainPackage.graphicsEngine.state.FinalState;
import mainPackage.graphicsEngine.state.GameState;
import mainPackage.graphicsEngine.state.MenuState;
import mainPackage.graphicsEngine.state.Option;

public class StateGame extends StateBasedGame
{
    public StateGame()
    {
    	super("Etudiant Simulator");
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException
    {
		addState(new MenuState());
		addState(new FinalState());
		addState(new FiliereState());
		addState(new GameState());
		addState(new Option());
    }
}

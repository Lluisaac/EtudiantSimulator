package mainPackage.gameEngine.event;

import java.util.ArrayList;

import mainPackage.gameEngine.Engine;

public class ModificateurPlayer {//L'ordre est � respecter
	private float argent;
	private float savoir;
	private float faim;
	private float fatigue;
	private float bonheur;

	private int argentJ = 0;
	private int savoirJ = 0;
	private int faimJ = -4;
	private int fatigueJ = 15;
	private int bonheurJ = 0;
	
	private int gainParMois;
	private int loyer;
	
	ModificateurPlayer(String modif)
	{
		String[] contentTab = modif.split("|");
		
		this.argent=Float.parseFloat( contentTab[0] );
		this.savoir=Float.parseFloat( contentTab[1] );
		this.faim=Float.parseFloat( contentTab[2] );
		this.fatigue=Float.parseFloat( contentTab[3] );
		this.bonheur=Float.parseFloat( contentTab[4] );
		
		this.argentJ=Integer.parseInt( contentTab[5] );
		this.savoirJ=Integer.parseInt( contentTab[6] );
		this.faimJ=Integer.parseInt( contentTab[7] );
		this.fatigueJ=Integer.parseInt( contentTab[8] );
		this.bonheurJ=Integer.parseInt( contentTab[9] );
		
		this.gainParMois=Integer.parseInt( contentTab[10] );
		this.loyer=Integer.parseInt( contentTab[11] );
	}
	
	public void appliquer()
	{
		Engine.getPlayer().setArgent(this.argent + Engine.getPlayer().getArgent());
		Engine.getPlayer().setSavoir(this.savoir + Engine.getPlayer().getSavoir());
		Engine.getPlayer().setFaim(this.faim + Engine.getPlayer().getFaim());
		Engine.getPlayer().setFatigue(this.fatigue + Engine.getPlayer().getFatigue());
		Engine.getPlayer().setBonheur(this.bonheur + Engine.getPlayer().getBonheur());
		
		Engine.getPlayer().setArgentJ(this.argentJ + Engine.getPlayer().getArgentJ());
		Engine.getPlayer().setSavoirJ(this.savoirJ + Engine.getPlayer().getSavoirJ());
		Engine.getPlayer().setFaimJ(this.faimJ + Engine.getPlayer().getFaimJ());
		Engine.getPlayer().setFatigueJ(this.fatigueJ + Engine.getPlayer().getFatigueJ());
		Engine.getPlayer().setBonheurJ(this.bonheurJ + Engine.getPlayer().getBonheurJ());
		
		Engine.getPlayer().setGainParMois(this.gainParMois + Engine.getPlayer().getGainParMois());
		Engine.getPlayer().setLoyer(this.loyer + Engine.getPlayer().getLoyer());
	}
	
	public static ArrayList<ModificateurPlayer> createArrayFromString(String liste) {
		//Va cr�er chaque modifObjet pour en faire une array list, chaque modifObjet doit etre s�par� par _
		
		if (liste.equals("")) {
			return null;
		}
		
		ArrayList<ModificateurPlayer> modifListe = new ArrayList<ModificateurPlayer>();
		
		String[] contentTab = liste.split("_");
		
		for (int i = 0; i < contentTab.length - 1; i++) {
			modifListe.add(new ModificateurPlayer(contentTab[i]));
		}
		
		return modifListe;
	}
}

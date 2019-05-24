package game.controllers.pacman;

import game.PacManSimulator;
import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.G;
import game.core.Game;
import game.core.GameView;

import java.awt.Color;

/*
 * FSM strategy. Pacman moves randomly eaten pellets; when power pill is eaten, pacman chase 
 * ghosts. When ghosts are flashing, pacman evade ghosts; finally, when no visible ghosts, 
 * he keeps seeking pellets.
 * Please note: the visuals are just to highlight different functionalities and may
 * not make sense from a controller's point of view (i.e., they might not be useful)
 * Comment/un-comment code below as desired (drawing all visuals would probably be too much).
 */
public final class FirstStateMachine extends PacManHijackController
{	
	boolean evade = false;
	boolean chase = false;

	@Override
	public void tick(Game game, long timeDue) {
		//
		int current=game.getCurPacManLoc();
		int[] activePills=game.getPillIndicesActive();
		int[] activePowerPills=game.getPowerPillIndicesActive();
		int[] targetsArray=new int[activePills.length+activePowerPills.length];
		int[] ghostsTarg=new int[4];
		

		//index for active pills
		for(int i=0;i<activePills.length;i++)
			targetsArray[i]=activePills[i];
		//index for active power pills
		for(int i=0;i<activePowerPills.length;i++)
			targetsArray[activePills.length+i]=activePowerPills[i];		
		//index of all ghosts
		for(int i=0;i<G.NUM_GHOSTS;i++)
			ghostsTarg[i]=game.getCurGhostLoc(i);	


		//add the path from Ms Pac-Man to the nearest existing pill
		int nearest=game.getTarget(current,targetsArray,true,G.DM.PATH);		
		
		//add the path that Ms Pac-Man is following
		GameView.addPoints(game,Color.GREEN,game.getPath(current,nearest));
		
		//show the distance to all the ghosts
		int[] ghostDistances = new int[]{ game.getPathDistance(current, game.getCurGhostLoc(0)), game.getPathDistance(current, game.getCurGhostLoc(1)), game.getPathDistance(current, game.getCurGhostLoc(2)), game.getPathDistance(current, game.getCurGhostLoc(3))};
		GameView.addText(0, 10, Color.YELLOW, "Ghost distances: " + ghostDistances[0] + ", " + ghostDistances[1] + ", " + ghostDistances[2] + ", " + ghostDistances[3]);
		
		//add lines to the ghosts (if not in lair) - green if edible, red otherwise
		for(int i=0;i<G.NUM_GHOSTS;i++)										
			if(game.getLairTime(i)==0)
				if(game.isEdible(i))
					GameView.addLines(game,Color.GREEN,current,game.getCurGhostLoc(i));
				else
					GameView.addLines(game,Color.RED,current,game.getCurGhostLoc(i));
		
	
		//check if all ghosts are edible (power pill was eaten)
		if(game.isEdible(1)==true && game.isEdible(2)==true && game.isEdible(3)==true && game.isEdible(4)==true)
		{
			evade = false;
			chase = true;
		}

		//check if any ghosts is flashing
		if(chase == true  && (game.isEdible(1) == false|| game.isEdible(2) == false || game.isEdible(3) == false || game.isEdible(4) == false))
		{
			chase = false;
			evade = true;
		}
		//check if any ghosts is visible
		boolean visible = false;
		for(int i = 0;i<G.NUM_GHOSTS && visible == false;++i)
		{
			if(game.getX(ghostsTarg[i]) == game.getX(game.getCurPacManLoc()) || game.getY(ghostsTarg[i]) == game.getY(game.getCurPacManLoc()))
			{
				visible = true;
			}
		}	
		if(visible == true)
		{
			chase = false;
			evade = true;
		}
		else
		{
			chase = false;
			evade = false;
		}

		if(evade==true) //if evade, pacman will evade the nearest ghost
		{
			nearest = game.getTarget(current,ghostsTarg,true,G.DM.PATH);
			pacman.set(game.getNextPacManDir(nearest,false,G.DM.PATH));	
		}
		else
		{
			if(chase==true) //if chase, pacman will chase the closest ghost
			{
				nearest = game.getTarget(current,ghostsTarg,true,G.DM.PATH);
				pacman.set(game.getNextPacManDir(nearest,true,G.DM.PATH));	
			}
			else //pacman will seek the pellets
			{
				pacman.set(game.getNextPacManDir(nearest,true,G.DM.PATH));	
			}
		}
	
		System.out.println("Evade is " + evade);
		System.out.println("Chase is " + chase);

	}
	
	public static void main(String[] args) {
		while (true) {
			PacManSimulator.play(new FirstStateMachine(), new GameGhosts(false));
			GameView.lastInstance.setVisible(false);
		}
	}
}
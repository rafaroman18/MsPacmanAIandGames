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
 */
public final class FiniteStateMachine extends PacManHijackController {
	// States Matrix

	// Sates Codes:
	// Seek pellets -> 0
	// Chase Ghosts -> 1
	// Evade Ghosts -> 2

	// Transitions Codes:
	// Power Pill eaten -> 0
	// Ghosts Flashing -> 1
	// No visible Ghost -> 2
	// Ghost in sight -> 3

	int states[][] = { { 1, -1, -1, 2 }, { -1, 2, -1, -1 }, { -1, -1, 0, -1 } };
	// Begins with Seek Pellets
	int currentState = 0;

	// Has Power Pill been eaten?...
	int storagePP = 0;
	int nPPeat = 0;

	// Constans about the time ghosts start to flash and the distance which pacman
	// should evade ghosts
	int VISIBLE_DISTANCE = 15;
	int TIME_FLASH = 35;

	@Override
	public void tick(Game game, long timeDue) {
		// check if any PowerPill has been eaten
		if (storagePP == 0) {

			nPPeat = game.getNumberPowerPills();
		} else {
			nPPeat = storagePP;
		}
		int current = game.getCurPacManLoc();
		int[] activePills = game.getPillIndicesActive();
		int[] activePowerPills = game.getPowerPillIndicesActive();
		int[] targetsArray = new int[activePills.length + activePowerPills.length];
		int[] ghostsTarg = new int[4]; //nodes where ghosts are
		int[] EdibleGhost; //index of Edible ghosts 
		int[] notEdibleGhost; //index of NOT Edible ghosts
		int EdibleGhostLength = 0;
		int notEdibleGhostLength = 0;

		// index for active pills
		for (int i = 0; i < activePills.length; i++)
			targetsArray[i] = activePills[i];
		// index for active power pills
		for (int i = 0; i < activePowerPills.length; i++)
			targetsArray[activePills.length + i] = activePowerPills[i];
		// index of all ghosts
		for (int i = 0; i < G.NUM_GHOSTS; i++)
			ghostsTarg[i] = game.getCurGhostLoc(i);

		//index of edible and non-edible ghosts
		int[] aux1 = new int[4];
		int[] aux2 = new int[4];
		for(int i = 0;i<Game.NUM_GHOSTS;++i)
		{
			if(game.isEdible(i))
			{
				aux1[EdibleGhostLength] = game.getCurGhostLoc(i);
				EdibleGhostLength++;
			}
			else
			{
				aux2[notEdibleGhostLength] = game.getCurGhostLoc(i);
				notEdibleGhostLength++;
			}
		}
		EdibleGhost = new int[EdibleGhostLength];
		notEdibleGhost = new int[notEdibleGhostLength];
		for(int i = 0,j = 0;i < EdibleGhostLength || j < notEdibleGhostLength ;++j,++i)
		{
			if(i<EdibleGhostLength)
			{
				EdibleGhost[i] = aux1[i];
			}
			if(j<notEdibleGhostLength)
			{
				notEdibleGhost[j] = aux2[j];
			}
		}


		// add the path from Ms Pac-Man to the nearest existing pill
		int nearest = game.getTarget(current, targetsArray, true, G.DM.PATH);

		// add the path that Ms Pac-Man is following
		GameView.addPoints(game, Color.GREEN, game.getPath(current, nearest));

		// show the distance to all the ghosts
		int[] ghostDistances = new int[] { game.getPathDistance(current, game.getCurGhostLoc(0)),
				game.getPathDistance(current, game.getCurGhostLoc(1)),
				game.getPathDistance(current, game.getCurGhostLoc(2)),
				game.getPathDistance(current, game.getCurGhostLoc(3)) };
		GameView.addText(0, 10, Color.YELLOW, "Ghost distances: " + ghostDistances[0] + ", " + ghostDistances[1] + ", "
				+ ghostDistances[2] + ", " + ghostDistances[3]);

		// add lines to the ghosts (if not in lair) - green if edible, red otherwise
		for (int i = 0; i < G.NUM_GHOSTS; i++)
			if (game.getLairTime(i) == 0)
				if (game.isEdible(i))
					GameView.addLines(game, Color.GREEN, current, game.getCurGhostLoc(i));
				else
					GameView.addLines(game, Color.RED, current, game.getCurGhostLoc(i));

		// Update States
		if (currentState == 0) {
			// check if power pill is eaten...
			if (activePowerPills.length < nPPeat) {
				storagePP = activePowerPills.length;
				nPPeat = storagePP;
				currentState = states[currentState][0];
			} else // ...and if ghosts in sight
			{
				boolean visible = false;
				for (int i = 0; i < G.NUM_GHOSTS && visible == false; ++i) {
					if (game.getPathDistance(current, ghostsTarg[i]) > 0
							&& game.getPathDistance(current, ghostsTarg[i]) <= VISIBLE_DISTANCE) {
						visible = true;
					}
				}
				if (visible) {
					currentState = states[currentState][3];
				}
			}
		} else {
			if (currentState == 1) // check if the nearest ghost is flashing
			{
				// We have to guess the index of the ghost with the minimal distance
				int NearestGhost = 0;
				if(EdibleGhostLength != 0){
				int DistanceGhost = game.getPathDistance(current, EdibleGhost[0]);
				for (int i = 0; i < EdibleGhostLength; ++i) {
					int j = game.getPathDistance(current, ghostsTarg[i]);
					if (j < DistanceGhost) {
						DistanceGhost = j;
						NearestGhost = i;
					}
				}
			}

				if (EdibleGhostLength == 0 || (game.getEdibleTime(NearestGhost) < TIME_FLASH && game.getEdibleTime(NearestGhost) > 0)) {
					currentState = states[currentState][1];
				}
			} else // check if ghosts are not visible anymore
			{
				//this loop is to check that we will only have in mind the nearest ghosts NOT edible
				boolean visible = false;
				
				//we will have in mind the distance to the nearest ghost NOT edible
				for (int i = 0; i < notEdibleGhost.length && visible == false; ++i) {
					if (game.getPathDistance(current, notEdibleGhost[i]) > 0
							&& game.getPathDistance(current, notEdibleGhost[i]) <= VISIBLE_DISTANCE) {
						visible = true;
					}
				}
				if (visible == false) {
					currentState = states[currentState][2];
				}
			}
		}

		// Update Targets and Direction of Pacman
		if (currentState == 0) // pacman will seek the pellets
		{
			pacman.set(game.getNextPacManDir(nearest, true, G.DM.PATH));

		} else {


			if (currentState == 1) // if chase, pacman will chase the closest ghost
			{
				nearest = game.getTarget(current, EdibleGhost, true, G.DM.PATH);
				pacman.set(game.getNextPacManDir(nearest, true, G.DM.PATH));

			} else // if evade, pacman will evade the nearest ghost
			{
				nearest = game.getTarget(current, notEdibleGhost, true, G.DM.PATH);
				pacman.set(game.getNextPacManDir(nearest, false, G.DM.PATH));
			}
		}

	}

	public static void main(String[] args) {
		while (true) {
			PacManSimulator.play(new FiniteStateMachine(), new GameGhosts(false));
			GameView.lastInstance.setVisible(false);
		}
	}
}
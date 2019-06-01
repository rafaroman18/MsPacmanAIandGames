package game.controllers.pacman;

import game.PacManSimulator;
import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.G;
import game.core.Game;
import game.controllers.pacman.trees.*;

/*
***********************************************************************
***************** MONTE CARLO TREE SEARCH strategy*********************
***************** 	  github.com/rafaroman18 	  *********************
***********************************************************************

In this case, we will implement the Monte Carlo Tree Search for the 
MsPacman. For the implementation we will use generic trees to make
the search easier.

Monte Carlo Search Tree has 3 stages:

1- Select. We see ehich node we must to expand.

2- Expansion. We expand that node

3- Simulation. We take random actions till a terminal state is reach

4- BackPropagation. 

The Heuristic Function will be ruled by this formula:

	Current SCORE of the game - 1.500 x (Lives Lost). 
		10.000 (if WIN) // -10.000 (if LOSE)

*/



public final class MonteCarlo extends PacManHijackController
{
	@Override
	public void tick(Game game, long timeDue) {

		int[] directions=game.getPossiblePacManDirs(true);		//set flag as true to include reversals		
		pacman.set(directions[G.rnd.nextInt(directions.length)]);
		
	}
	
	public static void main(String[] args) {
		PacManSimulator.play(new MonteCarlo(), new GameGhosts(false));
	}
}
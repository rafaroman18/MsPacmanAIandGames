package game.controllers.pacman;

import game.PacManSimulator;
import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.G;
import game.core.Game;
import game.controllers.pacman.trees.*;

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
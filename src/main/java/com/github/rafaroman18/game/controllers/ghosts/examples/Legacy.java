package com.github.rafaroman18.game.controllers.ghosts.examples;

import com.github.rafaroman18.game.controllers.ghosts.GhostsControllerBase;
import com.github.rafaroman18.game.core.G;
import com.github.rafaroman18.game.core.Game;
import com.github.rafaroman18.game.core.Game.DM;

public class Legacy extends GhostsControllerBase {
	public Legacy() {
		super(Game.NUM_GHOSTS);
	}

	@Override
	public void tick(Game game, long timeDue) {
		int[] directions = new int[Game.NUM_GHOSTS];
		DM[] dms = Game.DM.values();

		for (int i = 0; i < directions.length - 1; i++)
			if (game.ghostRequiresAction(i))
				directions[i] = game.getNextGhostDir(i, game.getCurPacManLoc(), true, dms[i]); // approach Ms Pac-Man
																								// using a different
																								// distance measure
																								// for each ghost; last
																								// ghost takes random
																								// action
		directions[3] = G.rnd.nextInt(4);

		input.set(directions);
	}

}
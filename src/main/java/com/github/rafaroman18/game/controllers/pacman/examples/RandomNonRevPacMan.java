package com.github.rafaroman18.game.controllers.pacman.examples;

import com.github.rafaroman18.game.PacManSimulator;
import com.github.rafaroman18.game.controllers.ghosts.game.GameGhosts;
import com.github.rafaroman18.game.controllers.pacman.PacManHijackController;
import com.github.rafaroman18.game.core.G;
import com.github.rafaroman18.game.core.Game;

public final class RandomNonRevPacMan extends PacManHijackController {
	@Override
	public void tick(Game game, long timeDue) {
		int[] directions = game.getPossiblePacManDirs(false); // set flag as false to prevent reversals
		pacman.set(directions[G.rnd.nextInt(directions.length)]);
	}

	public static void main(String[] args) {
		PacManSimulator.play(new RandomNonRevPacMan(), new GameGhosts(false));
	}
}
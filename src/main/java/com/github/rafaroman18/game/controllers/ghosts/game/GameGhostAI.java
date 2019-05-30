package com.github.rafaroman18.game.controllers.ghosts.game;

import com.github.rafaroman18.game.core.Game;

public interface GameGhostAI {

	public final int BLINKY = 0;
	public final int PINKY = 1;
	public final int CLYDE = 2;
	public final int INKY = 3;

	public int[] execute(int ghostType, Game game, long timeDue);
}

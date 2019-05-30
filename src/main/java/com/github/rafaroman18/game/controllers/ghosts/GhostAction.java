package com.github.rafaroman18.game.controllers.ghosts;

import com.github.rafaroman18.game.controllers.EntityAction;

public final class GhostAction extends EntityAction {

	public GhostAction clone() {
		GhostAction result = new GhostAction();

		result.direction = direction;

		return result;
	}

}

package com.github.rafaroman18.game.controllers.pacman.tournament.run;

import com.github.rafaroman18.game.controllers.pacman.tournament.PacManConfig;
import com.github.rafaroman18.game.controllers.pacman.IPacManController;

public class PacManRuns {
	
	public PacManConfig[] configs;
	
	public PacManRuns(PacManConfig[] configs) {
		this.configs = configs;
	}
	
	public synchronized PacManResults run(String pacManFQCN) {
		PacManResults results = new PacManResults();
		for (PacManConfig config : configs) {
			PacManRun run = new PacManRun(config);
			PacManRunResult result = run.run(pacManFQCN);
			results.addRunResults(result);
		}
		return results;
	}

}

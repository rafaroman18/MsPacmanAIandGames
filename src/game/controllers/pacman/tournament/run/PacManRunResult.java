package game.controllers.pacman.tournament.run;

import game.controllers.pacman.tournament.EvaluationInfos;
import game.controllers.pacman.tournament.PacManConfig;


public class PacManRunResult extends EvaluationInfos {
	
	private PacManConfig config;	
		
	public PacManRunResult(PacManConfig config) {
		this.config = config;
	}
	
	public PacManConfig getConfig() {
		return config;
	}
	
	public String getCSVHeader() {
		return super.getCSVHeader() + ";" + config.getCSVHeader();
	}
	
	public String getCSV() {
		return super.getCSV() + ";" + config.getCSV();		
	}
	
}

import game.controllers.pacman.FiniteStateMachine;
import game.controllers.pacman.examples.NearestPillPacManVS;
import game.controllers.pacman.examples.RandomPacMan;
import game.controllers.pacman.tournament.EvaluateAgentConsole;
import game.core.Replay;

import java.io.File;

import cz.cuni.mff.amis.pacman.tournament.EvaluationInfos;


public class running
{
    public static void main(String[] args)
    {
        
        FiniteStateMachine.main(args);
        
        //File replay = new File("replay.log");
        //Replay R = new Replay(replay);
    }
}
import game.SimulatorConfig;
import game.controllers.pacman.FiniteStateMachine;
import game.controllers.pacman.examples.NearestPillPacManVS;
import game.controllers.pacman.examples.RandomPacMan;
import game.core.Replay.ReplayMsPacman;

import java.io.File;

import game.controllers.pacman.tournament.EvaluateAgent;
import game.controllers.pacman.tournament.EvaluationInfos;


public class running
{
    public static void main(String[] args)
    {
        
        FiniteStateMachine.main(args);

        //SimulatorConfig config = new SimulatorConfig();
        //File file = new File("C:/Users/Rafa/Desktop/AI_Games/MsPacman AI and Games/src/replay.log");
        
        //File replay = new File("replay.log");
        //Replay R = new Replay(replay);

    }
}
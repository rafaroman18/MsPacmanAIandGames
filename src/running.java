import game.controllers.pacman.FiniteStateMachine;
import game.controllers.pacman.examples.NearestPillPacManVS;
import game.controllers.pacman.examples.RandomPacMan;
import game.core.Replay;

import java.io.IOException;
import java.io.File;



public class running
{
    public static void main(String[] args) throws IOException
    {
        FiniteStateMachine.main(args);
        
        //File replay = new File("replay.log");
        //Replay R = new Replay(replay);
    }
}
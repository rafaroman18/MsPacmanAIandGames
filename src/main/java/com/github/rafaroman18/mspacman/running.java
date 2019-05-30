package com.github.rafaroman18.mspacman;
 
import com.github.rafaroman18.game.controllers.pacman.FiniteStateMachine;
import com.github.rafaroman18.game.controllers.pacman.tournament.EvaluateAgentConsole;
import com.martiansoftware.jsap.JSAPException;

public class running {
    public static void main(String[] args) throws JSAPException {

        //FiniteStateMachine.main(args);
        EvaluateAgentConsole.main(args);
        // File replay = new File("replay.log");
        // Replay R = new Replay(replay);
    }
}

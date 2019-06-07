package game.controllers.pacman.montecarlo;

import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.G;

//Simulate random actions until the Depth variable is reached out or a final state is get
//Lets add a Maximum Depth variable. So the tree will be exploring "Depth" future actions (or if a terminal state is get)
public class Simulation extends PacManHijackController{

    public int Depth;
    public GameMC game;
    GameGhosts ghosts;

    private int CurrentDepth = 0;

    public Simulation(GameMC game_,int Depth_)
    {
        game = game_;
        Depth = Depth_;
        ghosts = new GameGhosts(true);
        ghosts.reset(game.game);

    }

    public int simulate() {
        while (CurrentDepth < Depth && !game.game.gameOver()
                && (game.game.getNumActivePills() + game.game.getNumActivePowerPills()) != 0) {
        
            //Posible directions of the pacman in the game (and selects a random)
            int[] directions = game.game.getPossiblePacManDirs(true);
            pacman.set(directions[G.rnd.nextInt(directions.length)]);
            //Now we must put the directions of the ghost
                
            //Update the current game state
            game.game.advanceGame(pacman, ghosts.getActions());
            
            CurrentDepth++;
        }

        HeuristicFunction H = new HeuristicFunction(game.game);
        return H.Heuristic();
    }

}

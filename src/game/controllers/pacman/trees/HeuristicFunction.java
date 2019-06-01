package game.controllers.pacman.trees;
import game.core.Game;


public class HeuristicFunction{

    private Game game;

    HeuristicFunction(Game g)
    {
        game = g;
    }

    public int Heuristic() 
    {
        int H = 0;
        H = game.getScore(); // SCORE
        H = H + 1500 * (Game.NUM_LIVES - game.getLivesRemaining()); //1500 * Lives Lost
        if( (game.getNumberPills() + game.getNumberPowerPills()) == 0) //If WIN
        {
            H = 10000;
        }
        if(game.gameOver())
        {
            H = -10000;
        }
        return H;
    }
}
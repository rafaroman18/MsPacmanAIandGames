package game.controllers.pacman.montecarlo;

import game.core.Game;

public class GameMC
{
    
    public int n; //Number of times the node has been visited
    public int H; //Heuristic Value 
    public Game game; //The actual state of the game

    public GameMC(Game game_)
    {
        game = game_;
        n = 0;
        H = 0;
    }
}
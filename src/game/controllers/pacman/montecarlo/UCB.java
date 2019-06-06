package game.controllers.pacman.montecarlo;

import game.core.Game;

public class UCB
{
    //H is the average of heuristic value of all nodes beneath this node
    //C is the exploration constant
    //n is the number of times the child node has been visited
    //N is the number of times the parent node has been visited

    // UCB = H + 2C * sqrt(2ln(N)/n)
    public static double UCBvalue(int H, double C, int n, int N)
    {
        double UCB = 0;
        UCB = H + 2 * C * Math.sqrt((2*Math.log(N))/n);
        return UCB;
    }
}
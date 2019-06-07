package game.controllers.pacman.montecarlo;

import game.controllers.pacman.montecarlo.*;
import game.controllers.pacman.trees.*;

import game.PacManSimulator;
import game.controllers.ghosts.GhostAction;
import game.controllers.ghosts.GhostsActions;
import game.controllers.ghosts.game.GameGhosts;
import game.controllers.pacman.PacManHijackController;
import game.core.G;
import game.core.Game;
import sun.util.resources.cldr.el.CalendarData_el_GR;

import java.util.*;

import com.sun.org.apache.xml.internal.utils.IntVector;

public final class MonteCarlo extends PacManHijackController {
    private boolean f_iteration = true;
    private GameMC g;
    private TreeNode<GameMC> gRoot;
    private TreeNode<GameMC> CurrentNode;

    @Override
    public void tick(Game game, long timeDue) {

        int[] directions = game.getPossiblePacManDirs(true);
        // In the first iteration, we store the game state that will be the root node
        // (the parent) and add his children
        if (f_iteration) {

            g = new GameMC(game);
            gRoot = new TreeNode<GameMC>(g);
            CurrentNode = gRoot;
            f_iteration = false;

            GameGhosts ghs = new GameGhosts();
            ghs.reset(game);
            for (int i = 0; i < directions.length; ++i) {
                pacman.set(directions[i]);
                Game cpy = game.copy();
                cpy.advanceGame(pacman, ghs.getActions());
                CurrentNode.addChild(new GameMC(cpy));
            }

        }

        if(CurrentNode.isLeaf())
        {
            if(CurrentNode.data.n == 0)
            {
                
            }
        }
        else
        {
            
        }





        int i = 0; 
        Vector<Double> UCBvalues = new Vector<Double>();
        while(i < CurrentNode.children.size())
        {
            if(CurrentNode.children.get(i).isLeaf())
            {
               if(CurrentNode.children.get(i).data.n == 0)
                {
                    HeuristicFunction H = new HeuristicFunction(CurrentNode.children.get(i).data.game);
                    UCBvalues.addElement(UCB.value(H.Heuristic(),(1/Math.sqrt(2)),CurrentNode.children.get(i).data.n,CurrentNode.children.get(i).parent.data.n));
                }

            }
        }



        CurrentNode = gRoot;
        pacman.set(directions[G.rnd.nextInt(directions.length)]);
    }

    public static void main(String[] args) {
        PacManSimulator.play(new MonteCarlo(), new GameGhosts(false));
    }
}
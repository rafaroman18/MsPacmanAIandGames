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
    private GameMC g;
    private TreeNode<GameMC> gRoot;
    private TreeNode<GameMC> CurrentNode;

    public int DEPTH = 150;
    public double C = 1/Math.sqrt(2);

    @Override
    public void tick(Game game, long timeDue) {

        int repetitions = 0;
        int[] directions = game.getPossiblePacManDirs(true);

        //Every Time we enter this function, we create a tree with the root being the actual game state
        g = new GameMC(game);
        gRoot = new TreeNode<GameMC>(g);
        CurrentNode = gRoot;

        GameGhosts ghs = new GameGhosts();
        ghs.reset(game);
        for (int i = 0; i < directions.length; ++i) {
            pacman.set(directions[i]);
            Game cpy = game.copy();
            cpy.advanceGame(pacman, ghs.getActions());
            CurrentNode.addChild(new GameMC(cpy));
        }

        //We will search over the tree until the DEPTH is reached out
        while (repetitions < DEPTH) {

            if (!CurrentNode.isLeaf()) { // If CurrentNode is not Leaf, we search for a LeafNode children which maximises the UCB
                int Nchildren = 0;       //This process will be done until we get a LeafNode
                double UCBtemp = Double.NEGATIVE_INFINITY;
                int childrenchosen = 0;

                while (!CurrentNode.isLeaf()) {
                    Nchildren = 0;
                    UCBtemp = Double.NEGATIVE_INFINITY;
                    childrenchosen = 0;
                    while (Nchildren < CurrentNode.children.size()) { //We search the children which maximise the UCB
                        HeuristicFunction H = new HeuristicFunction(CurrentNode.children.get(Nchildren).data.game);
                        double UCBCurrent = UCB.value(H.Heuristic(), C,
                                CurrentNode.children.get(Nchildren).data.n, CurrentNode.data.n);
                        if (UCBCurrent > UCBtemp) {
                            childrenchosen = Nchildren;
                            UCBtemp = UCBCurrent;
                        }
                        Nchildren++;
                    }

                    CurrentNode = CurrentNode.children.get(childrenchosen);

                }

                //Once the leaf node is selected, we see which action we should take

                if (CurrentNode.data.n == 0) { //If node haven't been visited, we rollout

                    Simulation S = new Simulation(CurrentNode.data,DEPTH);
                    int Simulated = S.simulate();
                    
                    while(!CurrentNode.isRoot()) //We "back propagate" the value from parents to the root
                    {
                        CurrentNode.data.n++;
                        CurrentNode.data.H = CurrentNode.data.H + Simulated;
                        CurrentNode = CurrentNode.parent;
                    }


                } else { //If node has been visited, we expand it

                    int[] possibleDirections = CurrentNode.data.game.getPossiblePacManDirs(true);
                    GameGhosts ghs_ = new GameGhosts();
                    ghs_.reset(CurrentNode.data.game);

                    for(int i = 0;i < possibleDirections.length;++i)
                    {
                        pacman.set(possibleDirections[i]);
                        Game cpy = CurrentNode.data.game.copy();
                        cpy.advanceGame(pacman, ghs.getActions());
                        CurrentNode.addChild(new GameMC(cpy));
                    }

                    CurrentNode = CurrentNode.children.get(0);
                }
            }

            repetitions++;

        }

        //At the end, we calculate the best UCB, and we take the direction with the higher
        CurrentNode = gRoot;
        double UCBtemp = Double.NEGATIVE_INFINITY;
        int childrenchosen = 0;

        for (int i = 0; i < directions.length; ++i) {
            HeuristicFunction H = new HeuristicFunction(CurrentNode.children.get(i).data.game);
                        double UCBCurrent = UCB.value(H.Heuristic(), C,
                                CurrentNode.children.get(i).data.n, CurrentNode.data.n);
                        if (UCBCurrent > UCBtemp) {
                            childrenchosen = i;
                            UCBtemp = UCBCurrent;
                        }
        }

        pacman.set(directions[childrenchosen]);
    }

    public static void main(String[] args) {
        PacManSimulator.play(new MonteCarlo(), new GameGhosts(false));
    }
}
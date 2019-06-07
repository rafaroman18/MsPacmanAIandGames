import java.util.ArrayList;
import java.util.Vector;

import game.controllers.pacman.FSM.*;
import game.controllers.pacman.trees.*;

public class running
{
    public static void main(String[] args)
    {
        //FiniteStateMachine.main(args);
        TreeNode<String> root = new TreeNode<String>("1");
        TreeNode<String> dos = root.addChild("2");
        TreeNode<String> tres = root.addChild("3");

        dos.addChild("4");
        dos.addChild("5");

        tres.addChild("6");
        tres.addChild("7");

        ArrayList<Integer> v = new ArrayList<Integer>();
        v.ad

        System.out.println(root.children.get(1));
        System.out.println(root.children.get(1).isLeaf());
        

    }
}
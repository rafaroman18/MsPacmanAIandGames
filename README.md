# Ms Pacman AI and Games
This repository is oriented to college studients and only for knowledge purposes. In this respository we will work under *MsPacman, (Namco.-1981)* running some Artificial Intelligents methods such as **search trees**, **neuronal network** or **automatas**.


This repository is based (and is where the information is extracted) on the book ***Artificial Intelligence and Games*** by *Georgios N. Yannakakis* and *Julian Togelius*: http://gameaibook.org/


Also, using the **MsPacman Framework** by *kefki*: https://github.com/kefik/MsPacMan-vs-Ghosts-AI

## 1. Finite State Machine 
The FSM used in this first method is the following.

![Finite State Machine used in Pacman](images_met/FSM.jpg)

The initial state will always be *Seek Pellets*, in this state, MsPacman will eat randomly the maximum pellets in the path.

Once a Power Pill is eaten, the automata goes to state *Chase Ghost* where MsPacman start to chase the ghost (because they are edible at that time).

Finally, the state *Evade Ghost* will be reached out when the ghosts are flashing (while we are in *Chase Ghost* state) and if any ghost is in sight if we are in *Seek Pellets* state. We can reach out this state too if we are in *Seek Pellets* and we have any ghost in sight.



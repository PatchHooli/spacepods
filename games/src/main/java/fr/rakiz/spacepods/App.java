package fr.rakiz.spacepods;

import fr.rakiz.spacepods.players.MyPlayer;
import fr.rakiz.spacepods.simulator.Game;

public class App {
    private static final int NB_PLAYERS = 1;
    private static final int NB_CHECKPOINTS = 4;
    private static final int WIDTH_MAX = 16000;
    private static final int HEIGHT_MAX = 16000;
    private static final int NB_POD_PER_PLAYER = 1;
    private static final int LAPS = 3;

    private App() {
    }

    public static void main(String[] args)  {

        Game game = new Game(WIDTH_MAX, HEIGHT_MAX, LAPS, NB_CHECKPOINTS, NB_POD_PER_PLAYER);
        for (int i = 0; i < NB_PLAYERS; i++) {
            game.addPlayer(new MyPlayer(i));
        }

        game.start();
    }
}

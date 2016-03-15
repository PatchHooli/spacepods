package fr.rakiz.spacepods.simulator;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import fr.rakiz.spacepods.players.Player;
import fr.rakiz.spacepods.simulator.world.PlayerChannels;
import fr.rakiz.spacepods.simulator.world.World;

public class Game {
    private final List<RunnablePlayer> m_players;
    private final World m_world;

    public Game(int widthMax, int heightMax, int laps, int nbCheckpoints, int nbPodPerPlayer) {
        m_world = new World(heightMax, widthMax, laps, nbCheckpoints, nbPodPerPlayer);
        m_players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        RunnablePlayer p = new RunnablePlayer(player);
        m_players.add(p);
        m_world.addPlayer(p);
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(m_players.size() + 1);
        m_players.forEach(executor::execute);     // start one thread per player
        executor.execute(m_world);                // and start the game on the last one
    }

    private static class RunnablePlayer implements Runnable, PlayerChannels {
        private final DataOutput m_game2player;
        private final DataInput m_player2game;
        private final DataInput m_player2log;
        private final Player m_player;

        private RunnablePlayer(Player player) {
            try {
                m_player = player;

                PipedInputStream in = new PipedInputStream();
                PipedOutputStream out = new PipedOutputStream();
                PipedOutputStream log = new PipedOutputStream();
                m_player.initCommunications(new DataInputStream(in), new DataOutputStream(out), new DataOutputStream(log));

                m_game2player = new DataOutputStream(new PipedOutputStream(in));
                m_player2game = new DataInputStream(new PipedInputStream(out));
                m_player2log = new DataInputStream(new PipedInputStream(log));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            m_player.loop();
        }

        @Override
        public DataOutput playerInput() {
            return m_game2player;
        }

        @Override
        public DataInput playerCommands() {
            return m_player2game;
        }

        @Override
        public DataInput playerLog() {
            return m_player2log;
        }
    }
}

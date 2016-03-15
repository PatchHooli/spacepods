package fr.rakiz.spacepods.simulator.world;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import fr.rakiz.spacepods.players.DataExchangeUtils.PodCommand;
import fr.rakiz.spacepods.simulator.math.Point2D;
import fr.rakiz.spacepods.simulator.math.Vector2D;

import static fr.rakiz.spacepods.simulator.world.DataExchangeUtils.*;

public class World implements Runnable {
    // TODO: add obstacles
    // TODO: add energy refill
    public static final int CHECKPOINT_SIZE = 600;
    public static final int POD_SIZE = 600;
    private static final double POD_MAX_ANGLE = Math.PI / 10;
    private static final long POD_MAX_SPEED = 200;
    private static final long POD_MASS = 100;
    private static final double FRICTION = 0.85;

    private final SecureRandom m_random;
    public final int heightMax;
    public final int widthMax;
    public final int laps;
    private final int m_nbPodPerPlayer;

    private final Checkpoint[] m_checkpoints;
    private final Map<PlayerChannels, List<Pod>> m_players;

    public World(int heightMax, int widthMax, int laps, int nbCheckpoints, int nbPodPerPlayer) {
        this.heightMax = heightMax;
        this.widthMax = widthMax;
        this.laps = laps;
        m_nbPodPerPlayer = nbPodPerPlayer;
        m_random = new SecureRandom();

        m_checkpoints = new Checkpoint[nbCheckpoints];
        initCheckpoints(m_checkpoints);

        m_players = new HashMap<>();
    }

    public void addPlayer(PlayerChannels player) {
        List<Pod> pods = new ArrayList<>();
        for (int j = 0; j < m_nbPodPerPlayer; j++) {
            double x = (POD_SIZE * 6) + (m_random.nextDouble() * (heightMax - (POD_SIZE * 66)));
            double y = (POD_SIZE * 6) + (m_random.nextDouble() * (widthMax - (POD_SIZE * 6)));

            pods.add(new Pod(new Point2D(x, y), new Vector2D(0, 0), POD_MAX_ANGLE, POD_MAX_SPEED, POD_MASS, POD_SIZE, FRICTION));
        }
        m_players.put(player, pods);
    }

    @Override
    public void run() {
        for (PlayerChannels player : m_players.keySet()) {
            DataOutput out = player.playerInput();

            writeWorldData(out, this);
            writeCheckpointsData(out, m_checkpoints);
        }

        while (true) {
            sendPodDetails();
            for (Map.Entry<PlayerChannels, List<Pod>> entry : m_players.entrySet()) {
                DataInput in = entry.getKey().playerCommands();
                for (Pod pod : entry.getValue()) {
                    PodCommand command = DataExchangeUtils.readPodCommand(in);
                    System.out.println("[Game] X:" + command.dirX + " Y:" + command.dirY + " A:" + command.acceleration);
                    System.out.println("[Game] "+pod.podInfos().toString());
                    pod.calculateNextFrame(new Point2D(command.dirX, command.dirY), command.acceleration);
                    System.out.println("[Game] "+pod.podInfos().toString());
                }
            }

            // TODO receive orders from each player
            // TODO calculate next frame for each pods
        }
    }

    private void initCheckpoints(Checkpoint[] nbCheckpoints) {
        for (int i = 0; i < nbCheckpoints.length; i++) {
            double x = (CHECKPOINT_SIZE * 2) + (m_random.nextDouble() * (heightMax - (CHECKPOINT_SIZE * 2)));
            double y = (CHECKPOINT_SIZE * 2) + (m_random.nextDouble() * (widthMax - (CHECKPOINT_SIZE * 2)));

            m_checkpoints[i] = new Checkpoint(x, y, CHECKPOINT_SIZE);
        }
    }

    private void sendPodDetails() {
        for (PlayerChannels player : m_players.keySet()) {
            // send pods details to everyone
            DataOutput out = player.playerInput();

            DataExchangeUtils.writelayerSize(out, m_players.size());
            writePodsData(out, m_players.get(player));            // first their own pod
            m_players.entrySet().stream()                           //
                    .filter(others -> others.getKey() != player)    //
                    .forEach(others -> {                            //
                        writePodsData(out, others.getValue());    // then other ones (beware! no order here right now)
                    });
        }
    }
}

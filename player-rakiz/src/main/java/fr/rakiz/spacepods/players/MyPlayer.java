package fr.rakiz.spacepods.players;

import java.io.*;
import fr.rakiz.spacepods.players.DataExchangeUtils.*;

import static fr.rakiz.spacepods.players.DataExchangeUtils.*;
import static java.lang.Thread.sleep;

public class MyPlayer implements Player {

    private final int m_id;
    private DataInput m_in;
    private DataOutput m_out;
    private DataOutput m_log;

    public MyPlayer(int id) {
        m_id = id;
    }

    @Override
    public void initCommunications(DataInput in, DataOutput out, DataOutput log) {
        m_in = in;
        m_out = out;
        m_log = log;
    }

    @Override
    public void loop() {
        WorldData worldData = readWorldData(m_in);
        System.out.println("[Player " + m_id + "] " + worldData.toString());

        CheckpointData[] checkpoints = readCheckpointsData(m_in);
        for (int i = 0; i < checkpoints.length; i++) {
            System.out.println("[Player " + m_id + "]." + i + " " + checkpoints[i].toString());
        }

        while (true) {
            int nbPlayer = readNbPlayers(m_in);

            PodData[] myPodsData = readPodsData(m_in);  // my pods
            for (int i = 0; i < myPodsData.length; i++) {
                PodData data = myPodsData[i];
                System.out.println("[Player " + m_id + "] [Pod " + m_id+"."+ i + "] " + data.toString());
            }

            for (int j = 1; j < nbPlayer; j++) {        // others pods
                PodData[] podData = readPodsData(m_in);
                for (int i = 0; i < podData.length; i++) {
                    PodData data = podData[i];
                    System.out.println("[Player " + m_id + "] [Pod " + j+"."+ i + "] " + data.toString());
                }
            }

            PodCommand[] commands = new PodCommand[myPodsData.length];
            for (int i = 0; i < commands.length; i++) {
                commands[i] = new PodCommand(0, 0, 100);    // commands for my pods
            }
            writePodsCommand(m_out, commands);

            // wait a bit
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


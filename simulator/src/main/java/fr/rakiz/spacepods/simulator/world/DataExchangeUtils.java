package fr.rakiz.spacepods.simulator.world;

import java.io.*;
import java.util.*;
import fr.rakiz.spacepods.players.DataExchangeUtils.PodCommand;

class DataExchangeUtils {
    private DataExchangeUtils() {
    }

    static void writeWorldData(DataOutput out, World world) {
        try {
            out.writeInt(world.widthMax);
            out.writeInt(world.heightMax);
            out.writeInt(world.laps);
        } catch (IOException e) {
            throw new RuntimeException(e);  // TODO handle exception
        }
    }

    static void writeCheckpointsData(DataOutput out, Checkpoint[] checkpoints) {
        try {
            out.writeInt(checkpoints.length);
            for (Checkpoint m_checkpoint : checkpoints) {
                out.writeInt(m_checkpoint.x());
                out.writeInt(m_checkpoint.y());
                out.writeInt(m_checkpoint.size());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);  // TODO handle exception
        }
    }

    static void writePodsData(DataOutput out, List<Pod> pods) {
        try {
            out.writeInt(pods.size());
            for (Pod pod : pods) {
                out.writeInt(pod.x());
                out.writeInt(pod.y());
                out.writeInt(pod.vx());
                out.writeInt(pod.vy());
                out.writeInt(pod.angle());
                out.writeInt(pod.nextCheckPointId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void writelayerSize(DataOutput out, int size) {
        try {
            out.writeInt(size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static PodCommand readPodCommand(DataInput in) {
        try {
            int dirX = in.readInt();
            int dirY = in.readInt();
            int acceleration = in.readInt();
            return new PodCommand(dirX, dirY, acceleration);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

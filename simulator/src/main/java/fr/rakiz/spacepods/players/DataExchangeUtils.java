package fr.rakiz.spacepods.players;

import java.io.*;

public class DataExchangeUtils {

    private DataExchangeUtils() {
    }

    public static WorldData readWorldData(DataInput in) {
        try {
            int widthMax = in.readInt();
            int heightMax = in.readInt();
            int laps = in.readInt();
            return new WorldData(widthMax, heightMax, laps);
        } catch (IOException e) {
            throw new RuntimeException(e);  // TODO handle exception
        }
    }

    public static int readNbPlayers(DataInput in) {
        try {
            int nbPlayer = in.readInt();
            return nbPlayer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static PodData[] readPodsData(DataInput in) {
        try {
            int nbPods = in.readInt();
            PodData[] podData = new PodData[nbPods];
            for (int i = 0; i < nbPods; i++) {
                int x = in.readInt();
                int y = in.readInt();
                int vx = in.readInt();
                int vy = in.readInt();
                int angle = in.readInt();
                int nextCheckPointId = in.readInt();
                podData[i] = new PodData(x, y, vx, vy, angle, nextCheckPointId);
            }
            return podData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writePodsCommand(DataOutput out, PodCommand[] commands) {
        try {
            for (PodCommand command : commands) {
                out.writeInt(command.dirX);
                out.writeInt(command.dirY);
                out.writeInt(command.acceleration);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CheckpointData[] readCheckpointsData(DataInput in) {
        try {
            int checkpointCount = in.readInt();
            CheckpointData[] cd = new CheckpointData[checkpointCount];
            for (int i = 0; i < checkpointCount; i++) {
                int x = in.readInt();
                int y = in.readInt();
                int size = in.readInt();
                cd[i] = new CheckpointData(x, y, size);
            }
            return cd;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // stuctures ...
    public static class WorldData {
        final int widthMax;
        final int heightMax;
        final int laps;

        private WorldData(int widthMax, int heightMax, int laps) {
            this.widthMax = widthMax;
            this.heightMax = heightMax;
            this.laps = laps;
        }

        @Override
        public String toString() {
            return "WorldData{" +
                    "widthMax=" + widthMax +
                    ", heightMax=" + heightMax +
                    ", laps=" + laps +
                    '}';
        }
    }

    public static class CheckpointData {
        public final int x;
        public final int y;
        public final int size;

        private CheckpointData(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        @Override
        public String toString() {
            return "CheckpointData{" +
                    "x=" + x +
                    ", y=" + y +
                    ", size=" + size +
                    '}';
        }
    }

    public static class PodData {
        public final int x;
        public final int y;
        public final int vx;
        public final int vy;
        public final int angle;

        public final int nextCheckPointId;

        private PodData(int x, int y, int vx, int vy, int angle, int nextCheckPointId) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.angle = angle;
            this.nextCheckPointId = nextCheckPointId;
        }

        @Override
        public String toString() {
            return "PodData{" +
                    "x=" + x +
                    ", y=" + y +
                    ", vx=" + vx +
                    ", vy=" + vy +
                    ", angle=" + angle +
                    ", nextCheckPointId=" + nextCheckPointId +
                    '}';
        }
    }

    public static class PodCommand {
        public final int dirX;
        public final int dirY;
        public final int acceleration;

        public PodCommand(int dirX, int dirY, int acceleration) {
            this.dirX = dirX;
            this.dirY = dirY;
            this.acceleration = acceleration;
        }

        @Override
        public String toString() {
            return "PodCommand{" +
                    "dirX=" + dirX +
                    ", dirY=" + dirY +
                    ", acceleration=" + acceleration +
                    '}';
        }
    }
}

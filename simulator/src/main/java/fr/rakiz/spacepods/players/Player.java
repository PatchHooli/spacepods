package fr.rakiz.spacepods.players;

import java.io.*;

public interface Player {
    void initCommunications(DataInput in, DataOutput out, DataOutput log);

    void loop();
}

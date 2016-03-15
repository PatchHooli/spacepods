package fr.rakiz.spacepods.simulator.world;

import java.io.*;

public interface PlayerChannels {
    DataOutput playerInput();
    DataInput playerCommands();
    DataInput playerLog();
}

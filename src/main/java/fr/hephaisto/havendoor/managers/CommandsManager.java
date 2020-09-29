package fr.hephaisto.havendoor.managers;


import fr.hephaisto.havendoor.HavenDoor;
import fr.hephaisto.havendoor.commands.CommandSellDoors;
import fr.hephaisto.havendoor.commands.CommandSetDoor;

public class CommandsManager {
    public static void register(HavenDoor instance) {
        instance.getCommand("sellalldoor").setExecutor(new CommandSellDoors());
        instance.getCommand("setdoor").setExecutor(new CommandSetDoor());
    }
}

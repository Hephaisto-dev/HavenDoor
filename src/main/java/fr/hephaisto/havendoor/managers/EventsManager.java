package fr.hephaisto.havendoor.managers;

import fr.hephaisto.havendoor.HavenDoor;
import fr.hephaisto.havendoor.listeners.PlayersEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {
    public static void register(HavenDoor instance) {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayersEvent(), instance);
    }
}

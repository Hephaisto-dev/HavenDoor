package fr.hephaisto.havendoor;

import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.plugin.java.JavaPlugin;

public final class HavenDoor extends JavaPlugin {
    Managers managers = new Managers();
    @Override
    public void onEnable() {
        managers.load(this);
    }

    @Override
    public void onDisable() {
        managers.unload();
    }
}

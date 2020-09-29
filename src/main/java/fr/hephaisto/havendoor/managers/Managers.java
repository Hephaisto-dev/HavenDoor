package fr.hephaisto.havendoor.managers;

import fr.hephaisto.havendoor.HavenDoor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.*;

public class Managers {
    private static HavenDoor instance;
    private static Managers managers;
    private Map<Location, Player> doors;
    private Map<Location, Boolean> signs;

    public void load(HavenDoor instance) {
        instance = instance;
        managers = this;
        doors = new HashMap<>();
        signs = new HashMap<>();

        loadDoors();
        loadSigns();


        instance.saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage("§aLe plugin Haven Door se lance !");

        CommandsManager.register(instance);
        EventsManager.register(instance);

    }

    public void unload() {
        Bukkit.getConsoleSender().sendMessage("§cLe plugin Haven Door se ferme !");
    }

    public static HavenDoor getInstance(){
        return instance;
    }

    public static Managers getManagers(){
        return managers;
    }

    public void loadDoors(){
        List<Location> locations = loadSignDoors("portes");
        for (Location location : locations){
            doors.put(location,null);
        }
    }

    public void loadSigns(){
        List<Location> locations = loadSignDoors("panneaux");
        for (Location location : locations){
            signs.put(location,true);
        }
    }

    private List<Location> loadSignDoors(String path){
        List<Location> locations = new ArrayList<>();
        for(int i = 0; i <= 100; i++) {
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") == 0) {
                double X = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".X");
                double Y = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Y");
                double Z = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Z");
                String world = instance.getConfig().getString(path + ".coordonnees_" + i + ".Monde");
                World world1 = Bukkit.getWorld(world);
                Location location = new Location(world1,X,Y,Z);
                locations.add(location);
            }
        }
        return locations;
    }

    public Map<Location, Player> getDoors() {
        return doors;
    }

    public Map<Location, boolean> getSigns() {
        return signs;
    }

    public void addDoorsSign(Player player,String path,Location loc) {
        for(int i = 0; i <= 100; i++){
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") == 0){
                instance.getConfig().set(path+".coordonnees_" + i + ".X", loc.getX());
                instance.getConfig().set(path+".coordonnees_" + i + ".Y", loc.getY());
                instance.getConfig().set(path+".coordonnees_" + i + ".Z", loc.getZ());
                instance.getConfig().set(path+".coordonnees_" + i + ".Monde", loc.getWorld().getName());
                instance.getConfig().set(path+".coordonnees_" + i + ".Admin", player.getName());
                if (path.equals("portes")){
                    doors.add(loc, null);
                }
                if (path.equals("panneaux")){
                    signs.add(loc, true);
                }
                player.sendMessage("§aVotre nouveau "+path" a été enregistré avec succès !");
            }
        }
    }
}

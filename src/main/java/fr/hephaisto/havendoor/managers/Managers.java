package fr.hephaisto.havendoor.managers;

import fr.hephaisto.havendoor.Door;
import fr.hephaisto.havendoor.HavenDoor;
import fr.hephaisto.havendoor.Sign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Managers {
    private static HavenDoor instance;
    private static Managers managers;
    private List<Door> doors;
    private List<Sign> signs;

    public void load(HavenDoor instance) {
        Managers.instance = instance;
        managers = this;
        doors = new ArrayList<>();
        signs = new ArrayList<>();

        instance.saveDefaultConfig();

        loadDoors();
        loadSigns();

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
        for (Location location : locations) {
            Door door = new Door(location,locations.indexOf(location),null);
            doors.add(door);
        }
    }

    public void loadSigns(){
        List<Location> locations = loadSignDoors("panneaux");
        for (Location location : locations) {
            Sign sign = new Sign(location,locations.indexOf(location),null);
            signs.add(sign);
        }
    }

    private List<Location> loadSignDoors(String path){
        List<Location> locations = new ArrayList<>();
        for(int i = 0; i <= 100; i++) {
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") != 0) {
                double X = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".X");
                double Y = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Y");
                double Z = instance.getConfig().getDouble(path + ".coordonnees_" + i + ".Z");
                String world = instance.getConfig().getString(path + ".coordonnees_" + i + ".Monde");
                World world1 = Bukkit.getWorld("world");
                if (world != null) {
                    world1 = Bukkit.getWorld(world);
                }
                Location location = new Location(world1,X,Y,Z);
                locations.add(location);
            }
        }
        return locations;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public List<Sign> getSigns() {
        return signs;
    }

    public void addDoorsSign(Player player, String path, Location loc) {
        for(int i = 0; i <= 100; i++){
            if(instance.getConfig().getInt(path+".coordonnees_" + i + ".Y") == 0){
                instance.getConfig().set(path+".coordonnees_" + i + ".X", (int) loc.getX());
                instance.getConfig().set(path+".coordonnees_" + i + ".Y", (int) loc.getY());
                instance.getConfig().set(path+".coordonnees_" + i + ".Z", (int) loc.getZ());
                instance.getConfig().set(path+".coordonnees_" + i + ".Monde", player.getWorld().getName());
                instance.getConfig().set(path+".coordonnees_" + i + ".Admin", player.getName());
                instance.saveConfig();
                instance.reloadConfig();
                player.sendMessage("§aVotre nouveau "+path+" a été enregistré avec succès en! " + loc.toString());
                if (path.equals("portes")){
                    Door door = new Door(loc,i,null);
                    doors.add(door);
                }
                if(path.equals("panneaux")){
                    Sign sign = new Sign(loc,i,null);
                    signs.add(sign);
                }
                break;
            }
        }
    }

    public void deleteAllDoors(Player player){
        for (Sign sign : signs){
            if (sign.getOwner().equals(player.getUniqueId())){
                sign.setOwner(null);
            }
        }
        for (Door door : doors){
            if (door.getOwner().equals(player.getUniqueId())){
                door.setOwner(null);
            }
        }
    }

    public boolean isDoorOpenNotAllowed (Location location, Player player){
        for (Door door: doors){
            if (door.getLoc().equals(location) && door.getOwner().equals(player.getUniqueId())){
                return false;
            }
        }
        return false;
    }

    public boolean containLocation(Location location){
        for (Sign sign : signs){
            if (sign.getLoc()==location){
                return true;
            }
        }
        return false;
    }

    public boolean containPlayer(Player player){
        for (Sign sign : signs){
            if (sign.getOwner().equals(player.getUniqueId())){
                return true;
            }
        }
        return false;
    }

    public Sign getLocation(Location location){
        for (Sign sign : signs){
            if (sign.getLoc()==location){
                return sign;
            }
        }
        return null;
    }

    public Door getDoorById(int id){
        for (Door door: doors){
            if (door.getId() == id){
                return door;
            }
        }
        return null;
    }
}

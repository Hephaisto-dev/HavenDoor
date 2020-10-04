package fr.hephaisto.havendoor.managers;

import fr.hephaisto.havendoor.Door;
import fr.hephaisto.havendoor.HavenDoor;
import fr.hephaisto.havendoor.Sign;
import fr.hephaisto.havendoor.utils.Vault;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Managers {
    private static HavenDoor instance;
    private static Managers managers;
    private List<Door> doors;
    private List<Sign> signs;
    private Map<Player,Location> voting;

    public void load(HavenDoor instance) {
        Managers.instance = instance;
        managers = this;
        doors = new ArrayList<>();
        signs = new ArrayList<>();
        voting = new HashMap<>();

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
                double x = (int) loc.getX();
                double y = (int) loc.getY();
                double z = (int) loc.getZ();
                instance.getConfig().set(path+".coordonnees_" + i + ".X", x);
                instance.getConfig().set(path+".coordonnees_" + i + ".Y", y);
                instance.getConfig().set(path+".coordonnees_" + i + ".Z", z);
                instance.getConfig().set(path+".coordonnees_" + i + ".Monde", player.getWorld().getName());
                instance.getConfig().set(path+".coordonnees_" + i + ".Admin", player.getName());
                instance.saveConfig();
                instance.reloadConfig();
                player.sendMessage("§aVotre nouveau "+path+" a été enregistré avec succès en "+x+" "+y+""+z);
                if (path.equals("portes")){
                    Door door = new Door(new Location(loc.getWorld(),x,y,z),i,null);
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
            if (sign.getOwner()!=null && sign.getOwner().equals(player.getUniqueId())){
                sign.setOwner(null);
            }
        }
        for (Door door : doors){
            if (door.getOwner()!=null && door.getOwner().equals(player.getUniqueId())){
                door.setOwner(null);
            }
        }
    }

    public boolean isDoorOpenNotAllowed (Location location, Player player){
        if (!containDoorLocation(location)){
            return false;
        }
        for (Door door: doors){
            if (door.getOwner() != null && door.isOwner(player.getUniqueId()) && door.getLoc().getX() ==
                    location.getX() && door.getLoc().getZ() == location.getZ()) {
                if (door.getLoc().getY() == location.getY()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean containLocation(Location location){
        for (Sign sign : signs){
            if (sign.getLoc().getX() ==location.getX() && sign.getLoc().getY() == location.getY() && sign.getLoc().getZ() == location.getZ()){
                return true;
            }
        }
        return false;
    }

    public boolean containDoorLocation(Location location){
        for (Door door : doors){
            if (door.getLoc().getX() ==location.getX() && door.getLoc().getY() == location.getY() && door.getLoc().getZ() == location.getZ()){
                return true;
            }
        }
        return false;
    }

    public boolean containPlayer(Player player){
        for (Sign sign : signs){
            if (sign.getOwner() != null && sign.getOwner() == player.getUniqueId()){
                return true;
            }
        }
        return false;
    }

    public Sign getLocation(Location location){
        for (Sign sign : signs){
            if (sign.getLoc().getX() ==location.getX() && sign.getLoc().getY() == location.getY() && sign.getLoc().getZ() == location.getZ()){
                return sign;
            }
        }
        return null;
    }

    public Door getDoorByLocation(Location location){
        for (Door door : doors){
            if (door.getLoc().getX() ==location.getX() && door.getLoc().getY() == location.getY() && door.getLoc().getZ() == location.getZ()){
                return door;
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

    public Map<Player, Location> getVoting() {
        return voting;
    }

    public void buyDoor(Player player, Location location) {
        if (Vault.hasEnough(player,instance.getConfig().getInt("prixporte"))) {
            Vault.withdraw(player,instance.getConfig().getInt("prixporte"));
            getLocation(location).setOwner(player.getUniqueId());
            getDoorById(Managers.getManagers().getLocation(location).getId())
                    .setOwner(player.getUniqueId());
            getVoting().remove(player);
            player.sendMessage(ChatColor.GREEN + "Achat effectué avec succès");
        }
        else{
            player.sendMessage("Vous n'avez pas assez d'argent pour acheter cette porte");
        }
    }
}

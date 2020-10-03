package fr.hephaisto.havendoor;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.UUID;

public class Door {

    private Location loc;
    private int id;
    private UUID owner;

    public Door(Location location, int id, UUID owner) {
        this.loc = location;
        this.id = id;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public Location getLoc() {
        return loc;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public boolean isOwner(UUID owner){
        return this.getOwner().equals(owner);
    }

    public double getTop() {
        return this.loc.getY() + 1;
    }
}

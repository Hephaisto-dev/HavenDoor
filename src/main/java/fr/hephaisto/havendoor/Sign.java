package fr.hephaisto.havendoor;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class Sign {

    private Location loc;
    private int id;
    private UUID owner;

    public Sign(Location location, int id, UUID owner) {
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
}

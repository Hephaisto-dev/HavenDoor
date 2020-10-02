package fr.hephaisto.havendoor.listeners;
import fr.hephaisto.havendoor.Sign;
import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;


public class PlayersEvent implements Listener {
    @EventHandler
    public void onDoorOpen (PlayerInteractEvent e){
        if (e.getMaterial() == Material.OAK_DOOR && e.getAction()==Action.RIGHT_CLICK_BLOCK){
            Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
            if (Managers.getManagers().isDoorOpenNotAllowed(location,e.getPlayer())){
                e.getPlayer().sendMessage("Vous ne pouvez pas ouvrir cette porte");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignInteract (PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.OAK_WALL_SIGN || e.getClickedBlock().getType() == Material.OAK_SIGN) {
                Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
                if (Managers.getManagers().containLocation(location)) {
                    if (!Managers.getManagers().containPlayer(e.getPlayer())) {
                        Managers.getManagers().getLocation(location).setOwner(e.getPlayer().getUniqueId());
                        Managers.getManagers().getDoorById(Managers.getManagers().getLocation(location).getId()).setOwner(e.getPlayer().getUniqueId());
                        e.getPlayer().sendMessage("Achat effectué avec succès");
                    } else {
                        Managers.getManagers().getLocation(location).setOwner(null);
                        Managers.getManagers().getDoorById(Managers.getManagers().getLocation(location).getId()).setOwner(null);
                        e.getPlayer().sendMessage("Vente effectué avec succès");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDisconnect (PlayerQuitEvent e){
        Player player = e.getPlayer();
        Managers.getManagers().deleteAllDoors(player);
    }
}

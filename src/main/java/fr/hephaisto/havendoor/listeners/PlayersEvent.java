package fr.hephaisto.havendoor.listeners;
import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;


public class PlayersEvent implements Listener {
    @EventHandler
    public void onDoorOpen (PlayerInteractEvent e){
        if (e.getMaterial().equals(Material.OAK_DOOR) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
            if (Managers.getManagers().getDoors().containsKey(location) && !Managers.getManagers().getDoors().get(location).equals(e.getPlayer())){
                e.getPlayer().sendMessage("Vous ne pouvez pas ouvrir cette porte");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignInteract (PlayerInteractEvent e){
        if (e.getMaterial().equals(Material.OAK_SIGN) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
            if (Managers.getManagers().getSigns().containsKey(location)){
                int i = 0;
                while (Managers.getManagers().getSigns().keySet().toArray()[i] != location){
                    i++;
                }
                Location location1 = (Location) Managers.getManagers().getDoors().keySet().toArray()[i];
                if (Managers.getManagers().getSigns().get(location)){
                    Managers.getManagers().getSigns().replace(location,false);
                    Managers.getManagers().getDoors().replace(location1,e.getPlayer());
                }
                else if(Managers.getManagers().getDoors().containsValue(e.getPlayer())){
                    Managers.getManagers().getSigns().replace(location,true);
                    Managers.getManagers().getDoors().replace(location1,null);
                }
            }
        }
    }
}

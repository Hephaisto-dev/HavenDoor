package fr.hephaisto.havendoor.listeners;
import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayersEvent implements Listener {
    @EventHandler
    public void onDoorOpen (PlayerInteractEvent e){
        if (e.getMaterial().equals(Material.OAK_DOOR) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Location location = e.getClickedBlock().getLocation();
            if (Managers.getManagers().getDoors().containsKey(location) && !Managers.getManagers().getDoors().get(location).equals(e.getPlayer())){
                e.getPlayer().sendMessage("Vous ne pouvez pas ouvrir cette porte");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignInteract (PlayerInteractEvent e){
        if (e.getMaterial().equals(Material.OAK_SIGN) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Location location = e.getClickedBlock().getLocation();
            if (Managers.getManagers().getSigns().containsKey(location)){
                if (Managers.getManagers().getSigns().get(location)){
                }
            }
        }
    }
}

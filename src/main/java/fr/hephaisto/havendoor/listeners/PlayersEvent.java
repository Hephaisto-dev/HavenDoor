package fr.hephaisto.havendoor.listeners;
import fr.hephaisto.havendoor.managers.Managers;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.Objects;


public class PlayersEvent implements Listener {
    @EventHandler
    public void onDoorOpen (PlayerInteractEvent e){
        if (Objects.requireNonNull(e.getClickedBlock()).getType() == Material.OAK_DOOR && e.getAction()==Action.RIGHT_CLICK_BLOCK){
            Location location = e.getClickedBlock().getLocation();
            if (!Managers.getManagers().isDoorOpenNotAllowed(location,e.getPlayer())){
                e.getPlayer().sendMessage(ChatColor.RED + "Vous ne pouvez pas ouvrir cette porte");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSignInteract (PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.OAK_WALL_SIGN || e.getClickedBlock().getType() ==
                    Material.OAK_SIGN) {
                Managers m = Managers.getManagers();
                Location location = Objects.requireNonNull(e.getClickedBlock()).getLocation();
                if (m.containLocation(location)) {
                    e.getPlayer().sendMessage(ChatColor.GOLD + "Êtes-vous sûr? \nRépondez par 'oui' ou par 'non'");
                    m.getVoting().put(e.getPlayer(),location);
                }
            }
        }
    }

    @EventHandler
    public void onDisconnect (PlayerQuitEvent e){
        Player player = e.getPlayer();
        Managers.getManagers().deleteAllDoors(player);
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent e){
        if (Managers.getManagers().getVoting().containsKey(e.getPlayer())){
            Managers m = Managers.getManagers();
            Location location = Managers.getManagers().getVoting().get(e.getPlayer());
            String message = e.getMessage();
            if (message.contains("oui") || message.contains("Oui") || message.contains("OUI")){
                if (m.containPlayer(e.getPlayer())) {
                    m.getLocation(location).setOwner(null);
                    m.getDoorById(Managers.getManagers().getLocation(location).getId())
                            .setOwner(null);
                    m.getVoting().remove(e.getPlayer());
                    e.getPlayer().sendMessage(ChatColor.GREEN + "Vente effectué avec succès");
                } else if (!m.containPlayer(e.getPlayer())) {
                    m.getLocation(location).setOwner(e.getPlayer().getUniqueId());
                    m.getDoorById(Managers.getManagers().getLocation(location).getId())
                            .setOwner(e.getPlayer().getUniqueId());
                    m.getVoting().remove(e.getPlayer());
                    e.getPlayer().sendMessage(ChatColor.GREEN + "Achat effectué avec succès");
                }
                e.setCancelled(true);
            }
            else if (message.contains("non") || message.contains("Non") || message.contains("NON")){
                e.getPlayer().sendMessage(ChatColor.RED + "Opération annulée");
                m.getVoting().remove(e.getPlayer());
                e.setCancelled(true);
            }
        }
    }
}

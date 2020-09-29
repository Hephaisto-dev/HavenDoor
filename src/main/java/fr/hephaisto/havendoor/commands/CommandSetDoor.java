package fr.hephaisto.havendoor.commands;

import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetDoor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(player.isOp()){
                if(args.length != 0){
                    player.sendMessage("§aExecution: /setdoor ");
                    return false;
                }
                player.getLocation().getBlock().setType(Material.OAK_DOOR);
                Managers.getManagers().addDoorsSign(player,"portes",player.getLocation());
                
                return false;
            }else{
                player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
                return false;
            }
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande !");
        }
        return false;
    }
}

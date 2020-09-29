package fr.hephaisto.havendoor.commands;

import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
            if (player.isOp()){
                if(args.length != 3){
                    player.sendMessage("§aExecution: /setdoor x y z");
                    return false;
                }
                player.getLocation().getBlock().setType(Material.OAK_DOOR);
                Managers.getManagers().addDoorsSign(player,"portes",player.getLocation());
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                Managers.getManagers().addDoorsSign(player,"panneaux", new Location(player.getWorld(),x,y,z));
            }else{
                player.sendMessage("§cVous n'avez pas la permission d'executer cette commande !");
            } return false;
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande !");
        }
        return false;
    }
}

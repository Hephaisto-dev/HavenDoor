package fr.hephaisto.havendoor.commands;

import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSellDoors implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(args.length != 0){
                player.sendMessage("§aExecution: /sellalldoor ");
                return false;
            }
            Managers.getManagers().deleteAllDoors(player);
            return false;
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande !");
        }
        return false;
    }
}

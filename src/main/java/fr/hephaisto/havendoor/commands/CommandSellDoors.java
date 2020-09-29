package fr.hephaisto.havendoor.commands;

import fr.hephaisto.havendoor.managers.Managers;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandSellDoors implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(args.length != 0){
                player.sendMessage("§aExecution: /sellalldoor ");
                return false;
            }
            for (int i = 0; i < Managers.getManagers().getDoors().size(); i++){
                Location location = (Location) Managers.getManagers().getDoors().keySet().toArray()[i];
                Player player1 = Managers.getManagers().getDoors().get(location);
                if (player1.getName().equals(player.getName())){
                    Managers.getManagers().getDoors().replace(location,null);
                    Location location1 = (Location) Managers.getManagers().getSigns().keySet().toArray()[i];
                    Managers.getManagers().getSigns().replace(location1,true);
                }
            }
            return false;
        }else{
            sender.sendMessage("Vous devez être un joueur pour executer cette commande !");
        }
        return false;
    }
}

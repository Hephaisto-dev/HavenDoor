package fr.hephaisto.havendoor.utils;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {

    private final Economy econ;

    private Vault() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        econ = rsp.getProvider();
    }

    private static Economy getEconomy() {
        return VaultHolder.INSTANCE.econ;
    }

    private static class VaultHolder {

        private static final Vault INSTANCE = new Vault();
    }

    public static boolean hasEnough(Player player, double amount){
        return getEconomy().bankHas(player.getName(), amount).transactionSuccess();
    }

    public static void withdraw(Player p, double amount) {
        getEconomy().bankWithdraw(p.getName(), amount);
    }

}

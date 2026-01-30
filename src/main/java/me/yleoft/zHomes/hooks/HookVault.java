package me.yleoft.zHomes.hooks;

import me.yleoft.zAPI.hooks.HookInstance;
import me.yleoft.zHomes.configuration.languages.LanguageBuilder;
import me.yleoft.zHomes.zHomes;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HookVault implements HookInstance {

    private Economy economy;

    @Override
    public boolean exists() {
        return economy != null;
    }

    @Override
    public void load() {
        if (!Bukkit.getServer().getPluginManager().isPluginEnabled("Vault")) return;
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            zHomes.getInstance().getLoggerInstance().info("<yellow>Vault economy provider not found! Disabling Vault hook.");
            return;
        }
        economy = rsp.getProvider();
    }

    @Override
    public String message() {
        return "<yellow>Vault hooked successfully!";
    }

    public boolean canAfford(Player p, String commandPermission, double cost) {
        if(p.hasPermission(zHomes.getConfigYAML().getBypassCommandCostPermission(commandPermission))) {
            return true;
        }
        if (exists()) {
            if(economy.has(p, cost)) {
                economy.withdrawPlayer(p, cost);
                return true;
            }
            LanguageBuilder.sendMessage(p, zHomes.getLanguageYAML().getVaultCantAffordCommand());
            return false;
        }
        return true;
    }

}

package me.Pluto1099.GrimReaper;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class GrimReaper extends JavaPlugin implements Listener {
	String prefix = "[Reaper] ";
	public final Logger logger = Logger.getLogger("Minecraft");
	public static GrimReaper plugin;
	boolean deathkills = this.getConfig().getBoolean("DeathPay");
	int amt = this.getConfig().getInt("DeathPayAmt");
	public static Economy economy = null;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + "I will rise again!");
		this.saveDefaultConfig();
	}
	
	@Override
	public void onEnable () {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + "I have risen!");
		getServer().getPluginManager().registerEvents(this, this);
	    loadConf();
	    if (economy == null) {
	    	deathkills = false;
	    }
	        }
	private void loadConf() {	
		getConfig().addDefault("deathpay", false);
		getConfig().addDefault("deathpay.amount", 25);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		Player player = (Player) sender;
		if(commandLabel.equalsIgnoreCase("death")){
			if (args.length == 0) {
				player.sendMessage(ChatColor.GOLD + prefix + ChatColor.YELLOW + this.getConfig().getString("message"));
			    player.setHealth(0);
			    Bukkit.broadcastMessage(ChatColor.GOLD + prefix + ChatColor.YELLOW + "Reaper got Revenge on " + ChatColor.WHITE + player.getDisplayName());
			} else if (args.length == 1) {
				if (player.hasPermission("Death.kill.others")) {
					Player sdr = (Player)sender;
					Player p = Bukkit.getServer().getPlayer(args[0]);
					p.setHealth(0);
					p.chat("I've Been Killed by " + ChatColor.RED + sdr.getDisplayName() + "!");
				} else {
					sender.sendMessage(ChatColor.GOLD + prefix + ChatColor.RED + "No Permission!");
				}
			} else {
				sender.sendMessage(ChatColor.GOLD + prefix + ChatColor.RED + "Invalid Arguments!");
			}
		} return true;
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		String killed = e.getEntity().getName();
		String killer = e.getEntity().getKiller().getName();
		Player award = Bukkit.getServer().getPlayer(killer);
		if (deathkills) {
			award.sendMessage(ChatColor.GRAY + prefix + ChatColor.WHITE + "You have been awarded " + ChatColor.GREEN + amt + ChatColor.WHITE + " For killing " + killed);
			economy.depositPlayer(killer, amt);
		}
	}
	
    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}

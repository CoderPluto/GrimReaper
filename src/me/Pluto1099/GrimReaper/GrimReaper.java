package me.Pluto1099.GrimReaper;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class GrimReaper extends JavaPlugin {
	String prefix = "[Reaper] ";
	public final Logger logger = Logger.getLogger("Minecraft");
	public static GrimReaper plugin;
	
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
	
}

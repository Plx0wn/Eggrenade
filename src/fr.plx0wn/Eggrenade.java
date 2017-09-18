package fr.lucluc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Eggrenade extends JavaPlugin implements Listener {

	public static Plugin instance;

	public void onEnable() {
		instance = this;
		Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public static void sm(Player player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("eggrenade")) {
			if (sender.hasPermission("eggrenade.commands")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Try /eggrenade reload");
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						reloadConfig();
						sender.sendMessage(ChatColor.GREEN + "Eggrenade reloaded!");
					} else {
						sender.sendMessage(ChatColor.GREEN + "Unknow command. Try /eggrenade reload");
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have the permissions.");
		}
		return false;
	}

}

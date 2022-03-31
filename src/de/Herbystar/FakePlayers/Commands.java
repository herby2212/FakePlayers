package de.Herbystar.FakePlayers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {				
		Player p = null;
		if(sender instanceof Player) {
			p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("fakePlayers")) {
				if(Main.instance.protocolLib == true) {
					p.sendMessage(Main.instance.internalPrefix + "§aProtocolLib§e§l Mode! §c§lCommands are not supported in this mode!");
					return true;
				}
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("create")) {
						if(p.hasPermission("FakePlayers.Commands")) {
							Main.instance.playerListHandler.addCustomOnlinePlayer(args[1]);
							p.sendMessage(Main.instance.internalPrefix + "§aPlayer successful created!");
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("remove")) {
						if(p.hasPermission("FakePlayers.Commands")) {
							Main.instance.playerListHandler.removeCustomOnlinePlayer(args[1]);
							p.sendMessage(Main.instance.internalPrefix + "§aPlayer successful removed!");
							return true;
						}
					}
			
				}
			}	
		} else {
			if(cmd.getName().equalsIgnoreCase("fakePlayers")) {
				if(Main.instance.protocolLib == true) {
					Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§aProtocolLib§e§l Mode! §c§lCommands are not supported in this mode!");
					return true;
				}
				if(args.length == 2) {
					if(args[0].equalsIgnoreCase("create")) {
						Main.instance.playerListHandler.addCustomOnlinePlayer(args[1]);
						Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§aPlayer successful created!");
						return true;
					}
					if(args[0].equalsIgnoreCase("remove")) {
						Main.instance.playerListHandler.removeCustomOnlinePlayer(args[1]);
						Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§aPlayer successful removed!");
						return true;
					}
				}
			}
		}
		return false;
	}

}

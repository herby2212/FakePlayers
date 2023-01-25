package de.Herbystar.FakePlayers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("fakeplayers")) {
			if(sender instanceof Player) {
				List<String> recommandation = new ArrayList<String>();
				if(args.length == 1) {
					recommandation.add("create");
					recommandation.add("remove");
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("remove"))) {
					recommandation.add("<fakePlayerName>");
				}
				return recommandation;
			}
		}
		return null;
	}
}

package de.Herbystar.FakePlayers.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.PlayerListHandler.NMS_PlayerListHandler;

public class PlayerLoginEventHandler implements Listener {
	
	@EventHandler
	public void onLoginEvent(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		
		int maxPlayers = Bukkit.getServer().getMaxPlayers();
		int realPlayers = Bukkit.getOnlinePlayers().size() - Main.instance.fakePlayersCount;
		if(realPlayers < maxPlayers) {
			e.allow();
		}
		
		//Remove faked player if the real one with the name joins the server
		if(NMS_PlayerListHandler.customPlayers.containsKey(p.getName())) {
			Main.instance.playerListHandler.removeCustomOnlinePlayer(p.getName());
		}
		
	}

}

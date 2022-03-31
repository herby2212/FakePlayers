package de.Herbystar.FakePlayers.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.Herbystar.FakePlayers.Main;

public class PlayerLoginEventHandler implements Listener {
	
	@EventHandler
	public void onLoginEvent(PlayerLoginEvent e) {
		int maxPlayers = Bukkit.getServer().getMaxPlayers();
		int realPlayers = Bukkit.getOnlinePlayers().size() - Main.instance.fakePlayersCount;
		if(realPlayers < maxPlayers) {
			e.allow();
		}
	}

}

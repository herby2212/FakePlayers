package de.Herbystar.FakePlayers.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Herbystar.FakePlayers.Main;

public class PlayerQuitEventHandler implements Listener {
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		if(Main.instance.balancePlayerCount == false) {
			return;
		}
		if(Main.instance.advancedFakedPlayersEnabled == false) {
			Main.instance.playerListHandler.setOnlinePlayers(Main.instance.fakePlayersCount + 1);
		} else {
			Main.instance.playerListHandler.addCustomOnlinePlayer(player.getName());
		}
	}

}

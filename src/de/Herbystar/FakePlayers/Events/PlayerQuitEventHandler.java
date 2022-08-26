package de.Herbystar.FakePlayers.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.PlayerListHandler.NMS_PlayerListHandler;

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
			for(String name : Main.instance.advancedFakedPlayersNames) {
				if(!NMS_PlayerListHandler.customPlayers.keySet().contains(name)) {
					Main.instance.playerListHandler.addCustomOnlinePlayer(name);
					break;
				}
			}
		}
	}

}

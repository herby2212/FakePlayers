package de.Herbystar.FakePlayers.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.PlayerListHandler.NMS_PlayerListHandler;

public class PlayerJoinEventHandler implements Listener {
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();		
		
		if(Main.instance.balancePlayerCount == false) {
			return;
		}
		if(Main.instance.advancedFakedPlayersEnabled == false) {
			Main.instance.playerListHandler.setOnlinePlayers(Main.instance.fakePlayersCount - 1);
		} else {
			int i = 0;
			for(String name : NMS_PlayerListHandler.customPlayers.keySet()) {
				if(i == NMS_PlayerListHandler.customPlayers.size() - 1) {
					Main.instance.playerListHandler.removeCustomOnlinePlayer(name);
					break;
				}
				i++;
			}
		}
	}

}

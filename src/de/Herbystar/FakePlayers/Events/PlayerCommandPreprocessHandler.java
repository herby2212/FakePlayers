package de.Herbystar.FakePlayers.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.PlayerListHandler.NMS_PlayerListHandler;

public class PlayerCommandPreprocessHandler implements Listener {
	
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if(Main.instance.advancedFakedPlayersEnabled == true && Main.instance.blockCommandsToFakePlayer == true) {
			for(String name : NMS_PlayerListHandler.customPlayers.keySet()) {
				if(e.getMessage().toLowerCase().contains(name.toLowerCase())) {
					e.setCancelled(true);
				}
			}	
		}
	}

}

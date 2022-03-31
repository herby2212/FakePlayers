package de.Herbystar.FakePlayers;

import org.bukkit.Bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler;

public class ProtocolLibMode implements PlayerListHandler {
	
	@SuppressWarnings("deprecation")
	public void setOnlinePlayers(int amount) {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PacketAdapter.params(Main.instance, new PacketType[] {PacketType.Status.Server.OUT_SERVER_INFO}).optionAsync()) {
			public void onPacketSending(PacketEvent e) {
				WrappedServerPing p = e.getPacket().getServerPings().read(0);
				p.setPlayersOnline(amount);
			}
		});
	}
	
	public void removeOnlinePlayers() {
		
	}
	
	public void addCustomOnlinePlayer(String name) {
//		p.getPlayers().add(new WrappedGameProfile(RandomUUID.randomUUID(), ""));
		Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§aProtocolLib§e§l Mode! §c§lCommands are not supported in this mode!");
	}
	
	public void removeCustomOnlinePlayer(String name) {
		Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§aProtocolLib§e§l Mode! §c§lCommands are not supported in this mode!");
	}

	@Override
	public void removeCustomOnlinePlayers() {
		
	}

}

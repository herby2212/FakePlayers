package de.Herbystar.FakePlayers.CustomClient;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.network.PlayerConnection;

public class CustomClient_1_18_R1 extends PlayerConnection {
    public CustomClient_1_18_R1(MinecraftServer server, EntityPlayer p) {
        super(server, new NetworkManager(EnumProtocolDirection.b), p);
    }
    
    public CraftPlayer getCraftPlayer() {
    	return new CraftPlayer((CraftServer) Bukkit.getServer(), b);
    }

}
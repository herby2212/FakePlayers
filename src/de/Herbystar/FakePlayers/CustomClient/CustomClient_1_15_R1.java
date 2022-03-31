package de.Herbystar.FakePlayers.CustomClient;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.EnumProtocolDirection;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.NetworkManager;
import net.minecraft.server.v1_15_R1.PlayerConnection;

public class CustomClient_1_15_R1 extends PlayerConnection {
    public CustomClient_1_15_R1(MinecraftServer server, EntityPlayer p) {
        super(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), p);
    }
    @Override
    public CraftPlayer getPlayer() {
        return new CraftPlayer((CraftServer) Bukkit.getServer(), player); // Fake player prevents spout NPEs
    }
}
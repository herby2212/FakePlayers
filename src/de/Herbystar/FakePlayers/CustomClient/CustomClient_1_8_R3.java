package de.Herbystar.FakePlayers.CustomClient;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class CustomClient_1_8_R3 extends PlayerConnection {
    public CustomClient_1_8_R3(MinecraftServer server, EntityPlayer p) {
        super(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), p);
    }
    @Override
    public CraftPlayer getPlayer() {
        return new CraftPlayer((CraftServer) Bukkit.getServer(), player); // Fake player prevents spout NPEs
    }
}
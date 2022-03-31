package de.Herbystar.FakePlayers.CustomClient;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;

import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.EnumProtocolDirection;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.PlayerConnection;

public class CustomClient_1_8_R2 extends PlayerConnection {
    public CustomClient_1_8_R2(MinecraftServer server, EntityPlayer p) {
        super(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), p);
    }
    @Override
    public CraftPlayer getPlayer() {
        return new CraftPlayer((CraftServer) Bukkit.getServer(), player); // Fake player prevents spout NPEs
    }
}
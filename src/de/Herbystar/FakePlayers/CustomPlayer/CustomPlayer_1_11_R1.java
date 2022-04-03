package de.Herbystar.FakePlayers.CustomPlayer;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.CustomClient_1_11_R1;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;
import net.minecraft.server.v1_11_R1.EnumGamemode;

public class CustomPlayer_1_11_R1 extends EntityPlayer {

    public CustomPlayer_1_11_R1(PlayerInteractManager manager, String name, UUID uuid) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                new GameProfile(uuid, name),
                manager);


        manager.b(EnumGamemode.valueOf(Main.instance.gamemode));
        playerConnection = new CustomClient_1_11_R1(server, this);
    }

}
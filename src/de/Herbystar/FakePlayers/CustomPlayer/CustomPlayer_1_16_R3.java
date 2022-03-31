package de.Herbystar.FakePlayers.CustomPlayer;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.CustomClient_1_16_R3;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.EnumGamemode;

public class CustomPlayer_1_16_R3 extends EntityPlayer {

    public CustomPlayer_1_16_R3(PlayerInteractManager manager, String name, UUID uuid) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                new GameProfile(uuid, name),
                manager);

        manager.b(EnumGamemode.valueOf(Main.instance.gamemode));
        playerConnection = new CustomClient_1_16_R3(server, this);
    }

}

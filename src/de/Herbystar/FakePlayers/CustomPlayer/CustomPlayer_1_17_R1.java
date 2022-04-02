package de.Herbystar.FakePlayers.CustomPlayer;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.CustomClient_1_17_R1;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.EnumGamemode;

public class CustomPlayer_1_17_R1 extends EntityPlayer {

    public CustomPlayer_1_17_R1(String name, UUID uuid) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                new GameProfile(uuid, name));

        d.setGameMode(EnumGamemode.valueOf(Main.instance.gamemode));
        //d.setGameMode(EnumGamemode.getById(1));
        b = new CustomClient_1_17_R1(c, this);
    }

}

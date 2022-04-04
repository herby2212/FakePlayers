package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.CustomClient_1_18_R1;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.EnumGamemode;

public class CustomPlayer_1_18_R1 extends EntityPlayer {

	private static Class<?> playerInteractManagerClass;
	
	static {
		try {
			playerInteractManagerClass = Class.forName("net.minecraft.server.level.PlayerInteractManager");
		} catch (ClassNotFoundException | SecurityException ex) {
            ex.printStackTrace();
        }
	}
	
    public CustomPlayer_1_18_R1(String name, UUID uuid) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                new GameProfile(uuid, name));

        try {
            Method setGamemode = playerInteractManagerClass.getMethod("a", new Class[] { EnumGamemode.class });
			setGamemode.invoke(d, EnumGamemode.valueOf(Main.instance.gamemode));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} 
        b = new CustomClient_1_18_R1(c, this);
    }

}

package de.Herbystar.FakePlayers.CustomPlayer;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.CustomClient_1_8_R3;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

public class CustomPlayer_1_8_R3 extends EntityPlayer {

	/**
	 * 
	 * GameProfile ausgelagert, somit mehr mögliche Funktionen (profile)
	 * Zusätzliche Funktionen nicht benötigt aktuell, somit keine Aktualisierung der anderen CustomPlayer Klassen erforderlich
	 * 
	 */
	
    public CustomPlayer_1_8_R3(GameProfile profile, PlayerInteractManager manager) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                profile,
                manager);

        manager.b(EnumGamemode.valueOf(Main.instance.gamemode));
        playerConnection = new CustomClient_1_8_R3(server, this);
    }

}

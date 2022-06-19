package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.Herbystar.FakePlayers.Utilities.EnumHelper;
import de.Herbystar.TTA.Utils.Reflection;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class PlayOutPlayerInfo {
		
	private static Class<?> playOutPlayerInfoClass;
	private static Constructor<?> playOutPlayerInfoConstructor;
	
	private static Class<?> entityPlayerClass;
	private static Class<?> entityPlayerArrayClass;
	
	private static Class<?> enumPlayerInfoActionClass;
	
	private static Class<?> playerInfoDataClass;
	private static Constructor<?> playerInfoDataConstructor;

	static {
		try {			
			if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
//				minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
//				worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
//				enumGamemodeClass = Class.forName("net.minecraft.world.level.EnumGamemode");
			} else {
				
				entityPlayerArrayClass = Class.forName("[Lnet.minecraft.server.v1_14_R1.EntityPlayer;");
				
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				playOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");

				enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[1];
				
				playOutPlayerInfoConstructor = playOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);
			}
		} catch (SecurityException | NoSuchMethodException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
	}
	
	public PlayOutPlayerInfo(Object entityPlayer, playerInfoAction playerInfoAction) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> playerActionInfoEnums = Arrays.asList(enumPlayerInfoActionClass.getEnumConstants());
		Object enumPlayerActionInfo = EnumHelper.getEnumByString(playerActionInfoEnums, playerInfoAction.name());
		
		Object r = Array.newInstance(entityPlayerClass, 1);
		Array.set(r, 0, entityPlayer);
		
		Object packetPlayOutPlayerInfo = playOutPlayerInfoConstructor.newInstance(enumPlayerActionInfo, r);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Reflection.sendPacket(p, packetPlayOutPlayerInfo);
		}
	}
	
	public static enum playerInfoAction {
		ADD_PLAYER, REMOVE_PLAYER;
	}
}

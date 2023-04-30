package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
	
	static {
		try {			
			if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
				entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
				entityPlayerArrayClass = Class.forName("[Lnet.minecraft.server.level.EntityPlayer;");
				
				if(TTA_BukkitVersion.matchVersion(Arrays.asList("1.19.3", "1.19.4"))) {
					playOutPlayerInfoClass = Class.forName("net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket");
					enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[1];
					playOutPlayerInfoConstructor = playOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerClass);
				} else {
					playOutPlayerInfoClass = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo");
					enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[0];
					playOutPlayerInfoConstructor = playOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);
				}		
			} else {
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				entityPlayerArrayClass = getArrayVersionOfNMSClass("EntityPlayer");
				
				playOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");
				
				enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[1];
				playOutPlayerInfoConstructor = playOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);
			}		
			
		} catch (SecurityException | NoSuchMethodException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
	}
	
	public PlayOutPlayerInfo(Object entityPlayer, playerInfoAction playerInfoAction) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(playerInfoAction == de.Herbystar.FakePlayers.CustomPlayer.PlayOutPlayerInfo.playerInfoAction.REMOVE_PLAYER && TTA_BukkitVersion.matchVersion(Arrays.asList("1.19.3", "1.19.4"))) {
			playerInfoAction = de.Herbystar.FakePlayers.CustomPlayer.PlayOutPlayerInfo.playerInfoAction.UPDATE_LISTED;
		}
		List<Object> playerActionInfoEnums = Arrays.asList(enumPlayerInfoActionClass.getEnumConstants());
		Object enumPlayerActionInfo = EnumHelper.getEnumByString(playerActionInfoEnums, playerInfoAction.name());
		
		Object entityPlayers;
		if(TTA_BukkitVersion.matchVersion(Arrays.asList("1.19.3", "1.19.4"))) {
			entityPlayers = entityPlayer;
		} else {
			entityPlayers = Array.newInstance(entityPlayerClass, 1);
			Array.set(entityPlayers, 0, entityPlayer);
		}		
		Object packetPlayOutPlayerInfo = playOutPlayerInfoConstructor.newInstance(enumPlayerActionInfo, entityPlayers);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Reflection.sendPacket(p, packetPlayOutPlayerInfo);
		}
	}
	
	public static enum playerInfoAction {
		ADD_PLAYER, REMOVE_PLAYER, UPDATE_LISTED;
	}
	
	private static Class<?> getArrayVersionOfNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
        String className = "[Lnet.minecraft.server." + version + "." + name + ";";
        Class<?> c = null;
        
        try {
            c = Class.forName(className);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}

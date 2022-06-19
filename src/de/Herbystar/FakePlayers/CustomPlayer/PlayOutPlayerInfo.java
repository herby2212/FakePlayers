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
				
				playOutPlayerInfoClass = Class.forName("net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo");
				
				enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[0];
			} else {
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				entityPlayerArrayClass = getArrayVersionOfNMSClass("EntityPlayer");
				
				playOutPlayerInfoClass = Reflection.getNMSClass("PacketPlayOutPlayerInfo");
				
				enumPlayerInfoActionClass = playOutPlayerInfoClass.getClasses()[1];
			}		
			
			playOutPlayerInfoConstructor = playOutPlayerInfoClass.getConstructor(enumPlayerInfoActionClass, entityPlayerArrayClass);
		} catch (SecurityException | NoSuchMethodException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
	}
	
	public PlayOutPlayerInfo(Object entityPlayer, playerInfoAction playerInfoAction) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Object> playerActionInfoEnums = Arrays.asList(enumPlayerInfoActionClass.getEnumConstants());
		Object enumPlayerActionInfo = EnumHelper.getEnumByString(playerActionInfoEnums, playerInfoAction.name());
		
		Object entityPlayerArray = Array.newInstance(entityPlayerClass, 1);
		Array.set(entityPlayerArray, 0, entityPlayer);
		
		Object packetPlayOutPlayerInfo = playOutPlayerInfoConstructor.newInstance(enumPlayerActionInfo, entityPlayerArray);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			Reflection.sendPacket(p, packetPlayOutPlayerInfo);
		}
	}
	
	public static enum playerInfoAction {
		ADD_PLAYER, REMOVE_PLAYER;
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

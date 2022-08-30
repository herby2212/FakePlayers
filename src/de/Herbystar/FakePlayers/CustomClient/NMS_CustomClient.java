package de.Herbystar.FakePlayers.CustomClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;

import de.Herbystar.FakePlayers.CustomClient.cClasses.CustomChannel;
import de.Herbystar.TTA.Utils.Reflection;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class NMS_CustomClient {
	
	private static Class<?> minecraftServerClass;
	private static Class<?> enumProtocolDirection;
	private static Class<?> networkManagerClass;
	private static Constructor<?> networkManagerConstructor;
	private static Class<?> entityPlayerClass;
	
	private static Class<?> playerConnectionClass;
	private static Constructor<?> playerConnectionConstructor;
	
	private static Field preparing;
	private static Field channel;
	private static Field socketAddress;
	
	private Object playerCon;
	private static String connectionEnumName;
	
	static {
		try {
			if(TTA_BukkitVersion.getVersionAsInt(2) < 117) {
				minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
				enumProtocolDirection = Reflection.getNMSClass("EnumProtocolDirection");
				networkManagerClass = Reflection.getNMSClass("NetworkManager");
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				
				playerConnectionClass = Reflection.getNMSClass("PlayerConnection");
				
				preparing = networkManagerClass.getDeclaredField("preparing");
				channel = networkManagerClass.getDeclaredField("channel");
				if(TTA_BukkitVersion.getVersionAsInt(2) < 113) {
					socketAddress = networkManagerClass.getDeclaredField("l");
				} else {
					socketAddress = networkManagerClass.getDeclaredField("socketAddress");
				}
				
				connectionEnumName = "CLIENTBOUND";
			} else {
				minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
				enumProtocolDirection = Class.forName("net.minecraft.network.protocol.EnumProtocolDirection");
				networkManagerClass = Class.forName("net.minecraft.network.NetworkManager");
				entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
				
				playerConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
				
				preparing = networkManagerClass.getDeclaredField("preparing");
				if(TTA_BukkitVersion.getVersionAsInt(2) > 117) {
					channel = networkManagerClass.getDeclaredField("m");
					socketAddress = networkManagerClass.getDeclaredField("n");
				} else {
					channel = networkManagerClass.getDeclaredField("k");
					socketAddress = networkManagerClass.getDeclaredField("l");
				}
				
				connectionEnumName = "b";
			}
			networkManagerConstructor = networkManagerClass.getConstructor(enumProtocolDirection);
			playerConnectionConstructor = playerConnectionClass.getConstructor(minecraftServerClass, networkManagerClass, entityPlayerClass);
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
			ex.printStackTrace();
		}
	}
	
    public NMS_CustomClient(Object minecraftServer, Object entityPlayer) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Object netManager = networkManagerConstructor.newInstance(getEnumByString(Arrays.asList(enumProtocolDirection.getEnumConstants()), connectionEnumName));
    	channel.setAccessible(true);
    	socketAddress.setAccessible(true);
    	
    	channel.set(netManager, new CustomChannel());
    	
    	Method remoteAddress = Reflection.getMethod(CustomChannel.class, "remoteAddress");
    	socketAddress.set(netManager, remoteAddress.invoke(channel.get(netManager), new Object[0]));
    	
    	preparing.setAccessible(true);
    	preparing.setBoolean(netManager, false);

    	playerCon = playerConnectionConstructor.newInstance(new Object[] { minecraftServer,
        		netManager,
        		entityPlayer });
    	
    	setFieldsAccessible(Arrays.asList(channel, socketAddress, preparing), false);
    }
    
    private Object getEnumByString(List<Object> enumList, String target) {
    	Object o = null;
    	for(Object e : enumList) {
    		if(e.toString().equals(target)) {
    			o = enumList.get(enumList.indexOf(e));
    		}
    	}
    	return o;
    }
    
    public Object getPlayerConnection() {
    	return playerCon;
    }
    
    /**
     * Sets a list of Fields either accessible or inaccessible.
     * @param accessible
     */
    private void setFieldsAccessible(List<Field> fieldList, boolean accessible) {
    	for(Field field : fieldList) {
    		field.setAccessible(accessible);
    	}
    }
    
}
package de.Herbystar.FakePlayers.CustomClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class NMS_CustomClient {
	
	private static Class<?> minecraftServerClass;
	private static Class<?> enumProtocolDirection;
	private static Class<?> networkManagerClass;
	private static Constructor<?> networkManagerConstructor;
	private static Class<?> entityPlayerClass;
	
	private static Class<?> playerConnectionClass;
	private static Constructor<?> playerConnectionConstructor;
	
	private Object playerCon;
	private static String connectionEnumName;
	
	static {
		try {
			if(TTA_BukkitVersion.getVersionAsInt(2) < 117) {
				connectionEnumName = "CLIENTBOUND";
			} else {
				minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
				enumProtocolDirection = Class.forName("net.minecraft.network.protocol.EnumProtocolDirection");
				networkManagerClass = Class.forName("net.minecraft.network.NetworkManager");
				networkManagerConstructor = networkManagerClass.getConstructor(enumProtocolDirection);
				entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
				
				playerConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
				playerConnectionConstructor = playerConnectionClass.getConstructor(minecraftServerClass, networkManagerClass, entityPlayerClass);
				
				connectionEnumName = "b";
			}
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}
	}
	
    public NMS_CustomClient(Object minecraftServer, Object entityPlayer) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	Object netManager = networkManagerConstructor.newInstance(getEnumByString(Arrays.asList(enumProtocolDirection.getEnumConstants()), connectionEnumName));
    	playerCon = playerConnectionConstructor.newInstance(new Object[] { minecraftServer,
        		netManager,
        		entityPlayer });
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
    
}
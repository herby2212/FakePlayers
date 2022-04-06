package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import net.minecraft.server.level.EntityPlayer;

public class NMS_CustomPlayer extends EntityPlayer {

	private static Class<?> playerInteractManagerClass;
	
	private static Class<?> minecraftServerClass;
	private static Class<?> enumProtocolDirection;
	private static Class<?> enumGamemodeClass;
	private static Class<?> networkManagerClass;
	private static Constructor<?> networkManagerConstructor;
	private static Class<?> entityPlayerClass;
	
	private static Class<?> playerConnectionClass;
	private static Constructor<?> playerConnectionConstructor;
	
	private static Field playerCon;
	private static Field playerIntactmanager;
	
	private static Method sGamemode;
	
	static {
		try {
			playerInteractManagerClass = Class.forName("net.minecraft.server.level.PlayerInteractManager");
			
			minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
			enumProtocolDirection = Class.forName("net.minecraft.network.protocol.EnumProtocolDirection");
			enumGamemodeClass = Class.forName("net.minecraft.world.level.EnumGamemode");
			networkManagerClass = Class.forName("net.minecraft.network.NetworkManager");
			networkManagerConstructor = networkManagerClass.getConstructor(enumProtocolDirection);
			entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
			
			playerCon = entityPlayerClass.getField("b");
			playerIntactmanager = entityPlayerClass.getField("d");
			
			sGamemode = playerInteractManagerClass.getMethod("a", new Class[] { enumGamemodeClass });
			
			playerConnectionClass = Class.forName("net.minecraft.server.network.PlayerConnection");
			playerConnectionConstructor = playerConnectionClass.getConstructor(minecraftServerClass, networkManagerClass, entityPlayerClass);
		} catch (ClassNotFoundException | SecurityException | NoSuchMethodException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
	}
	
    public NMS_CustomPlayer(String name, UUID uuid) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle(),
                new GameProfile(uuid, name));
        Bukkit.getConsoleSender().sendMessage("NMS_CustomPlayer");
        try {
        	List<Object> gamemodes = Arrays.asList(enumGamemodeClass.getEnumConstants());
        	Object gamemode = getEnumByString(gamemodes, Main.instance.gamemode);
            Method setGamemode = playerInteractManagerClass.getMethod("a", new Class[] { enumGamemodeClass });
			setGamemode.invoke(playerIntactmanager.get(this), gamemode);
			
	        playerCon.setAccessible(true);
	        
	        Object netManager = networkManagerConstructor.newInstance(getEnumByString(Arrays.asList(enumProtocolDirection.getEnumConstants()), "b")); 
	        playerCon.set(this, playerConnectionConstructor.newInstance(new Object[] { c,
//	        		new NetworkManager(EnumProtocolDirection.b),
	        		netManager,
	        		this }));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			e.printStackTrace();
		}
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

}

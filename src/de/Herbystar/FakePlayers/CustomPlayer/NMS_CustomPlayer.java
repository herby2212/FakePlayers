package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.NMS_CustomClient;
import de.Herbystar.TTA.Utils.Reflection;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class NMS_CustomPlayer {

	private static Class<?> playerInteractManagerClass;
	
	private static Class<?> minecraftServerClass;
	private static Class<?> worldServerClass;
	private static Class<?> enumGamemodeClass;
	private static Class<?> entityPlayerClass;
	private static Constructor<?> entityPlayerConstructor;
	
	private static Class<?> craftServerClass;
	
	private static Class<?> craftWorldClass;
		
	private static Field playerCon;
	private static Field playerIntactmanager;
	private static Field minServer;
	
	private static Method setGamemodeMethod;
	
	private Object entityPlayer;
	
	static {
		try {
			playerInteractManagerClass = Class.forName("net.minecraft.server.level.PlayerInteractManager");
			
			craftServerClass = Reflection.getCraftClass("CraftServer");
			
			craftWorldClass = Reflection.getCraftClass("CraftWorld");
			minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
			worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
			enumGamemodeClass = Class.forName("net.minecraft.world.level.EnumGamemode");
			entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
			entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class);
			
			playerCon = entityPlayerClass.getField("b");
			playerIntactmanager = entityPlayerClass.getField("d");
			minServer = entityPlayerClass.getField("c");
			
			if(TTA_BukkitVersion.isVersion("1.17", 2)) {
				setGamemodeMethod = playerInteractManagerClass.getMethod("setGameMode", new Class[] { enumGamemodeClass });
			} else {
				setGamemodeMethod = playerInteractManagerClass.getMethod("a", new Class[] { enumGamemodeClass });
			}
		} catch (ClassNotFoundException | SecurityException | NoSuchMethodException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
	}
	
    public NMS_CustomPlayer(String name, UUID uuid) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
    	Method getDedicatedServer = craftServerClass.getMethod("getServer");
    	Object dServer =  getDedicatedServer.invoke(craftServerClass.cast(Bukkit.getServer()));
    	
        Object mcServer = minecraftServerClass.cast(dServer);
        
    	Method getWorldServer = craftWorldClass.getMethod("getHandle");
    	Object wServer =  getWorldServer.invoke(craftWorldClass.cast(Bukkit.getServer().getWorld(Main.instance.defaultWorldName)));
    	
    	Object worldServer = worldServerClass.cast(wServer);
    	
    	entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name));
    	
    	List<Object> gamemodes = Arrays.asList(enumGamemodeClass.getEnumConstants());
    	Object gamemode = getEnumByString(gamemodes, Main.instance.gamemode);
        setGamemodeMethod.invoke(playerIntactmanager.get(entityPlayer), gamemode);
		
        playerCon.setAccessible(true);
        
        playerCon.set(entityPlayer, new NMS_CustomClient(minServer.get(entityPlayer), entityPlayer).getPlayerConnection());
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
    
    public Object getEntityPlayer() {
		return this.entityPlayer;
    	
    }

}

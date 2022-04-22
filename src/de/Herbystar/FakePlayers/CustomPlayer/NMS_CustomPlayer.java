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
	private static Constructor<?> playerInteractManagerConstructor;
	
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
			craftServerClass = Reflection.getCraftClass("CraftServer");
			
			craftWorldClass = Reflection.getCraftClass("CraftWorld");
			
			if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
				minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
				worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
				enumGamemodeClass = Class.forName("net.minecraft.world.level.EnumGamemode");
				
				playerInteractManagerClass = Class.forName("net.minecraft.server.level.PlayerInteractManager");
				playerInteractManagerConstructor = playerInteractManagerClass.getConstructor(worldServerClass);
				
				entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
				entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class);
				
				playerIntactmanager = entityPlayerClass.getField("d");
				minServer = entityPlayerClass.getField("c");
				playerCon = entityPlayerClass.getField("b");
			} else {
				minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
				worldServerClass = Reflection.getNMSClass("WorldServer");
				enumGamemodeClass = Reflection.getNMSClass("EnumGamemode");
				
				playerInteractManagerClass = Reflection.getNMSClass("PlayerInteractManager");
				playerInteractManagerConstructor = playerInteractManagerClass.getConstructor(worldServerClass);
				
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class, playerInteractManagerClass);
				
				minServer = entityPlayerClass.getField("server");
				playerCon = entityPlayerClass.getField("playerConnection");
			}
						
			if(TTA_BukkitVersion.isVersion("1.17", 2)) {
				setGamemodeMethod = playerInteractManagerClass.getMethod("setGameMode", new Class[] { enumGamemodeClass });
			} else if(TTA_BukkitVersion.isVersion("1.18", 2)) {
				setGamemodeMethod = playerInteractManagerClass.getMethod("a", new Class[] { enumGamemodeClass });
			} else { //1.16 and below
				setGamemodeMethod = playerInteractManagerClass.getMethod("b", new Class[] { enumGamemodeClass });
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
    	Object playerInteractManager = playerInteractManagerConstructor.newInstance(worldServer);
    	
    	if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
    		entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name));
    	} else {
    		entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name), playerInteractManager);
    	}
    	
    	List<Object> gamemodes = Arrays.asList(enumGamemodeClass.getEnumConstants());
    	Object gamemode = getEnumByString(gamemodes, Main.instance.gamemode);
    	
    	if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
    		setGamemodeMethod.invoke(playerIntactmanager.get(entityPlayer), gamemode);
    	} else {
        	setGamemodeMethod.invoke(playerInteractManager, gamemode);
    	}  	
        		
        playerCon.setAccessible(true);
        
        Object connection = new NMS_CustomClient(minServer.get(entityPlayer), entityPlayer).getPlayerConnection();

        playerCon.set(entityPlayer, connection);
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

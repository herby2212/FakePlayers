package de.Herbystar.FakePlayers.CustomPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomClient.NMS_CustomClient;
import de.Herbystar.FakePlayers.CustomPlayer.PlayOutPlayerInfo.playerInfoAction;
import de.Herbystar.FakePlayers.Utilities.EnumHelper;
import de.Herbystar.TTA.Utils.Reflection;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class NMS_CustomPlayer {

	private static Class<?> playerInteractManagerClass;
	private static Constructor<?> playerInteractManagerConstructor;
	
	private static Class<?> minecraftServerClass;
	private static Class<?> worldClass;
	private static Class<?> worldServerClass;
	private static Class<?> enumGamemodeClass;
	private static Class<?> profilePublicKeyClass;
	private static Class<?> entityPlayerClass;
	private static Constructor<?> entityPlayerConstructor;
	
	private static Class<?> craftServerClass;
	
	private static Class<?> craftWorldClass;
		
	private static Field playerCon;
	private static Field playerIntactmanager;
	private static Field minServer;
	private static Field ping;
	
	private static Method setGamemodeMethod;
	
	private Object entityPlayer;
	
	private UUID uuid;
		
	static {
		try {		
			craftServerClass = Reflection.getCraftClass("CraftServer");
			
			craftWorldClass = Reflection.getCraftClass("CraftWorld");
			
			if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
				minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
				worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
				enumGamemodeClass = Class.forName("net.minecraft.world.level.EnumGamemode");
				
				playerInteractManagerClass = Class.forName("net.minecraft.server.level.PlayerInteractManager");
								
				entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
				
				if(TTA_BukkitVersion.isVersion("1.19", 2) && !TTA_BukkitVersion.matchVersion(Arrays.asList("1.19.3", "1.19.4"))) {
					profilePublicKeyClass = Class.forName("net.minecraft.world.entity.player.ProfilePublicKey");
					entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class, profilePublicKeyClass);
				} else {
					entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class);
				}
				
				playerIntactmanager = entityPlayerClass.getField("d");
				minServer = entityPlayerClass.getField("c");
				playerCon = entityPlayerClass.getField("b");
				ping = entityPlayerClass.getField("e");
			} else {
				minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
				
				worldClass = Reflection.getNMSClass("World");
				worldServerClass = Reflection.getNMSClass("WorldServer");
				
				if(TTA_BukkitVersion.getVersionAsInt(2) <= 19) {
					enumGamemodeClass = Reflection.getNMSClass("WorldSettings").getClasses()[0];
				} else {
					enumGamemodeClass = Reflection.getNMSClass("EnumGamemode");
				}
				
				playerInteractManagerClass = Reflection.getNMSClass("PlayerInteractManager");
				if(TTA_BukkitVersion.getVersionAsInt(2) <= 113) {
					playerInteractManagerConstructor = playerInteractManagerClass.getConstructor(worldClass);
				} else {
					playerInteractManagerConstructor = playerInteractManagerClass.getConstructor(worldServerClass);
				}
				
				entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
				entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class, playerInteractManagerClass);
				
				minServer = entityPlayerClass.getField("server");
				playerCon = entityPlayerClass.getField("playerConnection");
				ping = entityPlayerClass.getField("ping");
			}
						
			if(TTA_BukkitVersion.isVersion("1.17", 2)) {
				setGamemodeMethod = playerInteractManagerClass.getMethod("setGameMode", new Class[] { enumGamemodeClass });
			} else if(TTA_BukkitVersion.getVersionAsInt(2) >= 118) {
				setGamemodeMethod = playerInteractManagerClass.getMethod("a", new Class[] { enumGamemodeClass });
			} else { //1.16 and below
				setGamemodeMethod = playerInteractManagerClass.getMethod("b", new Class[] { enumGamemodeClass });
			}
		} catch (ClassNotFoundException | SecurityException | NoSuchMethodException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
	}
	
    public NMS_CustomPlayer(String name, UUID uuid) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException, WrongWorldException {
    	Method getDedicatedServer = craftServerClass.getMethod("getServer");
    	Object dServer =  getDedicatedServer.invoke(craftServerClass.cast(Bukkit.getServer()));
    	
        Object mcServer = minecraftServerClass.cast(dServer);
        
    	Method getWorldServer = craftWorldClass.getMethod("getHandle");
    	Object wServer = null;
    	try {
    		wServer = getWorldServer.invoke(craftWorldClass.cast(Bukkit.getServer().getWorld(Main.instance.defaultWorldName)));
    	} catch(NullPointerException ex) {
    		Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§cError: Misconfigured default world in config!");
    		throw new WrongWorldException();
    	}
    	
    	Object worldServer = worldServerClass.cast(wServer);
    	Object playerInteractManager = null;
    	
    	if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
    		if(TTA_BukkitVersion.isVersion("1.19", 2) && !TTA_BukkitVersion.matchVersion(Arrays.asList("1.19.3", "1.19.4"))) {
        		entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name), null);
    		} else {
        		entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name));
    		}
    	} else {
    		if(TTA_BukkitVersion.getVersionAsInt(2) <= 113) {
        		playerInteractManager = playerInteractManagerConstructor.newInstance(worldClass.cast(worldServer));
    		} else {
        		playerInteractManager = playerInteractManagerConstructor.newInstance(worldServer);
    		}
    		entityPlayer = entityPlayerConstructor.newInstance(mcServer, worldServer, new GameProfile(uuid, name), playerInteractManager);
    	}
    	
    	ping.setAccessible(true);
    	ping.setInt(entityPlayer, new Random().nextInt(100));
    	
    	List<Object> gamemodes = Arrays.asList(enumGamemodeClass.getEnumConstants());
    	Object gamemode = EnumHelper.getEnumByString(gamemodes, Main.instance.gamemode);
    	
    	if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
    		setGamemodeMethod.invoke(playerIntactmanager.get(entityPlayer), gamemode);
    	} else {
        	setGamemodeMethod.invoke(playerInteractManager, gamemode);
    	}  	
        
        playerCon.setAccessible(true);
        
        Object connection = new NMS_CustomClient(minServer.get(entityPlayer), entityPlayer).getPlayerConnection();

        playerCon.set(entityPlayer, connection);
        
        new PlayOutPlayerInfo(entityPlayer, playerInfoAction.ADD_PLAYER);
    }
    
    public Object getEntityPlayer() {
		return this.entityPlayer;
    	
    }
    
    public UUID getUUID() {
    	return this.uuid;
    }

}

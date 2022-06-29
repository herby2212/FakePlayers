package de.Herbystar.FakePlayers.PlayerListHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomPlayer.NMS_CustomPlayer;
import de.Herbystar.FakePlayers.CustomPlayer.PlayOutPlayerInfo;
import de.Herbystar.FakePlayers.CustomPlayer.WrongWorldException;
import de.Herbystar.FakePlayers.CustomPlayer.PlayOutPlayerInfo.playerInfoAction;
import de.Herbystar.FakePlayers.Utilities.RandomUUID;
import de.Herbystar.FakePlayers.Utilities.UUIDRecycler;
import de.Herbystar.TTA.Utils.Reflection;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

@SuppressWarnings("unused")
public class NMS_PlayerListHandler implements PlayerListHandler {
	
	public static HashMap<String, NMS_CustomPlayer> customPlayers = new HashMap<String, NMS_CustomPlayer>();
	private static List<NMS_CustomPlayer> fakedPlayersStorage = new ArrayList<NMS_CustomPlayer>();

	private static List<Object> list;
	
	private static Class<?> worldClass;
	private static Class<?> worldServerClass;
	private static Class<?> minecraftServerClass;
	private static Constructor<?> minecraftServerConstructor;
	
	private static Class<?> server;
	
	private static Class<?> craftPlayerClass;
	private static Constructor<?> craftPlayerConstructor;
	
	private static Class<?> entityPlayerClass;
	
	private static Class<?> craftServerClass;
	private static Constructor<?> craftServerConstructor;
	
	private static Class<?> playerInteractManagerClass;
	private static Constructor<?> playerInteractManagerConstructor;
	
	private static Field playerList;
	private static Field players;	
	 
    static {    
        try {
        	craftPlayerClass = Reflection.getCraftClass("entity.CraftPlayer");
        	
        	craftServerClass = Reflection.getCraftClass("CraftServer");
        	
        	if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
        		worldClass = Class.forName("net.minecraft.world.level.World");
            	worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
            	
            	minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
                        	
            	entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
            	
            	if(TTA_BukkitVersion.isVersion("1.19", 2)) {
            		playerList = minecraftServerClass.getDeclaredField("P");
            	} else {
                	playerList = minecraftServerClass.getDeclaredField("S");
            	}
        	} else {
        		worldClass = Reflection.getNMSClass("World");
            	worldServerClass = Reflection.getNMSClass("WorldServer");
            	
            	minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
                        	
            	entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
            	
            	if(TTA_BukkitVersion.matchVersion(Arrays.asList("1.8", "1.9", "1.10", "1.11", "1.12"), 2)) {
            		playerList = minecraftServerClass.getDeclaredField("v");
            	} else if(!TTA_BukkitVersion.isVersion("1.13.2") && TTA_BukkitVersion.isVersion("1.13", 2)) {
            		playerList = minecraftServerClass.getDeclaredField("s");
            	} else {
                	playerList = minecraftServerClass.getDeclaredField("playerList");
            	}
        	}      	
        } catch (SecurityException | ClassNotFoundException | NoSuchFieldException ex) {
            System.err.println("Error - Classes not initialized!");
			ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	public void setOnlinePlayers(int amount) {
        playerList.setAccessible(true);
        assert playerList != null;
        try {
        	Method getDedicatedServer = craftServerClass.getMethod("getServer");
        	Object dServer =  getDedicatedServer.invoke(craftServerClass.cast(Bukkit.getServer()));
        	
            Object instance = minecraftServerClass.cast(dServer);
            Object pList = playerList.get(instance);
            Field f;
            if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
            	f = pList.getClass().getSuperclass().getDeclaredField("j");
            } else {
            	f = pList.getClass().getSuperclass().getDeclaredField("players");
            }
            
            list = (List<Object>) f.get(pList);
            for(int i = 0; i < amount; i++) {
            	NMS_CustomPlayer cp;
				try {
					cp = new NMS_CustomPlayer("", UUID.fromString("3e5cf803-1183-43f5-a53d-ea53c61b6274"));
				} catch (WrongWorldException e) {
					break;
				}
            	fakedPlayersStorage.add(cp);
            	list.add(cp.getEntityPlayer());
            }
			
            Main.instance.fakePlayersCount = amount;
        } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
            e.printStackTrace();
        }
    }
    
    public void removeOnlinePlayers() {
    	int amount = fakedPlayersStorage.size();
    	for(NMS_CustomPlayer cp : fakedPlayersStorage) {
    		list.remove(cp.getEntityPlayer());
    	}
    	Main.instance.fakePlayersCount = amount;
    }
        
    @SuppressWarnings("deprecation")
	public void addCustomOnlinePlayer(String name) {
//    	Method getDedicatedServer;
		try {
	    	NMS_CustomPlayer customPlayer;
	    	UUID uuid = RandomUUID.randomUUID();
	    	
	    	if(Main.instance.reUseUUIDs == true && UUIDRecycler.fakePlayerUUIDStorage.containsKey(name)) {
	    		uuid = UUIDRecycler.fakePlayerUUIDStorage.get(name);
	    	}
	    	
	    	if(Main.instance.skins == true) {
	    		uuid = Bukkit.getServer().getOfflinePlayer(name).getUniqueId();
	    	}
			try {
				customPlayer = new NMS_CustomPlayer(name, uuid);
			} catch (WrongWorldException e) {
				return;
			}
		
			list.add(customPlayer.getEntityPlayer());
			customPlayers.put(name, customPlayer);
			if(Main.instance.reUseUUIDs == true) {
				UUIDRecycler.fakePlayerUUIDStorage.put(name, uuid);
				UUIDRecycler.saveUUIDs();
			}
			
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount + 1;

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
    }
    
    //Remove all custom faked online players created by users or advanced startup
    public void removeCustomOnlinePlayers() {
    	for(NMS_CustomPlayer cp : customPlayers.values()) {
    		list.remove(cp.getEntityPlayer());
    	}
    }
    
    public void removeCustomOnlinePlayer(String name) {
		if(customPlayers.containsKey(name)) {
			NMS_CustomPlayer cp = customPlayers.get(name);
			Object entityPlayer = cp.getEntityPlayer();	
			try {
				new PlayOutPlayerInfo(entityPlayer, playerInfoAction.REMOVE_PLAYER);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
			list.remove(entityPlayer);
			customPlayers.remove(name);
			
			if(Main.instance.reUseUUIDs == true && UUIDRecycler.fakePlayerUUIDStorage.containsKey(name)) {
				UUIDRecycler.fakePlayerUUIDStorage.remove(name);
				UUIDRecycler.saveUUIDs();
			}
			
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount - 1;
			
		}
    }
 
}
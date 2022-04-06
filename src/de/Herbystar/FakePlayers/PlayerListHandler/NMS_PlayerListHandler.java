package de.Herbystar.FakePlayers.PlayerListHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomPlayer.NMS_CustomPlayer;
import de.Herbystar.FakePlayers.Utilities.RandomUUID;
import de.Herbystar.TTA.Utils.Reflection;

@SuppressWarnings("unused")
public class NMS_PlayerListHandler implements PlayerListHandler {
	
	private static HashMap<String, NMS_CustomPlayer> customPlayers = new HashMap<String, NMS_CustomPlayer>();
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
	private static Constructor<?> entityPlayerConstructor;
	
	private static Class<?> craftServerClass;
	private static Constructor<?> craftServerConstructor;
	
	private static Class<?> playerInteractManagerClass;
	private static Constructor<?> playerInteractManagerConstructor;
	
	 
    static {    
        try {
        	worldClass = Class.forName("net.minecraft.world.level.World");
        	worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
        	
        	craftPlayerClass = Reflection.getCraftClass("entity.CraftPlayer");
        	        	        	
        	craftServerClass = Reflection.getCraftClass("CraftServer");
        	
        	minecraftServerClass = Class.forName("net.minecraft.server.MinecraftServer");
                    	
        	entityPlayerClass = Class.forName("net.minecraft.server.level.EntityPlayer");
        	entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class);

        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException ex) {
            System.err.println("Error - Classes not initialized!");
			ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	public void setOnlinePlayers(int amount) {
    	Bukkit.getConsoleSender().sendMessage("NMS PlayerListHandler");
        Field t = null;
        try {
            t = minecraftServerClass.getDeclaredField("S");
            t.setAccessible(true);
        } catch(Exception x) {
        	x.printStackTrace();
        }
        assert t != null;
        try {
        	Method getDedicatedServer = craftServerClass.getMethod("getServer");
        	Object dServer =  getDedicatedServer.invoke(craftServerClass.cast(Bukkit.getServer()));
        	
            Object instance = minecraftServerClass.cast(dServer);
            Object playerList = t.get(instance);
            Field f = playerList.getClass().getSuperclass().getDeclaredField("j");
            
            list = (List<Object>) f.get(playerList);
            for(int i = 0; i < amount; i++) {
            	NMS_CustomPlayer cp = new NMS_CustomPlayer("", UUID.fromString("3e5cf803-1183-43f5-a53d-ea53c61b6274"));
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
    
    public void addCustomOnlinePlayer(String name) {
    	Method getDedicatedServer;
		try {
	    	NMS_CustomPlayer customPlayer = new NMS_CustomPlayer(name, RandomUUID.randomUUID());		
			list.add(customPlayer.getEntityPlayer());
			customPlayers.put(name, customPlayer);

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
			list.remove(cp.getEntityPlayer());
			customPlayers.remove(name);
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount - 1;
		}
    }
 
}
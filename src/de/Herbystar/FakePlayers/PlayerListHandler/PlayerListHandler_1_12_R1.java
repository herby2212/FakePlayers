package de.Herbystar.FakePlayers.PlayerListHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomPlayer.CustomPlayer_1_11_R1;
import de.Herbystar.FakePlayers.CustomPlayer.CustomPlayer_1_12_R1;
import de.Herbystar.FakePlayers.Utilities.RandomUUID;
import de.Herbystar.TTA.Utils.Reflection;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.WorldServer;

@SuppressWarnings("unused")
public class PlayerListHandler_1_12_R1 implements PlayerListHandler {
	
	private static HashMap<String, CustomPlayer_1_12_R1> customPlayers = new HashMap<String, CustomPlayer_1_12_R1>();
	private static List<CustomPlayer_1_12_R1> fakedPlayersStorage = new ArrayList<CustomPlayer_1_12_R1>();

	private static List<EntityPlayer> list;
	
	private static Class<?> worldClass;
	private static Class<?> worldServerClass;
	private static Class<?> minecraftServerClass;
	private static Constructor<?> minecraftServerConstructor;
	
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
        	
        	worldClass = Reflection.getNMSClass("World");
        	worldServerClass = Reflection.getNMSClass("WorldServer");

        	playerInteractManagerClass = Reflection.getNMSClass("PlayerInteractManager");
        	playerInteractManagerConstructor = playerInteractManagerClass.getConstructor(worldClass);
        	
        	craftPlayerClass = Reflection.getCraftClass("entity.CraftPlayer");
        	
        	craftServerClass = Reflection.getCraftClass("CraftServer");        	
        	
        	minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
                    	
        	entityPlayerClass = Reflection.getNMSClass("EntityPlayer");
        	entityPlayerConstructor = entityPlayerClass.getConstructor(minecraftServerClass, worldServerClass, GameProfile.class, playerInteractManagerClass);

        } catch (NoSuchMethodException | SecurityException ex) {
            System.err.println("Error - Classes not initialized!");
			ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	public void setOnlinePlayers(int amount) {
        Field t = null;
        try {
            t = minecraftServerClass.getDeclaredField("v");
            t.setAccessible(true);
        } catch(Exception x) {
            x.printStackTrace();
        }
        MinecraftServer instance = ((CraftServer) Bukkit.getServer()).getServer();
        assert t != null;
        try {
        	Object nms_world = Reflection.getHandle(Bukkit.getServer().getWorld(Main.instance.defaultWorldName));
            Object playerList = t.get(instance);
            Field f = playerList.getClass().getSuperclass().getDeclaredField("players");
            
            list = (List<EntityPlayer>) f.get(playerList);
            for(int i = 0; i < amount; i++) {
            	CustomPlayer_1_12_R1 cp = new CustomPlayer_1_12_R1((PlayerInteractManager) playerInteractManagerConstructor.newInstance(nms_world), "", UUID.fromString("3e5cf803-1183-43f5-a53d-ea53c61b6274"));
            	fakedPlayersStorage.add(cp);
            	list.add(cp);
            }
			
            Main.instance.fakePlayersCount = amount;
        } catch (IllegalAccessException | NoSuchFieldException | InstantiationException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    public void removeOnlinePlayers() {
    	int amount = fakedPlayersStorage.size();
    	for(CustomPlayer_1_12_R1 cp : fakedPlayersStorage) {
    		list.remove(cp);
    	}
    	Main.instance.fakePlayersCount = amount;
    }
    
    //Remove all custom faked online players created by users or advanced startup
    public void removeCustomOnlinePlayers() {
    	for(CustomPlayer_1_12_R1 cp : customPlayers.values()) {
    		list.remove(cp);
    	}
    }
    
    public void addCustomOnlinePlayer(String name) {
		CustomPlayer_1_12_R1 customPlayer = new CustomPlayer_1_12_R1(new PlayerInteractManager(((CraftWorld) Bukkit.getServer().getWorld(Main.instance.defaultWorldName)).getHandle()), name, RandomUUID.randomUUID());		
		list.add(customPlayer);
		customPlayers.put(name, customPlayer);

		Main.instance.fakePlayersCount = Main.instance.fakePlayersCount + 1;
    }
    
    public void removeCustomOnlinePlayer(String name) {
		if(customPlayers.containsKey(name)) {
			CustomPlayer_1_12_R1 cp = customPlayers.get(name);
			list.remove(cp);
			customPlayers.remove(name);
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount - 1;
		}
    }
 
}
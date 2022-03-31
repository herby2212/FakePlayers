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
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomPlayer.CustomPlayer_1_8_R3;
import de.Herbystar.FakePlayers.Utilities.RandomUUID;
import de.Herbystar.TTA.Utils.Reflection;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

@SuppressWarnings("unused")
public class PlayerListHandler_1_8_R3 implements PlayerListHandler {
	
	private static HashMap<String, CustomPlayer_1_8_R3> customPlayers = new HashMap<String, CustomPlayer_1_8_R3>();
	private static List<CustomPlayer_1_8_R3> fakedPlayersStorage = new ArrayList<CustomPlayer_1_8_R3>();
	
	private static List<EntityPlayer> list;
	
	private static Object nmsWorld;
	
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
//        	craftServerConstructor = craftServerClass.getConstructor(Server.class);
        	
        	
        	minecraftServerClass = Reflection.getNMSClass("MinecraftServer");
//        	minecraftServerConstructor = minecraftServerClass.getConstructor(craftServerClass);
                    	
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
//        Object instance = minecraftServerConstructor.newInstance(craftServerClass.cast(Bukkit.getServer()));
//        Object instance = minecraftServerConstructor.newInstance(craftServerConstructor.newInstance(Bukkit.getServer()));
        MinecraftServer instance = ((CraftServer) Bukkit.getServer()).getServer();
        assert t != null;
        try {
        	nmsWorld = Reflection.getHandle(Bukkit.getServer().getWorld(Main.instance.defaultWorldName));
            Object playerList = t.get(instance);
            Field f = playerList.getClass().getSuperclass().getDeclaredField("players");
            
//            List<Object> list = (List<Object>) f.get(playerList);
            list = (List<EntityPlayer>) f.get(playerList);
            for(int i = 0; i < amount; i++) {
//                Object cp = entityPlayerConstructor.newInstance(instance, (WorldServer) Reflection.getHandle(Bukkit.getServer().getWorld("world")),new GameProfile(UUID.fromString("fdeeb0ad-51cc-404e-af3d-1a4bc1f525c9"), ""), pmanager);
            	CustomPlayer_1_8_R3 cp = new CustomPlayer_1_8_R3(new GameProfile(UUID.fromString("fdeeb0ad-51cc-404e-af3d-1a4bc1f525c9"), ""), (PlayerInteractManager) playerInteractManagerConstructor.newInstance(nmsWorld));            	
            	fakedPlayersStorage.add(cp);
                list.add(cp);
            }
            
            Main.instance.fakePlayersCount = amount;
        } catch (IllegalAccessException | NoSuchFieldException | InstantiationException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
    //Remove all faked online players created on startup
    public void removeOnlinePlayers() {
    	int amount = fakedPlayersStorage.size();
    	for(CustomPlayer_1_8_R3 cp : fakedPlayersStorage) {
    		list.remove(cp);
    	}    	
    	Main.instance.fakePlayersCount = amount;
    }
    
    //Remove all custom faked online players created by users or advanced startup
    public void removeCustomOnlinePlayers() {
    	for(CustomPlayer_1_8_R3 cp : customPlayers.values()) {
    		list.remove(cp);
    	}
    }
    
    public void addCustomOnlinePlayer(String name) {
		try {
			if(customPlayers.containsKey(name)) {
				removeCustomOnlinePlayer(name);
			}
        	CustomPlayer_1_8_R3 customPlayer = new CustomPlayer_1_8_R3(new GameProfile(RandomUUID.randomUUID(), name), (PlayerInteractManager) playerInteractManagerConstructor.newInstance(nmsWorld));            	

//			CustomPlayer_1_8_R3 customPlayer = new CustomPlayer_1_8_R3((PlayerInteractManager) playerInteractManagerConstructor.newInstance(nmsWorld), name, RandomUUID.randomUUID());
			list.add(customPlayer);
			customPlayers.put(name, customPlayer);

			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount + 1;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}		
    }
    
    public void removeCustomOnlinePlayer(String name) {
		if(customPlayers.containsKey(name)) {
			CustomPlayer_1_8_R3 cp = customPlayers.get(name);
			list.remove(cp);
			customPlayers.remove(name);
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount - 1;
		}
    }
 
//    public class CustomPlayer extends EntityPlayer {
// 
//        public CustomPlayer(PlayerInteractManager manager) {
//            super(((CraftServer) Bukkit.getServer()).getServer(),
//            		(WorldServer) Reflection.getHandle(Bukkit.getServer().getWorld("world")),
//                    new GameProfile(UUID.fromString("fdeeb0ad-51cc-404e-af3d-1a4bc1f525c9"), ""),
//                    manager);
// 
//            manager.b(EnumGamemode.NOT_SET);
//            playerConnection = new CustomClient(server, this);
//        }
// 
//        public class CustomClient extends PlayerConnection {
//            public CustomClient(MinecraftServer server, EntityPlayer p) {
//                super(server, new NetworkManager(EnumProtocolDirection.CLIENTBOUND), p);
//            }
//            @Override
//            public CraftPlayer getPlayer() {
//                return new CraftPlayer((CraftServer) Bukkit.getServer(), player); // Fake player prevents spout NPEs
//            }
//        }
//    }
 
}
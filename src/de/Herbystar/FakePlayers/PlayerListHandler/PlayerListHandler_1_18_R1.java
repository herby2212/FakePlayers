package de.Herbystar.FakePlayers.PlayerListHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import com.mojang.authlib.GameProfile;

import de.Herbystar.FakePlayers.Main;
import de.Herbystar.FakePlayers.CustomPlayer.CustomPlayer_1_18_R1;
import de.Herbystar.FakePlayers.Utilities.RandomUUID;
import de.Herbystar.TTA.Utils.Reflection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.MinecraftServer;

@SuppressWarnings("unused")
public class PlayerListHandler_1_18_R1 implements PlayerListHandler {
	
	private static HashMap<String, CustomPlayer_1_18_R1> customPlayers = new HashMap<String, CustomPlayer_1_18_R1>();
	private static List<CustomPlayer_1_18_R1> fakedPlayersStorage = new ArrayList<CustomPlayer_1_18_R1>();

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
        	worldClass = Class.forName("net.minecraft.world.level.World");
        	worldServerClass = Class.forName("net.minecraft.server.level.WorldServer");
        	
        	craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer");
        	
        	craftServerClass = Class.forName("org.bukkit.craftbukkit.v1_18_R1.CraftServer");
        	
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
        Field t = null;
        try {
            t = minecraftServerClass.getDeclaredField("S");
            t.setAccessible(true);
        } catch(Exception x) {
        	x.printStackTrace();
        }
        MinecraftServer instance = ((CraftServer) Bukkit.getServer()).getServer();
        assert t != null;
        try {
            Object playerList = t.get(instance);
            Field f = playerList.getClass().getSuperclass().getDeclaredField("j");
            
            list = (List<EntityPlayer>) f.get(playerList);
            for(int i = 0; i < amount; i++) {
            	CustomPlayer_1_18_R1 cp = new CustomPlayer_1_18_R1("", UUID.fromString("3e5cf803-1183-43f5-a53d-ea53c61b6274"));
            	fakedPlayersStorage.add(cp);
            	list.add(cp);
            }
			
            Main.instance.fakePlayersCount = amount;
        } catch (IllegalAccessException | NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
    public void removeOnlinePlayers() {
    	int amount = fakedPlayersStorage.size();
    	for(CustomPlayer_1_18_R1 cp : fakedPlayersStorage) {
    		list.remove(cp);
    	}
    	Main.instance.fakePlayersCount = amount;
    }
    
    public void addCustomOnlinePlayer(String name) {
    	CustomPlayer_1_18_R1 customPlayer = new CustomPlayer_1_18_R1(name, RandomUUID.randomUUID());		
		list.add(customPlayer);
		customPlayers.put(name, customPlayer);

		Main.instance.fakePlayersCount = Main.instance.fakePlayersCount + 1;
    }
    
    //Remove all custom faked online players created by users or advanced startup
    public void removeCustomOnlinePlayers() {
    	for(CustomPlayer_1_18_R1 cp : customPlayers.values()) {
    		list.remove(cp);
    	}
    }
    
    public void removeCustomOnlinePlayer(String name) {
		if(customPlayers.containsKey(name)) {
			CustomPlayer_1_18_R1 cp = customPlayers.get(name);
			list.remove(cp);
			customPlayers.remove(name);
			Main.instance.fakePlayersCount = Main.instance.fakePlayersCount - 1;
		}
    }
 
}
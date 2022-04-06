package de.Herbystar.FakePlayers;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.Herbystar.FakePlayers.Events.PlayerLoginEventHandler;
import de.Herbystar.FakePlayers.PlayerListHandler.NMS_PlayerListHandler;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_10_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_11_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_12_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_13_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_13_R2;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_14_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_15_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_16_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_16_R2;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_16_R3;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_17_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_18_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_18_R2;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_8_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_8_R2;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_8_R3;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_9_R1;
import de.Herbystar.FakePlayers.PlayerListHandler.PlayerListHandler_1_9_R2;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class Main extends JavaPlugin {
	
	public String internalPrefix = "§4[§6FakePlayers§4] ";
	public String version;
	public static Main instance;
	public Pattern Version_Pattern = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
	public int fakePlayersCount;
	public PlayerListHandler playerListHandler;
	public String defaultWorldName = "world";
	public boolean protocolLib = false;
	public String gamemode;
	public boolean advancedFakedPlayersEnabled = false;
	public List<String> advancedFakedPlayersNames;
	
	public void onEnable() {
		instance = this;
		sendTerms();
		loadConfig();
		
		this.defaultWorldName = this.getConfig().getString("FakePlayers.defaultWorld");
		this.protocolLib = this.getConfig().getBoolean("FakePlayers.ProtocolLib");
		this.gamemode = this.getConfig().getString("FakePlayers.Gamemode");
		this.advancedFakedPlayersEnabled = this.getConfig().getBoolean("FakePlayers.AdvancedFakedPlayers (Wiki Recommended).Enabled");
		this.advancedFakedPlayersNames = this.getConfig().getStringList("FakePlayers.AdvancedFakedPlayers (Wiki Recommended).Names");
		
		getCommands();
		getEvents();
		initializePlayerListHandler();
		if(this.CheckDepends() == false) {
			Bukkit.getServer().getConsoleSender().sendMessage(this.internalPrefix + "§cYou need to download §eTTA§c!");
			Bukkit.getServer().getConsoleSender().sendMessage(this.internalPrefix + "§cFakePlayers will now shut down!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		Instant beforePlayerCreation = Instant.now();
		/*
		 * Aktuell Fehler bei Benutzung von ProtocolSupport
		 * Jedoch bei Nutzung von ProtocolLib(Mode) und ProtocolSupport kein Fehler => Möglichkeit als temporäre Lösung, sollte jedoch wenn möglich vermieden werden!
		 * Laut ProtocolSupport Entwicklern => Fake player wurden nicht ordentlich erstellt
		 
		try {
			ProtocolLibMode.setOnlinePlayers(this.getConfig().getInt("FakePlayers.OnlinePlayers"));
			Bukkit.getConsoleSender().sendMessage(this.internalPrefix + "§e§lSwitched to §aProtocolLib§e§l Mode! Commands are not supported in this mode!");
		} catch(Exception | Error ex) {
			playerListHandler.setOnlinePlayers(this.getConfig().getInt("FakePlayers.OnlinePlayers"));
		}
		*/
		
		this.setFakePlayers();

		Bukkit.getConsoleSender().sendMessage(this.internalPrefix + "§bVersion: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + " §aenabled!");
		
		new BukkitRunnable() {
			@Override
		    public void run() {
			     Instant afterPlayerCreation = Instant.now();
			     Duration timeElapsed = Duration.between(beforePlayerCreation, afterPlayerCreation);
			     Bukkit.getConsoleSender().sendMessage(internalPrefix + "§4It took §6" + timeElapsed.toString().replace("PT", "").replace("S", "s") + "§4 to simulate §6" + Integer.toString(getConfig().getInt("FakePlayers.OnlinePlayers")) + "§4 players.");
			 }
		}.runTaskLater(this, 0L);
	}
	
	private void setFakePlayers() {
		if(this.advancedFakedPlayersEnabled == true) {
			if(this.advancedFakedPlayersNames.size() >= this.getConfig().getInt("FakePlayers.OnlinePlayers")) {
				playerListHandler.setOnlinePlayers(0);
				for(String name : getRandomName(this.advancedFakedPlayersNames, this.getConfig().getInt("FakePlayers.OnlinePlayers"))) {
					playerListHandler.addCustomOnlinePlayer(name);
				}
				
				return;
			} else {
				Bukkit.getServer().getConsoleSender().sendMessage(this.internalPrefix + "§cNot enough random names provided for advanced faked players generation!");
				Bukkit.getServer().getConsoleSender().sendMessage(this.internalPrefix + "§cSwitching back to normal mode!");
			}
		}
		
		playerListHandler.setOnlinePlayers(this.getConfig().getInt("FakePlayers.OnlinePlayers"));
	}
	
	//Pick a random name from the list without duplicates
	private List<String> getRandomName(List<String> names, int requiredNames) {
		Random rand = new Random();
		
		List<String> newList = new ArrayList<String>();
		for(int i = 0; i < requiredNames; i++) {
			int randomIndex = rand.nextInt(names.size());
			newList.add(names.get(randomIndex));
			names.remove(randomIndex);
		}
		return newList;
	}
	
	private void initializePlayerListHandler() {
		String version = this.getServerVersion();
		if(this.protocolLib == true) {
			if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
				//Wechsel zu ProtocolLib Funktionen sollten beim Nutzer Fehler auftreten, nur temporäre Lösung! Commands funktionieren nicht in diesem Modus, eventuell später noch implementieren mal schauen?
				playerListHandler = new ProtocolLibMode();
				Bukkit.getConsoleSender().sendMessage(this.internalPrefix + "§e§lSwitched to §aProtocolLib§e§l Mode! Commands are not supported in this mode!");
			} else {
				this.protocolLib = false;
				Bukkit.getConsoleSender().sendMessage(this.internalPrefix + "§e§lProtocolLib §c§lPlugin not found! Usage of normal mode.");
				this.initializePlayerListHandler();
			}
		} else {
			if(version.equals("v1_8_R1")) {
				playerListHandler = new PlayerListHandler_1_8_R1();
			}
			if(version.equals("v1_8_R2")) {
				playerListHandler = new PlayerListHandler_1_8_R2();
			}
			if(version.equals("v1_8_R3")) {
				playerListHandler = new PlayerListHandler_1_8_R3();
			}
			if(version.equals("v1_9_R1")) {
				playerListHandler = new PlayerListHandler_1_9_R1();
			}
			if(version.equals("v1_9_R2")) {
				playerListHandler = new PlayerListHandler_1_9_R2();
			}
			if(version.equals("v1_10_R1")) {
				playerListHandler = new PlayerListHandler_1_10_R1();
			}
			if(version.equals("v1_11_R1")) {
				playerListHandler = new PlayerListHandler_1_11_R1();
			}
			if(version.equals("v1_12_R1")) {
				playerListHandler = new PlayerListHandler_1_12_R1();
			}
			if(version.equals("v1_13_R1")) {
				playerListHandler = new PlayerListHandler_1_13_R1();
			}
			if(version.equals("v1_13_R2")) {
				playerListHandler = new PlayerListHandler_1_13_R2();
			}	
			if(version.equals("v1_14_R1")) {
				playerListHandler = new PlayerListHandler_1_14_R1();
			}	
			if(version.equals("v1_15_R1")) {
				playerListHandler = new PlayerListHandler_1_15_R1();
			}
			if(version.equals("v1_16_R1")) {
				playerListHandler = new PlayerListHandler_1_16_R1();
			}
			if(version.equals("v1_16_R2")) {
				playerListHandler = new PlayerListHandler_1_16_R2();
			}
			if(version.equals("v1_16_R3")) {
				playerListHandler = new PlayerListHandler_1_16_R3();
			}
			if(TTA_BukkitVersion.getVersionAsInt(2) >= 117) {
				playerListHandler = new NMS_PlayerListHandler();
			}
		}
	}

	public void onDisable() {
		playerListHandler.removeOnlinePlayers();
		playerListHandler.removeCustomOnlinePlayers();
		Bukkit.getConsoleSender().sendMessage(this.internalPrefix + "§bVersion: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + " §cdisabled!");
	}
	
	//Depends Hook (If false FakePlayers not work!)
	private boolean CheckDepends() {
		if(Bukkit.getServer().getPluginManager().getPlugin("TTA") != null) {
			if(Bukkit.getServer().getPluginManager().getPlugin("TTA").isEnabled()) {
				return true;
			}
		}
		return false;		
	}
	
	private void getEvents() {
		Bukkit.getPluginManager().registerEvents(new PlayerLoginEventHandler(), this);
	}
	
	private void getCommands() {
		getCommand("fakePlayers").setExecutor(new Commands());
	}
		
	@SuppressWarnings("unused")
	public String getServerVersion() {
		if(version != null) {
			return version;
		}
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		String version1 = pkg.substring(pkg.lastIndexOf(".") +1);
		if(!Version_Pattern.matcher(version1).matches()) {
			version1 = "";
		}
		String version = version1;
//		return version = !version.isEmpty() ? version + "." : ""; 
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	private void sendTerms() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§4=====>[§6FakePlayers Terms§4]<=====");		
		Bukkit.getConsoleSender().sendMessage("§4-> You are not permitted to claim this plugin as your own!");
		Bukkit.getConsoleSender().sendMessage("§4-> You are not permitted to decompiling the plugin's sourcecode!");
		Bukkit.getConsoleSender().sendMessage("§4-> You are not permitted to modify the code or the plugin and call it your own!");
		Bukkit.getConsoleSender().sendMessage("§4-> You are not permitted to redistributing this plugin as your own!");
		Bukkit.getConsoleSender().sendMessage("§4======>[§aTerms Accepted!§4]<======");
		Bukkit.getConsoleSender().sendMessage("");
	}
}

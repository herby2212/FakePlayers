package de.Herbystar.FakePlayers.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

public class UUIDRecycler {
	
	public static HashMap<String, UUID> fakePlayerUUIDStorage = new HashMap<String, UUID>();
	private static File uuidStorageFile = new File("plugins/FakePlayers", "UUIDStorage.yml");
	private static String seperator = ":";

	public static void loadUUIDs() {
		YamlConfiguration uuidStorage = YamlConfiguration.loadConfiguration(uuidStorageFile);
		
		List<String> nameUUIDPairs = uuidStorage.getStringList("FakePlayerUUIDs");
		for(String pair : nameUUIDPairs) {
			String fakePlayerName = pair.split(seperator)[0];
			UUID fakePlayerUUID = UUID.fromString(pair.split(seperator)[1]);
			
			fakePlayerUUIDStorage.put(fakePlayerName, fakePlayerUUID);
		}
	}
	
	public static void saveUUIDs() {
		YamlConfiguration uuidStorage = YamlConfiguration.loadConfiguration(uuidStorageFile);
		
		List<String> nameUUIDPairs = uuidStorage.getStringList("FakePlayerUUIDs");
		for(String key : fakePlayerUUIDStorage.keySet()) {
			String entry = key + seperator + fakePlayerUUIDStorage.get(key);
			if(!nameUUIDPairs.contains(entry)) {
				nameUUIDPairs.add(entry);
			}
		}
		uuidStorage.set("FakePlayerUUIDs", nameUUIDPairs);
		saveConfig(uuidStorage, uuidStorageFile);
	}
	
	private static void saveConfig(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}

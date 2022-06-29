package de.Herbystar.FakePlayers.Utilities;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

public class UUIDRecycler {
	
	public static HashMap<String, UUID> fakePlayerUUIDStorage = new HashMap<String, UUID>();
	private static File uuidStorageFile = new File("plugins/FakePlayers", "UUIDStorage.yml");

	public static void loadUUIDs() {
		YamlConfiguration uuidStorage = YamlConfiguration.loadConfiguration(uuidStorageFile);
		
		List<String> nameUUIDPairs = uuidStorage.getStringList("FakePlayerUUIDs");
		for(String pair : nameUUIDPairs) {
			String fakePlayerName = pair.split("-")[0];
			UUID fakePlayerUUID = UUID.fromString(pair.split("-")[1]);
			
			fakePlayerUUIDStorage.put(fakePlayerName, fakePlayerUUID);
		}
	}
	
}

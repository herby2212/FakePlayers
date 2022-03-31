package de.Herbystar.FakePlayers.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

import de.Herbystar.FakePlayers.Main;

public class RandomUUID {
	
	private static List<UUID> check = new ArrayList<UUID>();
	
	
	//Returns random UUID Version 4. Can result in Steve Skin in Tablist.
	public static UUID randomUUID() {
		UUID random = UUID.randomUUID();
		
		while(check.contains(random)) {
			random = UUID.randomUUID();
		}
		check.add(random);
		return random;
	}
	
	/*
	 * 
	 * Return random UUID Version 1. 
	 * Result always in Alex Skin in Tablist. (Not confirmed!)
	 * 
	 */
	public static UUID randomUUIDv1(String name) {
		UUID random = null;
		try {
			random = randomUUIDv1Local(name);
		} catch (UnsupportedEncodingException e) {
			Bukkit.getConsoleSender().sendMessage(Main.instance.internalPrefix + "§cLocal UUID generation failed, fallback to online method!");
			try { random = randomUUIDv1Online(); } catch (IOException ex) { ex.printStackTrace(); }
		}
		
		while(check.contains(random)) {
			try {
				random = randomUUIDv1Online();
			} catch (IOException ex) { ex.printStackTrace(); }
		}
		check.add(random);
		return random;
	}
	
	//Generated via online source.
	private static UUID randomUUIDv1Online() throws MalformedURLException, IOException {
		HttpURLConnection con = (HttpURLConnection) new URL("https://www.uuidgenerator.net/api/version1").openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("GET");
		String content = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		
		UUID random = UUID.fromString(content);
		
		return random;
	}

	//Generated via local variables. Only 1 UUID per name.
	private static UUID randomUUIDv1Local(String name) throws UnsupportedEncodingException {
		String source = "herbystar.eu" + name;
		byte[] bytes = source.getBytes("UTF-8");
		UUID random =  UUID.nameUUIDFromBytes(bytes);

		return random;
	}

}

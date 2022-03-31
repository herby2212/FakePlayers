package de.Herbystar.FakePlayers.PlayerListHandler;

public interface PlayerListHandler {
		
	public void setOnlinePlayers(int amount);
	
	public void removeOnlinePlayers();
	
	public void removeCustomOnlinePlayers();
	
	public void addCustomOnlinePlayer(String name);
	
	public void removeCustomOnlinePlayer(String name);
	
}

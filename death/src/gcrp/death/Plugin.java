package gcrp.death;

import java.util.logging.Logger;

public class Plugin extends org.bukkit.plugin.java.JavaPlugin {

	Logger 		logger; 
	
	Listener 	listener;
	
	DeathBook	db;

	
	@Override
	public void onEnable() {
		listener = new Listener(this);
		getDB().load();
		getLogger().info(getDescription().getName() + " activated");
	}

	
	@Override
	public void onDisable() {
		getDB().save();
		getLogger().info(getDescription().getName() + " deactivated");
	}
	
	
	public Logger getLogger() {
		if (null == logger) {
			logger = Logger.getLogger("Minecraft");
		}
		return logger;
	}
	
	
	public DeathBook getDB() {
		if (null == db) {
			db = new DeathBook(this);
		}
		return db;
	}
}

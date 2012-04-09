package gcrp.death;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class DeathBook {
		
	public static final String FILENAME = "DeathBook.yml";  
	
	private Map<String, GhostRecord> records;
	
	Plugin plugin;
	String worldName;
	
	public DeathBook(Plugin plugin) {
		this.plugin = plugin; 
	}

	public final File getFile() {
		return new File(plugin.getDataFolder(), FILENAME);
	}
	
	public void init() {
		records = new HashMap<String, GhostRecord>();
	}
	
	public void load() {
		init();
		try {
			File file = getFile();
			if (!file.exists()) {
				return;
			}
			FileConfiguration data = new YamlConfiguration();
			data.load(file);
			for (String k: data.getKeys(false)) {
				records.put(k, new GhostRecord(data, k));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void save() {
		try {
			File file = getFile();
			File basedir = file.getParentFile(); 
			if (basedir.exists()) {
				if (!basedir.isDirectory()) {
					throw new Exception("not a directory: " + basedir.getName());
				}
			} else {
				if (!basedir.mkdirs()) {
					throw new Exception("mkdirs error for " + basedir.getName());
				}
			}
			FileConfiguration data = new YamlConfiguration();
			for (GhostRecord r : records.values()) {
				r.save(data);
			}
			data.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public final boolean isAGhost(Player player) {
		return records.containsKey(player.getName());
	}
	
	public GhostRecord getRecord(Player player) {
		return records.get(player.getName()); 
	}
	
	public void addRecord(Player player) {	
		records.put(player.getName(), 
				new GhostRecord(player.getName(), player.getLocation())); 
	}

	public void removeRecord(Player player) {
		records.remove(player.getName()); 
	}

}

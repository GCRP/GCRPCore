package gcrp.TestBox;

import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestBox extends JavaPlugin implements Listener {
	
	Logger log = Logger.getLogger("Minecraft");
	
	public void onEnable() {
		log.info("TestBox has been enabled");
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		log.info("TestBox has been disabled");
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		log.info("player " + event.getPlayer().getDisplayName() + " logged in");
	}

	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isSneaking()) {
			log.info("player " + event.getPlayer().getDisplayName() + " hiding");		
		} else {
			log.info("player " + event.getPlayer().getDisplayName() + " unhiding");
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity(); 
		if (entity instanceof Player) {
		} else {
			log.info("creature #" + entity.getEntityId() + " died");
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		log.info("player " + p.getDisplayName() + " is dead!");		
	}
}

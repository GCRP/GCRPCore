package gcrp.TestBox;

import java.util.logging.Logger;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class TestBox extends JavaPlugin implements Listener {

	static final String META_DEAD_FLAG = "gcrp_dead";
	static final String META_PIT_BIRTH = "gcrp_birthday";
	static final String META_PIT_DEATH = "gcrp_deathday";
	
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
		Player p = event.getPlayer(); 
		log.info("player " + p.getDisplayName() + " logged in");
		if (p.hasMetadata(META_DEAD_FLAG)) {
			for (MetadataValue v : p.getMetadata(META_DEAD_FLAG)) {
				log.info("... DEAD_FLAG {" + v.asString() + "}");
			}
		} else {
			log.info("NO DEAD_FLAG ");
		}
		if (p.hasMetadata(META_PIT_DEATH)) {
			for (MetadataValue v : p.getMetadata(META_PIT_DEATH)) {
				log.info("... PIT_DEATH {" + v.asString() + "}");
			}
		} else {
			log.info("NO PIT_DEATH");
		}
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
		p.setBedSpawnLocation(p.getLocation());
		p.setMetadata(META_DEAD_FLAG, new FixedMetadataValue(this, 1));
		p.setMetadata(META_PIT_DEATH, new FixedMetadataValue(this, new java.util.Date()));
		
	}
}

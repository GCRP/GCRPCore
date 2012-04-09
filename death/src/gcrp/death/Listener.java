package gcrp.death;

import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public class Listener implements org.bukkit.event.Listener {
	
	Plugin plugin;
	DeathBook db;
	Logger logger;
	
	Listener(Plugin plugin) {
		this.plugin = plugin;
		this.db = plugin.getDB();
		this.logger = plugin.getLogger();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (db.isAGhost(player)) {
			ghostify(player);
		}
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		db.addRecord(player);
		event.setDeathMessage("You're died :(");
		ghostify(player);
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onEntityTarget(EntityTargetEvent event) {
		// Do not look at ghost!
		if (event.getTarget() instanceof Player) {
			if (db.isAGhost((Player)(event.getTarget()))) {
				event.setCancelled(true);
				return;
			}
		}
	}

	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent event) {
		Entity target   = event.getEntity();
		
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
			Entity damager  = e.getDamager();
			
			if (damager instanceof Projectile) {
				Projectile p = (Projectile)damager;
				LivingEntity shooter = p.getShooter();
				if (shooter instanceof Player) {
					damager = (Player)shooter;
				}
			}
			
			if (damager instanceof Player) {
				Player p = (Player)damager;
				if (db.isAGhost(p)) {
					event.setCancelled(true);
					p.chat("you are ghost and cannot hurt anything");
					return;
				}
			}
		}
		
		if (target instanceof Player) {
			Player p = (Player)target;
			if (db.isAGhost(p)) {
				event.setCancelled(true);
//				p.chat("you are ghost and invulnerable");
				return;
			}
		}
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player)(event.getEntity());
			if (db.isAGhost(p)) {
				event.setCancelled(true);
				return;
			}
		}
    }
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onPlayerChat(PlayerChatEvent event) {
    	Player player = event.getPlayer();
    	if (db.isAGhost(player)) {
    		event.setCancelled(true);
			player.chat("you are ghost and should be silent");
    		return;
    	}
    }


	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    	Player player = event.getPlayer();
    	String text = event.getMessage().toLowerCase();
    	if (db.isAGhost(player) && text.startsWith("/me")) {
    		event.setCancelled(true);
			player.chat("you are ghost and should be silent");
    		return;
    	}
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	if (db.isAGhost(player)) {
    		event.setCancelled(true);
			player.chat("you are ghost and cannot interact with world");
    		return;
    	}
    }
    
	@EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onBucketFill(PlayerBucketFillEvent event) {
    	Player player = event.getPlayer();
    	if (db.isAGhost(player)) {
    		event.setCancelled(true);
			player.chat("you are ghost");
    		return;
    	}
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    	Player player = event.getPlayer();
    	if (db.isAGhost(player)) {
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
		Player player = event.getPlayer();
		if (db.isAGhost(player)) {
			event.setCancelled(true);
			player.chat("you are ghost");
			return;
		}
    }
    

	////////////////////////////////////////////////////////////////////////
	
    void vanish(Player player) {
    	for (Player other : plugin.getServer().getOnlinePlayers()) {
    		other.hidePlayer(player);
    	}
    }

    void reveal(Player player) {
    	for (Player other : plugin.getServer().getOnlinePlayers()) {
    		other.showPlayer(player);
    	}
    }
    
	void ghostify(Player player) {
		logger.info("ghostify(" + player.getName() + ")");
		vanish(player);
		player.setGameMode(GameMode.CREATIVE);
		player.setAllowFlight(true);
		player.chat("KEEP IN MIND: YOU ARE GHOST.");
		
	}
	
	
	void deghostify(Player player) {
		logger.info("deghostify(" + player.getName() + ")");
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(false);
		player.chat("YOU ARE ALIVE.");
		reveal(player);
	}
}

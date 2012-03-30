package gcrp.TestSneaking;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TestSneaking extends JavaPlugin implements Listener {

	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		log.info(getClass().getSimpleName() + " has been enabled");
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {
		log.info(getClass().getSimpleName() + " has been disabled");
	}

	boolean checkVisibility(Player player, Player sneaker) {
		boolean visible = player.canSee(sneaker); 
		if (visible) {
			log.info("sneaker " + sneaker.getDisplayName()
					+ " is visible for " + player.getDisplayName());
		} else {
			log.info("sneaker " + sneaker.getDisplayName()
					+ " is invisible for " + player.getDisplayName());
		}
		return visible;
	}
	
	void hidePlayer(Player sneaker) {
		for (Player player : getServer().getOnlinePlayers()) {
			checkVisibility(player, sneaker);
			log.info("hide " + sneaker.getDisplayName()
					+ " for " + player.getDisplayName());
			player.hidePlayer(sneaker);
		}
	}
	
	void unhidePlayer(Player sneaker) {
		for (Player player : getServer().getOnlinePlayers()) {
			log.info("show " + sneaker.getDisplayName()
					+ " for " + player.getDisplayName());
			player.showPlayer(sneaker);
		}
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		log.info("onPlayerToggleSneak(" + event.toString() + ")");
		if (event.isSneaking()) {
			hidePlayer(event.getPlayer());
		} else {
			unhidePlayer(event.getPlayer());
		}
	}

}

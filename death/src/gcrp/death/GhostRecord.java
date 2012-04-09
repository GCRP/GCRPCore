package gcrp.death;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class GhostRecord implements Serializable {
	private static final long serialVersionUID = -20120507141822L;
	private static final String kPLAYER = "player";
	private static final String kWORLD = "world";
	private static final String kPOSITION = "position";
	private static final String kPITCH = "pitch";
	private static final String kYAW = "yaw";
	private static final String kRADIUS = "radius";
	private static final String kPIT = "pit";
	
	public static final String  UNKNOWN = "[[UNKNOWN]]";
	public static double defaultRadius = 50.0;

	private static final String PIT_FMT = "yyyyMMddHHmmss";
	private SimpleDateFormat sdf = new SimpleDateFormat(PIT_FMT);

	String playerName;
	String worldName;
	Vector corpsePosition;
	double corpseYaw;
	double corpsePitch;
	double cageRadius;
	Date pitDeath;

	public GhostRecord() {
	}

	public GhostRecord(String _name, Location _location) {
		setPlayerName(_name);
		setLocation(_location);
		setPITDeath(new Date());
	}

	public GhostRecord(Configuration book, String _name) {
		load(book, _name);
	}

	public final boolean isNull() {
		return (null == playerName || 
				null == worldName ||
				null == corpsePosition ||
				null == pitDeath);
	}
	
	public final String getPlayerName() {
		return playerName;
	}

	public final String getWorldName() {
		return worldName;
	}

	public final Vector getCorpsePosition() {
		return corpsePosition;
	}

	public final double getX() {
		return getCorpsePosition().getX();
	}

	public final double getY() {
		return getCorpsePosition().getY();
	}

	public final double getZ() {
		return getCorpsePosition().getZ();
	}

	public final double getPitch() {
		return corpsePitch;
	}

	public final double getYaw() {
		return corpseYaw;
	}

	public final Location getLocation() {
		try {
			World world = Bukkit.getServer().getWorld(worldName);
			if (null == world) {
				throw new Exception(worldName);
			}
			return getCorpsePosition().toLocation(world,
					(float) getYaw(), (float) getPitch());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public final Date getPITDeath() {
		return pitDeath;
	}

	public void setPlayerName(final String newName) {
		playerName = newName;
	}

	public void setLocation(final Location newLocation) {
		worldName = newLocation.getWorld().getName();
		corpsePosition = new Vector(newLocation.getX(), newLocation.getY(),
				newLocation.getZ());
		corpseYaw = newLocation.getYaw();
		corpsePitch = newLocation.getPitch();
	}
	
	public void setPITDeath(final Date newPIT) {
		pitDeath = newPIT;
	}

	public final boolean save(Configuration book) {
		try {
			ConfigurationSection section = book.createSection(playerName);
			if (null == section) {
				throw new Exception("section {" + playerName
						+ "} has not created");
			}
			section.set(kPLAYER, playerName);
			section.set(kWORLD, worldName);
			section.set(kPOSITION, corpsePosition);
			section.set(kYAW, new Double(corpseYaw));
			section.set(kPITCH, new Double(corpsePitch));
			section.set(kRADIUS, new Double(cageRadius));
			section.set(kPIT, sdf.format(pitDeath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean load(Configuration book, String _name) {
		try {
			ConfigurationSection section = book.getConfigurationSection(_name);
			if (null == section) {
				throw new Exception("section {" + _name + "} is missing");
			}
			playerName = section.getString(kPLAYER);
			worldName = section.getString(kWORLD);
			corpsePosition = section.getVector(kPOSITION);
			corpseYaw 	= section.getDouble(kYAW);
			corpsePitch = section.getDouble(kPITCH);
			cageRadius 	= section.getDouble(kRADIUS);
			pitDeath 	= sdf.parse(section.getString(kPIT));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

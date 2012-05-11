package gcrp.death;

import org.bukkit.World;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;

public class CorpseEntity extends EntityPlayer {
	public CorpseEntity(
			MinecraftServer minecraftserver, 
			World world, String s, 
			ItemInWorldManager iteminworldmanager) {
        super(minecraftserver, world, s, iteminworldmanager);
	}


}

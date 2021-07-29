package io.github.oyberntzen.cocoplugin;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CoCoPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("WELCOME TO COCOCRAFT!");
	}

	@Override
	public void onDisable() {
		getLogger().info("COCOCRAFT IS GETTING CLOSED FOR NOT LONG. WE WILL BE RIGHT BACK!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("randomize")) {
			if (sender instanceof Player) {
				if (args.length <= 1) {
					return false;
				}
				
				Player player = (Player)sender;
				Location location = player.getLocation();
				
				int playerX = location.getBlockX();
				int playerY = location.getBlockY() - 1;
				int playerZ = location.getBlockZ(); 
				
				int radius = Integer.parseInt(args[0]);
				if (radius > 100) {
					player.sendMessage("Radius can not be more than 100!");
					return true;
				}
				int numberOfBlocks = args.length - 1;
				
				for (int x = -radius; x < radius; x++) {
					for (int z = -radius; z < radius; z++) {
						int number = ThreadLocalRandom.current().nextInt(1, numberOfBlocks + 1);
						Material material = Material.getMaterial(args[number]);
						if (material == null) {
							player.sendMessage(args[number] + " is not a block!");
							return true;
						}
						player.getWorld().getBlockAt(playerX + x, playerY, playerZ + z).setType(material);
					}
				}
				
				return true;
			} else {
				sender.sendMessage("Only players can use this command!");
				return true;
			}
		}
		
		return false;
	}
}

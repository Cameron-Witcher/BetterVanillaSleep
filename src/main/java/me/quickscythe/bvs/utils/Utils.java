package me.quickscythe.bvs.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;

import me.quickscythe.bvs.Main;
import net.md_5.bungee.api.ChatColor;

public class Utils {

	private static Main plugin;
	private static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	static boolean skipping = false;
	private static double requiredSleeping = 0.33;

	public static void init(Main main) {
		plugin = main;
		if (plugin.getConfig().isSet("PlayersSleepingPercentage"))
			requiredSleeping = plugin.getConfig().getDouble("PlayersSleepingPercentage");
		else {
			plugin.getConfig().set("PlayersSleepingPercentage", requiredSleeping);
			plugin.saveConfig();
		}
	}

	public static Main getPlugin() {
		return plugin;
	}

	public static Essentials getEssentials() {
		return ess;
	}
	
	public static double getRequiredPercent() {
		return requiredSleeping;
	}

	public static double getSleepingPercent(World world) {
		double sleeping = 0;
		double nos = 0;
		for (Player player : world.getPlayers()) {
			if (Utils.getEssentials().getUser(player).isAfk() || Utils.getEssentials().getUser(player).isVanished())
				nos = nos + 1;
			if (player.isSleeping())
				sleeping = sleeping + 1;
		}
		return sleeping / (world.getPlayers().size() - nos);
	}

	public static void skipToDayTimer(World world) {
		if (!skipping) {
			Bukkit.broadcastMessage(colorize("&eSleeping through this night."));
			skipping = true;
		}
		Bukkit.getScheduler().runTaskLater(Utils.getPlugin(), () -> {
			skipping = false;
			if (getSleepingPercent(world) >= getRequiredPercent())
				world.setTime(0);

		}, 5 * 20);
	}

	public static String colorize(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

}

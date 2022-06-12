package me.quickscythe.bvs.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import me.quickscythe.bvs.Main;
import me.quickscythe.bvs.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerListener implements Listener {

	public PlayerListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		Bukkit.getScheduler().runTaskLater(Utils.getPlugin(), () -> {

			double percent = Utils.getSleepingPercent(e.getPlayer().getWorld());
			Bukkit.broadcastMessage(Utils.colorize("&6" + e.getPlayer().getName() + "&e is sleeping."));
			for (Player player : e.getPlayer().getWorld().getPlayers()) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
						Utils.colorize("&6" + ((int) (percent * 100)) + "%&e of players are sleeping.")));
			}
			if (percent >= 0.33) {
				Utils.skipToDayTimer(e.getPlayer().getWorld());
			}

		}, 0);
	}

}

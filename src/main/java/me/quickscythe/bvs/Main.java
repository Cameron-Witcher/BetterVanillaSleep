package me.quickscythe.bvs;

import org.bukkit.plugin.java.JavaPlugin;

import me.quickscythe.bvs.listener.PlayerListener;
import me.quickscythe.bvs.utils.Utils;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		Utils.init(this);
		new PlayerListener(this);
	}

}

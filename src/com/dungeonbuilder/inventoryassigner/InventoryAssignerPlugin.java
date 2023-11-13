package com.dungeonbuilder.inventoryassigner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryAssignerPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new GUIEventHandler(), this);
	}

	@Override
	public void onDisable() {}
}

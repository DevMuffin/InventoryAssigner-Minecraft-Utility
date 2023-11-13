package com.dungeonbuilder.inventoryassigner;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import com.dungeonbuilder.utils.inventory.GUI;
import com.dungeonbuilder.utils.item.InventoryItem;
import com.dungeonbuilder.utils.item.ItemBuilderParamd.InventoryItemClickEvent;

public class GUIEventHandler implements Listener {

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		UUID playerID = player.getUniqueId();
		HashSet<InventoryItem> set = GUI.getAssignedTo(playerID);
		if (set == null)
			return;
		ItemStack currentItem = e.getCurrentItem();
		if (currentItem == null || currentItem.getType().equals(Material.AIR)) {
			return;
		}
		for (InventoryItem invItem : set) {
			InventoryItemClickEvent invItemClickEvent = new InventoryItemClickEvent(invItem, e);
			if (invItem.equalsBukkit(currentItem)) {
				e.setCancelled(true);
				invItem.clickOpt.ifPresent(clickConsumer -> clickConsumer.accept(invItemClickEvent));
				break;
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInvClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		UUID playerID = player.getUniqueId();
		GUI.unassignAll(playerID);
	}
}

package com.quzzar.ge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.quzzar.ge.Buying.BuyingManager;

public class GrandExchangeMenu {
	
	private static Inventory menu = null;
	
	public static void initialize() {
		
		menu = Bukkit.createInventory(null, InventoryType.DROPPER, ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Grand Exchange");
		
		Utility.fillMenu(menu);
		
		menu.setItem(3, ItemsCreator.Buy_Button());
		
		menu.setItem(5, ItemsCreator.Sell_Button());
		
		TradeWindows.addToTradeInvs(menu);
		
		///
		
		BuyingManager.initialize();
		
	}
	
	public static Inventory getMenu() {
		return menu;
	}
	
}

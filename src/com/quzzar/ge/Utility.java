package com.quzzar.ge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utility {
	
	
	public static void tellConsole(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"["+ChatColor.LIGHT_PURPLE+"Grand Exchange"+ChatColor.DARK_PURPLE+"]"+ChatColor.GREEN+" "+message);
	}
	
	public static void tellSender(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.DARK_PURPLE+"["+ChatColor.LIGHT_PURPLE+"Grand Exchange"+ChatColor.DARK_PURPLE+"] "+message);
	}
	
	
	public static void fillMenu(Inventory inv) {
		for(int i=0; i<inv.getSize(); i++){
			
			if(inv.getItem(i)==null) {
				inv.setItem(i, ItemsCreator.Menu_Filler());
			}
			
		}
	}
	
	public static void createBorder(Inventory inv) {
		for(int i=0; i<inv.getSize(); i++){
			
			if(i<9) {
				inv.setItem(i, ItemsCreator.Generic_Footer());
			} else if(i>=inv.getSize()-9) {
				inv.setItem(i, ItemsCreator.Generic_Footer());
			}
			
		}
		
	}
	
	
	public static boolean inventoryFull(Inventory inv, ItemStack item){
		
		boolean full = false;
		
		if(inv.firstEmpty()==-1){
			for(ItemStack i : inv.getContents()){
				if(i!=null){
					if(i.isSimilar(item)&&i.getAmount()<i.getMaxStackSize()){
						return false;
					}else{
						full = true;
					}
				}
			}
		}
		
		return full;
	}
	
	
	public static boolean addItemToInventory(Inventory inv, ItemStack i, Location loc){
		if(inventoryFull(inv, i)){
			loc.getWorld().dropItem(loc, i);
			return false;
		}else{
			inv.addItem(i);
			return true;
		}
	}
	
	
}

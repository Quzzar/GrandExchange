package com.quzzar.ge.Buying;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuyingUtil {
	
	
	
	public static ItemStack makeBuyItem(ItemStack i, int price, String sellerName){
		
		ItemStack item = i.clone();
		
	    ItemMeta im = item.getItemMeta();
	    
	    ArrayList<String> lore = new ArrayList<String>();
	    
	    if(price!=1) {
	    	lore.add(ChatColor.BLUE+""+ChatColor.BOLD+"Price: "+ChatColor.RESET+""+ChatColor.GREEN+price+" Gems");
	    } else {
	    	lore.add(ChatColor.BLUE+""+ChatColor.BOLD+"Price: "+ChatColor.RESET+""+ChatColor.GREEN+price+" Gem");
	    }
	    
	    lore.add(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Seller: "+ChatColor.RESET+""+ChatColor.DARK_GRAY+sellerName);
	    im.setLore(lore);
	    
	    item.setItemMeta(im);
		
	    return item;
	}
	
	public static String getItemName(ItemStack item){
		
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			return ChatColor.stripColor(item.getItemMeta().getDisplayName());
		} else {
			String name = item.getType().toString();
			name = name.replaceAll("_", " "); name = WordUtils.capitalizeFully(name);
			return name;
		}
		
	}
	
}

package com.quzzar.ge.Selling;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SellingUtil {
	
	
	public static ArrayList<SellingItem> getPlayersSellingItems(OfflinePlayer p) {
		
		ArrayList<SellingItem> sellingItems = new ArrayList<SellingItem>();
		
		for(SellingItem sale : SellManager.itemsSelling) {
			if(sale.getSeller()!=null && sale.getSeller().getUniqueId().equals(p.getUniqueId())) {
				
				sellingItems.add(sale);
				
			}
		}
		
		return sellingItems;
		
	}
	
	public static ItemStack makeSellItem(ItemStack i, SellStage stage){
		
		ItemStack item = i.clone();
		
	    ItemMeta im = item.getItemMeta();
	    
	    ArrayList<String> lore = new ArrayList<String>();
	    
	    if(stage.equals(SellStage.PROCESSING)) {
	    	lore.add(ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Selling: "+ChatColor.RESET+ChatColor.GOLD+"PENDING");
	    } else if(stage.equals(SellStage.ACCEPTED)) {
	    	lore.add(ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Selling: "+ChatColor.RESET+ChatColor.GREEN+"SOLD");
	    } else if(stage.equals(SellStage.FAILED)) {
	    	lore.add(ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Selling: "+ChatColor.RESET+ChatColor.RED+"CANCELLED");
	    } else if(stage.equals(SellStage.NONE)) {
	    	lore.add(ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Selling: "+ChatColor.RESET+ChatColor.DARK_RED+"ERROR");
	    }
	    
	    im.setLore(lore);
	    
	    item.setItemMeta(im);
		
	    return item;
	}
	
	
	public static int getTotalGems(SellingItem sale) {
		return sale.getPriceSingle()+sale.getPriceBlock()*9;
	}
	
	public static ItemStack makeGemPackage(SellingItem sale) {
		
		ItemStack i = new ItemStack(Material.EMERALD, 1);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.GREEN+""+getTotalGems(sale)+" Gem(s)");
	    i.setItemMeta(im);
	    
	    return i;
	}
	
	
	public static SellingItem getSale(Inventory inv, Player p, SellStage stage) {
		
		for(SellingItem sale : getPlayersSellingItems(p)) {
			
			if(sale.getStage().equals(stage)) {
				
				if(ChatColor.stripColor(inv.getItem(8).getItemMeta().getDisplayName()).equals(sale.getUUID().toString())) {
					
					return sale;
					
				}
				
			}
			
		}
		
		return null;
	}
	
}

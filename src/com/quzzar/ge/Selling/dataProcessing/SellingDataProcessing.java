package com.quzzar.ge.Selling.dataProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import com.quzzar.ge.ConfigManager;
import com.quzzar.ge.Selling.SellManager;
import com.quzzar.ge.Selling.SellStage;
import com.quzzar.ge.Selling.SellingItem;

public class SellingDataProcessing {
	
	
	public static void encrypt() {
		
		ConfigManager.getInstance().getData().set("SellingItems", null);
		ConfigManager.getInstance().getData().set("SellingItems_List", null);
		
		ConfigManager.getInstance().saveData();
		
		List<String> idList = new ArrayList<String>();
		
		for(SellingItem sale : SellManager.itemsSelling) {
			
			idList.add(sale.getUUID().toString());
			
			ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".Stage", sale.getStage().name());
			if(sale.getSeller()!=null) {
				ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".Seller", sale.getSeller().getUniqueId().toString());
			} else {
				ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".Seller", "Unknown");
			}
			ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".Item", sale.getSellingItem());
			ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".PriceSingle", sale.getPriceSingle());
			ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".PriceBlock", sale.getPriceBlock());
			ConfigManager.getInstance().getData().set("SellingItems."+sale.getUUID()+".SaleTime", sale.getSellingTime());
			
		}
		
		ConfigManager.getInstance().getData().set("SellingItems_List", idList);
		
		ConfigManager.getInstance().saveData();
		
	}
	
	
	public static void decrypt() {
		
		List<String> idList = ConfigManager.getInstance().getData().getStringList("SellingItems_List");
		
		for(String strID : idList) {
			
			
			String stageName = ConfigManager.getInstance().getData().getString("SellingItems."+strID+".Stage");
			String playerID = ConfigManager.getInstance().getData().getString("SellingItems."+strID+".Seller");
			ItemStack item = ConfigManager.getInstance().getData().getItemStack("SellingItems."+strID+".Item");
			int priceSingle = ConfigManager.getInstance().getData().getInt("SellingItems."+strID+".PriceSingle");
			int priceBlock = ConfigManager.getInstance().getData().getInt("SellingItems."+strID+".PriceBlock");
			int saleTime = ConfigManager.getInstance().getData().getInt("SellingItems."+strID+".SaleTime");
			
			OfflinePlayer p = null;
			if(!playerID.equals("Unknown")) {
				p = Bukkit.getOfflinePlayer(UUID.fromString(playerID));
			}
			SellStage stage = SellStage.NONE;
			
			for(SellStage stg : SellStage.values()) {
				if(stg.name().equalsIgnoreCase(stageName)) {
					stage = stg;
				}
			}
			
			SellingItem sale = new SellingItem(stage, p, item, priceSingle, priceBlock);
			sale.setSellingTime(saleTime);
			
			SellManager.itemsSelling.add(sale);
			
		}
		
		
	}
	
	
}

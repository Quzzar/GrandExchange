package com.quzzar.ge.Buying;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import com.quzzar.ge.GrandExchangeMenu;
import com.quzzar.ge.ItemsCreator;
import com.quzzar.ge.Utility;
import com.quzzar.ge.Selling.SellManager;
import com.quzzar.ge.Selling.SellStage;
import com.quzzar.ge.Selling.SellingItem;
import com.quzzar.ge.Selling.SellingUtil;

public class BuyingManager {
	
	private static ArrayList<Inventory> pages = new ArrayList<Inventory>();
	
	private static int size = 54;
	private static String title = ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Buying " + ChatColor.RESET + "Pg. ";
	
	public static void initialize() {
		
		for(int n = 1; n<100; n++) {
			pages.add(Bukkit.createInventory(null, size, title+n));
		}
		
		updateBuyMenu();
		
	}
	
	public static ArrayList<Inventory> getBuyPages() {
		return pages;
	}
	
	
	public static void updateBuyMenu() {
		
		for(Inventory page : getBuyPages()) {
			page.clear();
		}
		
		
		///
		
		ArrayList<SellingItem> itemsOnSale = new ArrayList<SellingItem>();
		
		for(SellingItem sale : SellManager.itemsSelling) {
			if(sale.getStage().equals(SellStage.PROCESSING)) {
				itemsOnSale.add(sale);
			}
		}
		
		
		Collections.sort(itemsOnSale, new Comparator<SellingItem>() {
		    public int compare(SellingItem s1, SellingItem s2) {
		        return BuyingUtil.getItemName(s1.getSellingItem()).compareTo(BuyingUtil.getItemName(s2.getSellingItem()));
		    }
		});
		
		
		///
		
		int saleNum = 0;
		
		int pagesNum = (int) Math.ceil(itemsOnSale.size()/36.0);
		
		
		if(pagesNum==0) {
			
			Inventory page = getBuyPages().get(0);
			
			Utility.createBorder(page);
			
			page.setItem(0, ItemsCreator.Back_Button(GrandExchangeMenu.getMenu()));
			
			Utility.fillMenu(page);
			
			pages.add(page);
			
			return;
			
		}
		
		for(int pgCount = 0; pgCount<pagesNum; pgCount++) {
			
			Inventory page = getBuyPages().get(pgCount);
			
			Utility.createBorder(page);
			
			page.setItem(0, ItemsCreator.Back_Button(GrandExchangeMenu.getMenu()));
			
			int slotToCheck = -1;
			
			addingItem:
			while(saleNum<itemsOnSale.size()) {
				
				SellingItem sale = itemsOnSale.get(saleNum);
				
				saleNum++;
				
				do {
					
					slotToCheck++;
					
					if(slotToCheck>=page.getSize()) {
						break addingItem;
					}
					
				}while(page.getItem(slotToCheck)!=null);
				
				String sellerName = "Unknown";
				
				if(sale.getSeller()!=null) {
					sellerName = sale.getSeller().getName();
				}
				
				page.setItem(slotToCheck, BuyingUtil.makeBuyItem(sale.getSellingItem(), SellingUtil.getTotalGems(sale), sellerName));
				
				
			}
			
			Utility.fillMenu(page);
			
			pages.add(page);
			
			
		}
		
		
		for(int n = 0; n<pagesNum; n++) {
			
			if(n+1<pagesNum) {
				getBuyPages().get(n).setItem(8, ItemsCreator.Next_Button(getBuyPages().get(n+1)));
			}
			
			if(n!=0) {
				getBuyPages().get(n).setItem(0, ItemsCreator.Back_Button(getBuyPages().get(n-1)));
			}
			
		}
		
		
	}
	
	
	
}

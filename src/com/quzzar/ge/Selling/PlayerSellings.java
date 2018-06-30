package com.quzzar.ge.Selling;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.quzzar.ge.GrandExchangeMenu;
import com.quzzar.ge.ItemsCreator;
import com.quzzar.ge.TradeWindows;
import com.quzzar.ge.Utility;

public class PlayerSellings {
	
	
	private Inventory inv;
	private Player p;
	
	private ArrayList<SellingItem> sellingItems;
	
	public PlayerSellings(Player p) {
		
		inv = Bukkit.createInventory(null, PlayerSellSizeHandler.getSellSize(p), 
				ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Selling "+ChatColor.RESET+p.getDisplayName());
		
		TradeWindows.addToTradeInvs(inv);
		
		this.p = p;
		
		update();
		
	}
	
	public Inventory getSellInv() {
		return inv;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public ArrayList<SellingItem> getSellingItems() {
		return sellingItems;
	}
	
	
	public void update() {
		
		this.inv.clear();
		
		this.sellingItems = SellingUtil.getPlayersSellingItems(getPlayer());
		
		Utility.createBorder(getSellInv());
		
		this.inv.setItem(0, ItemsCreator.Back_Button(GrandExchangeMenu.getMenu()));
		this.inv.setItem(4, ItemsCreator.Add_New_Sale_Button());
		
		for(SellingItem sale : sellingItems) {
			
			placeItem:
			for(int n = 0; n<getSellInv().getSize(); n++) {
				
				if(getSellInv().getItem(n)==null) {
					
					getSellInv().setItem(n,SellingUtil.makeSellItem(sale.getSellingItem(), sale.getStage()));
					break placeItem;
					
				}
				
			}
			
		}
		
		Utility.fillMenu(getSellInv());
		
	}
	
	
}

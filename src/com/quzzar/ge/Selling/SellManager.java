package com.quzzar.ge.Selling;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class SellManager {
	
	public static ArrayList<SellingItem> itemsSelling = new ArrayList<SellingItem>();
	public static ArrayList<PlayerSellings> playerSellingsList = new ArrayList<PlayerSellings>();
	
	
	
	
	
	
	
	public static PlayerSellings getPlayerSellings(Player p) {
		
		for(PlayerSellings pSell : playerSellingsList) {
			
			if(pSell.getPlayer().equals(p)) {
				
				pSell.update();
				
				return pSell;
			}
		}
		
		PlayerSellings pSell = new PlayerSellings(p);
		playerSellingsList.add(pSell);
		
		return pSell;
	}
	
}

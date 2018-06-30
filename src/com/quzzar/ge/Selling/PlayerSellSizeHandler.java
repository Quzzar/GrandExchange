package com.quzzar.ge.Selling;

import org.bukkit.entity.Player;

public class PlayerSellSizeHandler {
	
	public static int getSellSize(Player p) {
		
		if(p.hasPermission("grandexchange.sellsize.gold")) {
			return 54;
		}
		if(p.hasPermission("grandexchange.sellsize.silver")) {
			return 45;
		}
		return 36;
		
	}
	
}

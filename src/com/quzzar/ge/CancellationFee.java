package com.quzzar.ge;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.atlasmc.gems.API;
import com.quzzar.ge.Selling.SellStage;
import com.quzzar.ge.Selling.SellingItem;
import com.quzzar.ge.Selling.SellingUtil;

public class CancellationFee {
	
	public static int getCancelFee(SellingItem sale) {
		
		if (sale.getStage().equals(SellStage.PROCESSING)) {
			
			return (int) (SellingUtil.getTotalGems(sale)*0.12);
			
		} else if (sale.getStage().equals(SellStage.FAILED)) {
			
			return (int) (SellingUtil.getTotalGems(sale)*0.10);
			
		} else {
			
			return 0;
			
		}
		
	}
	
	public static boolean performCancelFee(SellingItem sale, Player p) {
		
		int playerBalance = API.getGemBalance(p);
		int feeCost = getCancelFee(sale);
		
		if(playerBalance>=feeCost) {
			
			API.takeGems(p, feeCost);
			
			return true;
			
		} else {
			
			p.closeInventory();
			
			int need = (feeCost-playerBalance);
			
			if(need!=1) {
				Utility.tellSender(p, ChatColor.YELLOW+"You don't have enough Gems to pay the cancellation fee! (Need "
						+(feeCost-playerBalance)+ " more Gems in your inventory)");
			} else {
				Utility.tellSender(p, ChatColor.YELLOW+"You don't have enough Gems to pay the cancellation fee! (Need "
						+(feeCost-playerBalance)+ " more Gem in your inventory)");
			}
			
			return false;
			
		}
		
	}
	
	
}

package com.quzzar.ge;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import com.quzzar.ge.Selling.SellStage;
import com.quzzar.ge.Selling.SellingItem;
import com.quzzar.ge.Selling.SellingUtil;

public class TradeWindows {
	
	private static String invTitle = ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Grand Exchange:"+ChatColor.RESET;
	
	private static ArrayList<Inventory> tradeInvs = new ArrayList<Inventory>();
	
	
	public static ArrayList<Inventory> getTradeInvs(){
		return tradeInvs;
	}
	
	public static void addToTradeInvs(Inventory inv){
		getTradeInvs().add(inv);
	}
	
	
	public static Inventory createCorrectTradeLayout(SellingItem sale, Inventory prevInv) {
		
		if(sale.getStage().equals(SellStage.PROCESSING)) {
			return processingTrade(sale, prevInv);
	    } else if(sale.getStage().equals(SellStage.ACCEPTED)) {
	    	return acceptedTrade(sale, prevInv);
	    } else if(sale.getStage().equals(SellStage.FAILED)) {
	    	return failedTrade(sale, prevInv);
	    } else {
	    	return GrandExchangeMenu.getMenu();
	    }
		
	}
	
	public static String emptyTradeInvName = invTitle+ChatColor.BLUE+" Sell Item";
	public static String proccesingTradeInvName = invTitle+ChatColor.GOLD+" Pending";
	public static String acceptedTradeInvName = invTitle+ChatColor.GREEN+" Sold";
	public static String failedTradeInvName = invTitle+ChatColor.RED+" Cancelled";
	
	
	public static Inventory emptyTrade() {
		
		Inventory inv = Bukkit.createInventory(null, 36, emptyTradeInvName);
		
		Utility.fillMenu(inv);
		
		inv.setItem(3, ItemsCreator.Trade_Border());
		inv.setItem(4, ItemsCreator.Trade_Border());
		inv.setItem(5, ItemsCreator.Trade_Border());
		
		inv.setItem(7, ItemsCreator.createIncreaseGemsBlocksItem());
		inv.setItem(8, ItemsCreator.createIncreaseGemsItem());
		
		inv.setItem(11, ItemsCreator.createConfirmItem());
		inv.setItem(10, ItemsCreator.createCancelItemNoFee());
		inv.setItem(12, ItemsCreator.Trade_Border());
		inv.getItem(13).setAmount(0);                      // Tracked by Slot
		inv.setItem(14, ItemsCreator.Trade_Border());
		
		inv.setItem(16, ItemsCreator.Menu_Filler());  // Tracked by Slot
		inv.setItem(17, ItemsCreator.createGemSellPrice(1));  // Tracked by Slot
		
		inv.setItem(21, ItemsCreator.Trade_Border());
		inv.setItem(22, ItemsCreator.Trade_Border());
		inv.setItem(23, ItemsCreator.Trade_Border());
		
		inv.setItem(25, ItemsCreator.createDecreaseGemsBlocksItem());
		inv.setItem(26, ItemsCreator.createDecreaseGemsItem());
		
		inv.setItem(27, ItemsCreator.Generic_Footer());
		inv.setItem(28, ItemsCreator.Generic_Footer());
		inv.setItem(29, ItemsCreator.Generic_Footer());
		inv.setItem(30, ItemsCreator.Generic_Footer());
		inv.setItem(31, ItemsCreator.Generic_Footer());
		inv.setItem(32, ItemsCreator.Generic_Footer());
		inv.setItem(33, ItemsCreator.Generic_Footer());
		inv.setItem(34, ItemsCreator.Generic_Footer());
		inv.setItem(35, ItemsCreator.Generic_Footer());
		
		
		tradeInvs.add(inv);
		
		return inv;
		
	}
	
	
	public static Inventory processingTrade(SellingItem sale, Inventory prevInv) {
		
		Inventory inv = Bukkit.createInventory(null, 36, proccesingTradeInvName);
		
		inv.setItem(0, ItemsCreator.Back_Button(prevInv));
		
		inv.setItem(3, ItemsCreator.Trade_Border());
		inv.setItem(4, ItemsCreator.Trade_Border());
		inv.setItem(5, ItemsCreator.Trade_Border());
		
		inv.setItem(8, ItemsCreator.Menu_Filler_ID(sale)); // Tracked by Slot
		
		inv.setItem(10, ItemsCreator.createCancelItem(sale));
		inv.setItem(12, ItemsCreator.Trade_Border());
		
		inv.setItem(13, sale.getSellingItem());   // Tracked by Slot
		
		inv.setItem(14, ItemsCreator.Trade_Border());
		
		inv.setItem(16, ItemsCreator.createBlockSellPrice(sale.getPriceBlock()));  // Tracked by Slot
		inv.setItem(17, ItemsCreator.createGemSellPrice(sale.getPriceSingle()));  // Tracked by Slot
		
		inv.setItem(21, ItemsCreator.Trade_Border());
		inv.setItem(22, ItemsCreator.Trade_Border());
		inv.setItem(23, ItemsCreator.Trade_Border());
		
		inv.setItem(27, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(28, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(29, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(30, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(31, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(32, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(33, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(34, ItemsCreator.Processing_Trade_Footer());
		inv.setItem(35, ItemsCreator.Processing_Trade_Footer());
		
		Utility.fillMenu(inv);
		
		tradeInvs.add(inv);
		
		return inv;
		
	}
	
	
	public static Inventory acceptedTrade(SellingItem sale, Inventory prevInv) {
		
		Inventory inv = Bukkit.createInventory(null, 36, acceptedTradeInvName);
		
		inv.setItem(0, ItemsCreator.Back_Button(prevInv));
		
		inv.setItem(3, ItemsCreator.Trade_Border());
		inv.setItem(4, ItemsCreator.Trade_Border());
		inv.setItem(5, ItemsCreator.Trade_Border());
		
		inv.setItem(8, ItemsCreator.Menu_Filler_ID(sale)); // Tracked by Slot
		
		inv.setItem(10, ItemsCreator.createConfirmItem());
		
		inv.setItem(12, ItemsCreator.Trade_Border());
		
		inv.setItem(13, SellingUtil.makeGemPackage(sale));
		
		inv.setItem(14, ItemsCreator.Trade_Border());
		
		inv.setItem(16, ItemsCreator.createConfirmItem());
		
		inv.setItem(21, ItemsCreator.Trade_Border());
		inv.setItem(22, ItemsCreator.Trade_Border());
		inv.setItem(23, ItemsCreator.Trade_Border());
		
		inv.setItem(27, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(28, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(29, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(30, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(31, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(32, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(33, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(34, ItemsCreator.Confirmed_Trade_Footer());
		inv.setItem(35, ItemsCreator.Confirmed_Trade_Footer());
		
		Utility.fillMenu(inv);
		
		tradeInvs.add(inv);
		
		return inv;
		
	}
	
	public static Inventory failedTrade(SellingItem sale, Inventory prevInv) {
		
		Inventory inv = Bukkit.createInventory(null, 36, failedTradeInvName);
		
		inv.setItem(0, ItemsCreator.Back_Button(prevInv));
		
		inv.setItem(3, ItemsCreator.Trade_Border());
		inv.setItem(4, ItemsCreator.Trade_Border());
		inv.setItem(5, ItemsCreator.Trade_Border());
		
		inv.setItem(8, ItemsCreator.Menu_Filler_ID(sale)); // Tracked by Slot
		
		inv.setItem(10, ItemsCreator.createCancelItem(sale));
		
		inv.setItem(12, ItemsCreator.Trade_Border());
		
		inv.setItem(13, sale.getSellingItem());
		
		inv.setItem(14, ItemsCreator.Trade_Border());
		
		inv.setItem(16, ItemsCreator.createCancelItem(sale));
		
		inv.setItem(21, ItemsCreator.Trade_Border());
		inv.setItem(22, ItemsCreator.Trade_Border());
		inv.setItem(23, ItemsCreator.Trade_Border());
		
		inv.setItem(27, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(28, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(29, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(30, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(31, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(32, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(33, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(34, ItemsCreator.Failed_Trade_Footer());
		inv.setItem(35, ItemsCreator.Failed_Trade_Footer());
		
		Utility.fillMenu(inv);
		
		tradeInvs.add(inv);
		
		return inv;
		
	}
	
	
	
}

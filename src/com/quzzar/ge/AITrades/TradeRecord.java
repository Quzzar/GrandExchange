package com.quzzar.ge.AITrades;

import org.bukkit.inventory.ItemStack;

public class TradeRecord {
	
	
	private ItemStack tradedItem;
	private int costSingle;
	private int costBlock;

	public TradeRecord(ItemStack tradedItem, int costSingle, int costBlock) {
		
		this.tradedItem = tradedItem;
		this.costSingle = costSingle;
		this.costBlock = costBlock;
		
	}
	
	
	public ItemStack getTradedItem() {
		return tradedItem;
	}

	public int getCostSingle() {
		return costSingle;
	}


	public int getCostBlock() {
		return costBlock;
	}
	
}

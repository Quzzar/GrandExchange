package com.quzzar.ge.Selling;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class SellingItem {
	
	private SellStage stage;
	private OfflinePlayer p;
	private ItemStack item;
	
	private int priceSingle;
	private int priceBlock;
	
	private int time;
	
	private UUID id;
	
	private boolean unknownTrade = false;
	
	
	public SellingItem(ItemStack item, int priceSingle, int priceBlock) {
		
		this.stage = SellStage.PROCESSING;
		
		this.item = item.clone();
		
		this.priceSingle = priceSingle;
		this.priceBlock = priceBlock;
		
		this.time = 0;
		
		this.id = UUID.randomUUID();
		
		unknownTrade = true;
		
	}
	
	public SellingItem(OfflinePlayer p, ItemStack item, int priceSingle, int priceBlock) {
		
		this.stage = SellStage.PROCESSING;
		this.p = p;
		this.item = item.clone();
		
		this.priceSingle = priceSingle;
		this.priceBlock = priceBlock;
		
		this.time = 0;
		
		this.id = UUID.randomUUID();
		
		if(p==null) {
			unknownTrade = true;
		}
		
	}
	
	public SellingItem(SellStage stage, OfflinePlayer p, ItemStack item, int priceSingle, int priceBlock) {
		
		this.stage = stage;
		this.p = p;
		this.item = item.clone();
		
		this.priceSingle = priceSingle;
		this.priceBlock = priceBlock;
		
		this.id = UUID.randomUUID();
		
		if(p==null) {
			unknownTrade = true;
		}
		
	}
	
	public void setStage(SellStage stage) {
		this.stage = stage;
	}
	
	public SellStage getStage() {
		return stage;
	}
	
	public OfflinePlayer getSeller() {
		return p;
	}
	
	public ItemStack getSellingItem() {
		return item;
	}
	
	public int getPriceSingle() {
		return priceSingle;
	}
	
	public int getPriceBlock() {
		return priceBlock;
	}
	
	public UUID getUUID() {
		return id;
	}
	
	
	public void setSellingTime(int time) {
		this.time = time;
	}
	
	public int getSellingTime() {
		return time;
	}
	
	public boolean isUnknownTrade() {
		return unknownTrade;
	}
	
}

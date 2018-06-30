package com.quzzar.ge.AITrades;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

import com.quzzar.ge.ConfigManager;
import com.quzzar.ge.Selling.SellManager;
import com.quzzar.ge.Selling.SellingItem;
import com.quzzar.ge.Selling.dataProcessing.SellingDataProcessing;

public class AITradesManager {
	
	
	private static ArrayList<TradeRecord> tradeRecords = new ArrayList<TradeRecord>();
	
	public static void loadTradeRecords() {
		
		String records = "TradeRecords";
		
		if(ConfigManager.getInstance().getTradeRecords().get(records)!=null) {
			for(String recordID : ConfigManager.getInstance().getTradeRecords().getConfigurationSection(records).getKeys(false)) {
				
				ItemStack item = ConfigManager.getInstance().getTradeRecords().getItemStack(records+"."+recordID+".Item");
				int costSingle = ConfigManager.getInstance().getTradeRecords().getInt(records+"."+recordID+".CostSingle");
				int costBlock = ConfigManager.getInstance().getTradeRecords().getInt(records+"."+recordID+".CostBlock");
				
				getTradeRecords().add(new TradeRecord(item, costSingle, costBlock));
			}
		}
	}
	
	public static void saveTradeRecords() {
		
		String records = "TradeRecords";
		
		ConfigManager.getInstance().getTradeRecords().set(records, null);
		
		ConfigManager.getInstance().saveTradeRecords();
		
		
		for(TradeRecord record : getTradeRecords()) {
			
			String recordID = UUID.randomUUID().toString();
			
			ConfigManager.getInstance().getTradeRecords().set(records+"."+recordID+".Item", record.getTradedItem());
			ConfigManager.getInstance().getTradeRecords().set(records+"."+recordID+".CostSingle", record.getCostSingle());
			ConfigManager.getInstance().getTradeRecords().set(records+"."+recordID+".CostBlock", record.getCostBlock());
			
		}
		
		ConfigManager.getInstance().saveTradeRecords();
	}
	
	public static ArrayList<TradeRecord> getTradeRecords() {
		return tradeRecords;
	}
	
	
	public static void addToTradeRecords(SellingItem sale) {
		if(!isItemRecorded(sale.getSellingItem()) && !sale.getSellingItem().hasItemMeta()) {
			
			Random rand = new Random();
			
			if(rand.nextInt(15)!=1) {
				getTradeRecords().add(new TradeRecord(sale.getSellingItem(), sale.getPriceSingle(), sale.getPriceBlock()));
			}
			
		}
	}
	
	public static void removeFromTradeRecords(TradeRecord trade) {
		getTradeRecords().remove(trade);
	}
	
	public static void updateInTradeRecords(TradeRecord trade, SellingItem sale) {
		for(TradeRecord tradeRecord : getTradeRecords()) {
			if(tradeRecord.equals(trade)) {
				removeFromTradeRecords(tradeRecord);
				addToTradeRecords(sale);
				return;
			}
		}
	}
	
	
	public static void createAITrade() {
		
		if(getTradeRecords().size()>0) {
			
			TradeRecord trade = getTradeRecords().get(new Random().nextInt(getTradeRecords().size()));
			
			if(!isSellingAITrade(trade)) {
				
				SellingItem sale = new SellingItem(trade.getTradedItem(),
						(int) Math.ceil(1.2*((0.003*trade.getTradedItem().getAmount())+1)*trade.getCostSingle()), 
						(int) Math.ceil(1.1*trade.getCostBlock()));
				
				SellManager.itemsSelling.add(sale);
				
				SellingDataProcessing.encrypt();
				
				updateInTradeRecords(trade,sale);
				
			}
			
		}
		
	}
	
	public static boolean isItemRecorded(ItemStack item) {
		for(TradeRecord record : getTradeRecords()) {
			if(record.getTradedItem().isSimilar(item)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSellingAITrade(TradeRecord trade) {
		for(SellingItem sale : SellManager.itemsSelling) {
			if(sale.isUnknownTrade() && sale.getSellingItem().equals(trade.getTradedItem())) {
				return true;
			}
		}
		return false;
	}
	
}

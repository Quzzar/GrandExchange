package com.quzzar.ge;

import org.bukkit.plugin.java.JavaPlugin;

import com.quzzar.ge.AITrades.AITradesManager;
import com.quzzar.ge.Selling.dataProcessing.SellingDataProcessing;


public class Main extends JavaPlugin{
	
	public static Main instance;
	
	@Override
	public void onEnable(){
		
		instance = this;
		
		ConfigManager.getInstance().setup(instance);
		
		GrandExchangeMenu.initialize();
		
		SellingDataProcessing.decrypt();
		
		getServer().getPluginManager().registerEvents(new MainListener(), instance);
		
		instance.getCommand("ge").setExecutor(new CommandGE());
		
		instance.getCommand("grandexchange").setExecutor(new CommandGE());
		
		
		MainListener.runTask();
		
		AITradesManager.loadTradeRecords();
		
		Utility.tellConsole("Loaded and Ready!");
		
	}
	
	@Override
	public void onDisable(){
		try {
			SellingDataProcessing.encrypt();
			AITradesManager.saveTradeRecords();
		} catch (Exception e) {
			
		}
	}
	
	

}

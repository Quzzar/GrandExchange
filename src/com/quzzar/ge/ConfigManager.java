package com.quzzar.ge;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class ConfigManager {
 
        private static ConfigManager instance = new ConfigManager();
       
        public static ConfigManager getInstance() {
                return instance;
        }
        
        private Plugin p;
       
        private FileConfiguration config;
        private File cfile;
       
        private FileConfiguration data;
        private File bfile;
        
        private FileConfiguration tRec;
        private File tfile;
        
        
        public PluginDescriptionFile getDesc() {
            return p.getDescription();
        }
       
        public void setup(Plugin p) {
        	
        	if (!p.getDataFolder().exists()) {
                p.getDataFolder().mkdir();
        	}
        	
        	 cfile = new File(p.getDataFolder(), "config.yml");
             if (!cfile.exists()) {
                 try {
                         cfile.createNewFile();
                 }
                 catch (IOException e) {
                         Bukkit.getServer().getLogger().severe("Could not create config.yml!");
                 }
             }
             config = YamlConfiguration.loadConfiguration(cfile);
             
             
            
             bfile = new File(p.getDataFolder() + File.separator + "Data", "data.yml");
             if (!bfile.exists()) {
                     try {
                             bfile.createNewFile();
                     }
                     catch (IOException e) {
                             Bukkit.getServer().getLogger().severe("Could not create Data/data.yml!");
                     }
             }
             data = YamlConfiguration.loadConfiguration(bfile);
             
             
             
             tfile = new File(p.getDataFolder() + File.separator + "Data", "tradeRecords.yml");
             if (!bfile.exists()) {
                     try {
                             tfile.createNewFile();
                     }
                     catch (IOException e) {
                             Bukkit.getServer().getLogger().severe("Could not create Data/tradeRecords.yml!");
                     }
             }
             tRec = YamlConfiguration.loadConfiguration(tfile);
             
             
             
        	
        }
        /////////////////////////////////////////////////////
        
        public FileConfiguration getConfig() {
                return config;
        }
       
        public void saveConfig() {
                try {
                        config.save(cfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe("Could not save config.yml!");
                }
        }
       
        public void reloadConfig() {
        	config = YamlConfiguration.loadConfiguration(cfile);
        }
        
        ///
       
        public FileConfiguration getData() {
                return data;
        }
       
        public void saveData() {
                try {
                	data.save(bfile);
                }
                catch (IOException e) {
                        Bukkit.getServer().getLogger().severe("Could not save Data/data.yml!");
                }
        }
       
        public void reloadData() {
        	data = YamlConfiguration.loadConfiguration(bfile);
        }
        
        ///
        
	    public FileConfiguration getTradeRecords() {
	            return tRec;
	    }
	   
	    public void saveTradeRecords() {
	            try {
	            	tRec.save(tfile);
	            }
	            catch (IOException e) {
	                    Bukkit.getServer().getLogger().severe("Could not save Data/tradeRecords.yml!\"");
	            }
	    }
	   
	    public void reloadTradeRecords() {
	    	tRec = YamlConfiguration.loadConfiguration(tfile);
	    }
        
        
        
}
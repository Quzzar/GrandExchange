package com.quzzar.ge;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.atlasmc.gems.API;
import com.quzzar.ge.CustomHeads.HeadUtil;
import com.quzzar.ge.CustomHeads.TextureDatabase;
import com.quzzar.ge.Selling.SellingItem;

public class ItemsCreator {
	
	
	public static ItemStack Buy_Button(){
		
		ItemStack i = new ItemStack(Material.NETHER_STAR);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BOLD+""+ChatColor.BLUE+"Buy");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Sell_Button(){
		
		ItemStack i = new ItemStack(Material.NETHER_STAR);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BOLD+""+ChatColor.AQUA+"Sell");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	
	public static ItemStack Add_New_Sale_Button(){
		
		ItemStack i = new ItemStack(Material.HOPPER);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Add Item");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	
	public static ItemStack Back_Button(Inventory previous){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.LEFT_ARROW,1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BOLD+"<- Back");
	    
	    ArrayList<String> lore = new ArrayList<String>();
	    lore.add(ChatColor.GRAY+"<"+ChatColor.stripColor(previous.getTitle())+">");
	    im.setLore(lore);
	    i.setItemMeta(im);
	    
	    return i;
	}
	
	public static ItemStack Next_Button(Inventory next){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.RIGHT_ARROW,1);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BOLD+"Next ->");
	    
	    ArrayList<String> lore = new ArrayList<String>();
	    lore.add(ChatColor.GRAY+"<"+ChatColor.stripColor(next.getTitle())+">");
	    im.setLore(lore);
	    i.setItemMeta(im);
	    
	    return i;
	}
	
	
	//////
	
	public static ItemStack Menu_Filler(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Menu_Filler_ID(SellingItem sale){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+""+sale.getUUID());
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Trade_Border(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Generic_Footer(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Processing_Trade_Footer(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Confirmed_Trade_Footer(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack Failed_Trade_Footer(){
		
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.WHITE+"");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createConfirmItem(){
		
		ItemStack i = new ItemStack(Material.INK_SACK, 1, (short) 10);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.GREEN+"Confirm");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createCancelItem(SellingItem sale){
		
		int fee = CancellationFee.getCancelFee(sale);
		
		ItemStack i = new ItemStack(Material.BARRIER);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.RED+"Cancel");
	    im.setLore(Arrays.asList(ChatColor.RED+""+ChatColor.ITALIC+"Costs "+fee+" Gem(s)"));
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createCancelItemNoFee(){
		
		ItemStack i = new ItemStack(Material.BARRIER);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.RED+"Cancel");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createIncreaseGemsItem(){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.UP_ARROW,1);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.DARK_GREEN+"Increase");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createDecreaseGemsItem(){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.DOWN_ARROW,1);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.DARK_RED+"Decrease");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	
	public static ItemStack createIncreaseGemsBlocksItem(){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.UP_ARROW,1);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.DARK_GREEN+"Increase ");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createDecreaseGemsBlocksItem(){
		
		ItemStack i = HeadUtil.makeRawItemStack(TextureDatabase.DOWN_ARROW,1);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.DARK_RED+"Decrease ");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createGemSellPrice(int amt){
		
		ItemStack i = new ItemStack(Material.EMERALD, amt);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BLUE+"Selling Price");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createBlockSellPrice(int amt){
		
		ItemStack i = new ItemStack(Material.EMERALD_BLOCK, amt);
		
		ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.BLUE+"Selling Price");
	    i.setItemMeta(im);
		
	    return i;
	}
	
	public static ItemStack createMoney(int amt){
	    return API.makeGem(amt);
	}
	
}

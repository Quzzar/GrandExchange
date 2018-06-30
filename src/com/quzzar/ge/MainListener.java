package com.quzzar.ge;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.atlasmc.gems.API;
import com.quzzar.ge.AITrades.AITradesManager;
import com.quzzar.ge.Buying.BuyingManager;
import com.quzzar.ge.Buying.BuyingUtil;
import com.quzzar.ge.Selling.PlayerSellSizeHandler;
import com.quzzar.ge.Selling.PlayerSellings;
import com.quzzar.ge.Selling.SellManager;
import com.quzzar.ge.Selling.SellStage;
import com.quzzar.ge.Selling.SellingItem;
import com.quzzar.ge.Selling.SellingUtil;
import com.quzzar.ge.Selling.dataProcessing.SellingDataProcessing;

public class MainListener implements Listener {
	
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e){
		
		Inventory inv = e.getClickedInventory();
		ItemStack item = e.getCurrentItem();
		
		if(inv!=null) {
			
			
			/// Handle Back/Next Buttons
			
			if(item!=null&&item.getType().equals(Material.SKULL_ITEM)&&item.hasItemMeta()
					&&item.getItemMeta().hasLore()&&e.getWhoClicked() instanceof Player){
				
				ItemStack next = ItemsCreator.Next_Button(GrandExchangeMenu.getMenu());
				ItemStack back = ItemsCreator.Back_Button(GrandExchangeMenu.getMenu());
				
				ItemMeta imNext = next.getItemMeta();
				imNext.setLore(null);
				next.setItemMeta(imNext);
				
				ItemMeta imBack = back.getItemMeta();
				imBack.setLore(null);
				back.setItemMeta(imBack);
				
				
				ItemStack clickedItem = item.clone();
				
				ItemMeta imClickItem = clickedItem.getItemMeta();
				imClickItem.setLore(null);
				clickedItem.setItemMeta(imClickItem);
				
				if(clickedItem.equals(next) || clickedItem.equals(back)) {
					
					Player p = (Player) e.getWhoClicked();
					
					String loreStr = item.getItemMeta().getLore().get(0);
					loreStr = loreStr.replace("<", "");
					loreStr = loreStr.replace(">", "");
					
					Inventory gotoInv = findInventory(loreStr);
					
					if(gotoInv!=null) {
						
						p.closeInventory();
						p.openInventory(gotoInv);
						
						return;
						
					}
					
				}
			}
			
			///
			
			
			for(Inventory invTrade : TradeWindows.getTradeInvs()) {
				
				if(inv.equals(invTrade)) {
					
					if(inv.getTitle().equals(TradeWindows.emptyTradeInvName) && e.getSlot()==13) {
						
						// pass
						
					} else {
						
						e.setCancelled(true);
						e.setResult(Result.DENY);
						
					}
					
				}
			}
			
			for(Inventory invTrade : BuyingManager.getBuyPages()) {
				
				if(inv.equals(invTrade)) {
					
					e.setCancelled(true);
					e.setResult(Result.DENY);
					
				}
			}
			
			if(e.getWhoClicked()!=null && e.getWhoClicked() instanceof Player && item!=null) {
				Player p = (Player) e.getWhoClicked();
				
				PlayerSellings pSell = SellManager.getPlayerSellings(p);
				
				if(inv.equals(GrandExchangeMenu.getMenu())) {
					
					if(item.isSimilar(ItemsCreator.Sell_Button())) {
						
						p.closeInventory();
						p.openInventory(SellManager.getPlayerSellings(p).getSellInv());
						return;
						
					}
					
					if(item.isSimilar(ItemsCreator.Buy_Button())) {
						
						p.closeInventory();
						
						BuyingManager.updateBuyMenu();
						
						p.openInventory(BuyingManager.getBuyPages().get(0));
						return;
						
					}
					
				}
				
				
				if(isBuyInv(inv)) {
					
					for(SellingItem sale : SellManager.itemsSelling) {
						
						String sellerName = "Unknown";
						
						if(sale.getSeller()!=null) {
							sellerName = sale.getSeller().getName();
						}
						
						if(item.equals(BuyingUtil.makeBuyItem(sale.getSellingItem(), SellingUtil.getTotalGems(sale), sellerName))
								&& sale.getStage().equals(SellStage.PROCESSING)) {
							
							// Can cancel items if they have permission
							if(e.getClick().equals(ClickType.MIDDLE) && p.hasPermission("grandexchange.cancelsale")) {
								
								sale.setStage(SellStage.FAILED);
								
								BuyingManager.updateBuyMenu();
								
								return;
							}
							
							// Make sure its not yours
							if(sale.getSeller()!=null && sale.getSeller().getUniqueId().equals(p.getUniqueId())) {
								
								p.closeInventory();
								Utility.tellSender(p, ChatColor.YELLOW+"You can't buy your own items!");
								
								return;
								
							} else {
								
								/// Found buy, see if player has money
								
								
								int playerBalance = API.getGemBalance(p);
								int itemCost = SellingUtil.getTotalGems(sale);
								
								if(playerBalance>=itemCost) {
									
									AITradesManager.addToTradeRecords(sale);
									
									API.takeGems(p, itemCost);
									
									sale.setStage(SellStage.ACCEPTED);
									
									BuyingManager.updateBuyMenu();
									
									Utility.addItemToInventory(p.getInventory(), sale.getSellingItem(), p.getLocation());
									
									if(sale.isUnknownTrade()) {
										SellManager.itemsSelling.remove(sale);
									}
									
									SellingDataProcessing.encrypt();
									
								} else {
									
									p.closeInventory();
									
									int need = (itemCost-playerBalance);
									
									if(need!=1) {
										Utility.tellSender(p, ChatColor.YELLOW+"You don't have enough Gems to buy this! (Need "
												+(itemCost-playerBalance)+ " more Gems in your inventory)");
									} else {
										Utility.tellSender(p, ChatColor.YELLOW+"You don't have enough Gems to buy this! (Need "
												+(itemCost-playerBalance)+ " more Gem in your inventory)");
									}
									
								}
								
								return;
							
							
							}
						
						}
					}
				}
				
				
				if(inv.getTitle().equals(TradeWindows.emptyTradeInvName)) {
					
					if(item.isSimilar(ItemsCreator.createIncreaseGemsBlocksItem())) {
						
						
						if(e.getClick().equals(ClickType.RIGHT)) {
							
							if(inv.getItem(16).getType().equals(Material.EMERALD_BLOCK)) {
								
								if(inv.getItem(16).getAmount()+8<64) {
									inv.getItem(16).setAmount(inv.getItem(16).getAmount()+8);
								}else {
									inv.getItem(16).setAmount(64);
								}
								
							}else {
								
								inv.setItem(16, ItemsCreator.createBlockSellPrice(8));
								
							}
							
						} else {
							
							if(inv.getItem(16).getType().equals(Material.EMERALD_BLOCK)) {
								
								if(inv.getItem(16).getAmount()+1<64) {
									inv.getItem(16).setAmount(inv.getItem(16).getAmount()+1);
								}else {
									inv.getItem(16).setAmount(64);
								}
								
							}else {
								
								inv.setItem(16, ItemsCreator.createBlockSellPrice(1));
								
							}
							
						}
						
						return;
						
					}
					
					if(item.isSimilar(ItemsCreator.createDecreaseGemsBlocksItem())) {
						
						if(e.getClick().equals(ClickType.RIGHT)) {
							
							if(inv.getItem(16).getAmount()-8>1) {
								inv.getItem(16).setAmount(inv.getItem(16).getAmount()-8);
							}else {
								inv.setItem(16, ItemsCreator.Menu_Filler());
							}
							
						} else {
							
							if(inv.getItem(16).getAmount()>1) {
								inv.getItem(16).setAmount(inv.getItem(16).getAmount()-1);
							}else {
								inv.setItem(16, ItemsCreator.Menu_Filler());
							}
							
						}
						
						return;
						
					}
					
					///////
					
					if(item.isSimilar(ItemsCreator.createIncreaseGemsItem())) {
						
						
						if(e.getClick().equals(ClickType.RIGHT)) {
							
							if(inv.getItem(17).getType().equals(Material.EMERALD)) {
								
								if(inv.getItem(17).getAmount()+8<64) {
									inv.getItem(17).setAmount(inv.getItem(17).getAmount()+8);
								}else {
									inv.getItem(17).setAmount(64);
								}
								
							}else {
								
								inv.setItem(17, ItemsCreator.createGemSellPrice(8));
								
							}
							
						} else {
							
							if(inv.getItem(17).getType().equals(Material.EMERALD)) {
								
								if(inv.getItem(17).getAmount()+1<64) {
									inv.getItem(17).setAmount(inv.getItem(17).getAmount()+1);
								}else {
									inv.getItem(17).setAmount(64);
								}
								
							}else {
								
								inv.setItem(17, ItemsCreator.createGemSellPrice(1));
								
							}
							
						}
						
						return;
						
					}
					
					if(item.isSimilar(ItemsCreator.createDecreaseGemsItem())) {
						
						if(e.getClick().equals(ClickType.RIGHT)) {
							
							if(inv.getItem(17).getAmount()-8>1) {
								inv.getItem(17).setAmount(inv.getItem(17).getAmount()-8);
							}else {
								inv.setItem(17, ItemsCreator.Menu_Filler());
							}
							
						} else {
							
							if(inv.getItem(17).getAmount()>1) {
								inv.getItem(17).setAmount(inv.getItem(17).getAmount()-1);
							} else {
								inv.setItem(17, ItemsCreator.Menu_Filler());
							}
							
						}
						
						return;
						
					}
					
					////////
					
					
					if(item.isSimilar(ItemsCreator.createConfirmItem())) {
						
						if(inv.getItem(13)!=null && !API.isCurrency(inv.getItem(13))) {
							
							int singleVal = 0;
							int blockVal = 0;
							
							if(inv.getItem(16).getType().equals(Material.EMERALD_BLOCK)) {
								
								blockVal = inv.getItem(16).getAmount();
								
							}
							
							if(inv.getItem(17).getType().equals(Material.EMERALD)) {
								
								singleVal = inv.getItem(17).getAmount();
								
							}
							
							if(blockVal!=0 || singleVal!=0) {
								
								SellManager.itemsSelling.add(new SellingItem(p, inv.getItem(13), singleVal, blockVal));
								
								inv.getItem(13).setAmount(0);
								
								SellingDataProcessing.encrypt();
								
								p.closeInventory();
								
								pSell.update();
								
								p.openInventory(pSell.getSellInv());
								
							}
							
						}
						
						return;
					}
					
					if(item.isSimilar(ItemsCreator.createCancelItemNoFee())) {
						
						if(inv.getItem(13)!=null) {
							
							Utility.addItemToInventory(p.getInventory(), inv.getItem(13), p.getLocation());
							
							inv.getItem(13).setAmount(0);
							
						}
						
						p.closeInventory();
						p.openInventory(pSell.getSellInv());
						
						return;
					}
					
				}
				
				if(inv.getTitle().equals(TradeWindows.proccesingTradeInvName)) {
					
					SellingItem sale = SellingUtil.getSale(inv, p, SellStage.PROCESSING);
					
					if(item.isSimilar(ItemsCreator.createCancelItem(sale))) {
						
						boolean feePaid = CancellationFee.performCancelFee(sale, p);
						
						if(inv.getItem(13)!=null && feePaid) {
							
							Utility.addItemToInventory(p.getInventory(), inv.getItem(13), p.getLocation());
							
							SellManager.itemsSelling.remove(sale);
							
							inv.getItem(13).setAmount(0);
							
						}
						
						p.closeInventory();
						
						pSell.update();
						
						if(feePaid) {
							p.openInventory(pSell.getSellInv());
						}
						
						return;
						
					}
					
				}
				
				
				if(inv.getTitle().equals(TradeWindows.acceptedTradeInvName)) {
					
					
					if(item.isSimilar(ItemsCreator.createConfirmItem())) {
						
						if(inv.getItem(13)!=null) {
							
							SellingItem sale = SellingUtil.getSale(inv, p, SellStage.ACCEPTED);
							
							API.giveGems(p, SellingUtil.getTotalGems(sale));
							
							SellManager.itemsSelling.remove(sale);
							
							inv.getItem(13).setAmount(0);
							
						}
						
						
						p.closeInventory();
						
						pSell.update();
						
						p.openInventory(pSell.getSellInv());
						
						return;
						
					}
					
				}
				
				
				if(inv.getTitle().equals(TradeWindows.failedTradeInvName)) {
					
					SellingItem sale = SellingUtil.getSale(inv, p, SellStage.FAILED);
					
					if(item.isSimilar(ItemsCreator.createCancelItem(sale))) {
						
						boolean feePaid = CancellationFee.performCancelFee(sale, p);
						
						if(inv.getItem(13)!=null && feePaid) {
							
							Utility.addItemToInventory(p.getInventory(), inv.getItem(13), p.getLocation());
							
							SellManager.itemsSelling.remove(sale);
							
							inv.getItem(13).setAmount(0);
							
						}
						
						
						p.closeInventory();
						
						pSell.update();
						
						if(feePaid) {
							p.openInventory(pSell.getSellInv());
						}
						
						return;
						
					}
					
				}
				
				
				if(inv.equals(pSell.getSellInv())) {
					
					if(item.isSimilar(ItemsCreator.Add_New_Sale_Button())) {
						
						if (pSell.getSellingItems().size()<(PlayerSellSizeHandler.getSellSize(p)-18)) {
							
							p.closeInventory();
							p.openInventory(TradeWindows.emptyTrade());
							
						}
						return;
						
					}
					
					for(SellingItem sale : pSell.getSellingItems()) {
						
						if(SellingUtil.makeSellItem(sale.getSellingItem(), sale.getStage()).isSimilar(item)) {
							
							p.closeInventory();
							p.openInventory(TradeWindows.createCorrectTradeLayout(sale, inv));
							return;
							
						}
						
					}
					
				}
				
				
				
				
			}
			
			
		}
	}
	
	@EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
		
		Inventory inv = e.getInventory();
		
		if(inv!=null) {
			
			if(inv.getTitle().equals(TradeWindows.emptyTradeInvName) && inv.getItem(13) != null) {
				
				Utility.addItemToInventory(e.getPlayer().getInventory(), inv.getItem(13), e.getPlayer().getLocation());
				
				inv.getItem(13).setAmount(0);
				
			}
		}
		
	}
	
	
	private static boolean isBuyInv(Inventory inv) {
		
		for(Inventory invCheck : BuyingManager.getBuyPages()) {
			if(inv.equals(invCheck)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static Inventory findInventory(String invName) {
		
		for(Inventory invTrade : TradeWindows.getTradeInvs()) {
			if(ChatColor.stripColor(invTrade.getTitle()).equals(ChatColor.stripColor(invName))) {
				return invTrade;
			}
		}
		
		for(Inventory invTrade : BuyingManager.getBuyPages()) {
			if(ChatColor.stripColor(invTrade.getTitle()).equals(ChatColor.stripColor(invName))) {
				return invTrade;
			}
		}
		
		return null;
		
	}
	
	
	public static void runTask() {
		
		final int expireTime = 2880; // Two days
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			
			public void run() {
				
				ArrayList<SellingItem> removeSales = new ArrayList<SellingItem>();
				
				for(SellingItem sale : SellManager.itemsSelling) {
					if(sale.getStage().equals(SellStage.PROCESSING)) {
						if(sale.getSellingTime()<expireTime) {
							
							sale.setSellingTime(sale.getSellingTime()+1);
							
						} else {
							
							sale.setStage(SellStage.FAILED);
							
							if(sale.isUnknownTrade()) {
								SellingItem newSale = new SellingItem(sale.getSellingItem(), 
										(int) Math.ceil(sale.getPriceSingle()-0.4*sale.getPriceSingle()), 
										(int) Math.ceil(sale.getPriceBlock()-0.2*sale.getPriceBlock()));
								
								AITradesManager.addToTradeRecords(newSale);
								removeSales.add(sale);
							}
							
						}
					} else if(sale.getStage().equals(SellStage.FAILED)) {
						
						if(sale.isUnknownTrade()) {
							removeSales.add(sale);
						}
						
					}
					
				}
				
				SellManager.itemsSelling.removeAll(removeSales);
				
				BuyingManager.updateBuyMenu();
				SellingDataProcessing.encrypt();
				
				
				
				int range = new Random().nextInt(35);
				
				if(range==1) {
					
					AITradesManager.createAITrade();
					
				}
				
			}
		
		}, 300, 300); // Every minute 1200
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
			
			public void run() {
				
				AITradesManager.saveTradeRecords();
				
			}
		
		}, 36000, 36000); // Every half hour
		
	}
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
		
		final boolean tellPlayerOnJoin;
		
		if(ConfigManager.getInstance().getConfig().get("Tell_Player_About_Purchases_On_Join")!=null) {
			
			tellPlayerOnJoin = ConfigManager.getInstance().getConfig().getBoolean("Tell_Player_About_Purchases_On_Join");
			
		}else {
			
			ConfigManager.getInstance().getConfig().set("Tell_Player_About_Purchases_On_Join", true);
			ConfigManager.getInstance().saveConfig();
			
			tellPlayerOnJoin = true;
			
		}
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			  public void run() {
				  
				  if(tellPlayerOnJoin) {
					  
					  boolean cancelled = false;
					  boolean sold = false;
					  
					  for(SellingItem sale : SellingUtil.getPlayersSellingItems(e.getPlayer())) {
						  
						  if(sale.getStage().equals(SellStage.ACCEPTED)) {
							  sold = true;
						  } else if(sale.getStage().equals(SellStage.FAILED)) {
							  cancelled = true;
						  }
						  
					  }
					  
					  if(sold) {
						  e.getPlayer().sendMessage(ChatColor.GOLD+"There's Gems waiting for you, one or more of your "
						  		+ "items have been sold on the Grand Exchange!");
					  }
					  if(cancelled) {
						  e.getPlayer().sendMessage(ChatColor.RED+"One or more of your items have expired or been cancelled on the Grand Exchange!");
					  }
					  
				  }
				  
			  }
		}, 10L);
		
	}
	
	
	/*
	 * 
	 * /// Found buy, see if player has money
								
								int totalCountRemoved = 0;
								
								removing:
								for(ItemStack pItem : p.getInventory().getContents()) {
									
									if(totalCountRemoved>=SellingUtil.getTotalGems(sale)) {
										
										break removing;
									}
									
									if(pItem!=null) {
										
										if(pItem.isSimilar(ItemsCreator.createMoney(1))) {
											
											for(int n = 0; n<pItem.getAmount(); n++) {
												
												if(totalCountRemoved+n>=SellingUtil.getTotalGems(sale)) {
													
													totalCountRemoved += n;
													
													pItem.setAmount(pItem.getAmount()-n);
													
													break removing;
												}
												
											}
											
											totalCountRemoved += pItem.getAmount();
											
											pItem.setAmount(0);
											
											// Continue
											
										}
										
									}
									
								}
								
								if(totalCountRemoved>=SellingUtil.getTotalGems(sale)) {
									
									sale.setStage(SellStage.ACCEPTED);
									
									BuyingManager.updateBuyMenu();
									
									Utility.addItemToInventory(p.getInventory(), sale.getSellingItem(), p.getLocation());
									
									SellingDataProcessing.encrypt();
									
								} else {
									
									Utility.addItemToInventory(p.getInventory(), ItemsCreator.createMoney(totalCountRemoved), p.getLocation());
									
									p.closeInventory();
									
									Utility.tellSender(p, ChatColor.YELLOW+"You don't have enough Gems in your inventory to buy this!");
									
								}
								
								return;
	 * 
	 * 
	 */

}

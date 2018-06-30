package com.quzzar.ge.CustomHeads;

import java.lang.reflect.Field;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadUtil {
	
	
	public static ItemStack makeRawItemStack(TextureDatabase tex, int amt){
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, amt, (short) 3);
	    if(tex.getURL().isEmpty())return head;
	    
	    SkullMeta headMeta = (SkullMeta) head.getItemMeta();
	    GameProfile profile = new GameProfile(tex.getUUID(), null);
	    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", tex.getURL()).getBytes());
	    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
	    Field profileField = null;
	    try {
	        profileField = headMeta.getClass().getDeclaredField("profile");
	        profileField.setAccessible(true);
	        profileField.set(headMeta, profile);
	        
	    } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
	        e1.printStackTrace();
	    }
	    head.setItemMeta(headMeta);
	    return head;
	}
	
	
}

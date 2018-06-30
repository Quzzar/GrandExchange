package com.quzzar.ge.CustomHeads;

import java.util.UUID;

public enum TextureDatabase {
	
	
	LEFT_ARROW ("d49c678b-f0e6-4738-8651-8a36fa371e4e","http://textures.minecraft.net/texture/d6680abc6e580306ed6e31b7b78bfcd94a5ffd5d682cc6969a89deb529e8"),
	RIGHT_ARROW ("8294c84b-e6b0-491b-a0e6-8456b5886955","http://textures.minecraft.net/texture/2f39e1c3ddca9167ea6c5b56f934c15446edc31c82ff7f992849f3e67b8"),
	
	UP_ARROW ("610f0839-5e66-4655-8f20-883787652618","http://textures.minecraft.net/texture/db295a2abf7f8bcc3bb0d074a3796a9991635e5bfab194517d9c88b7fda1e95"),
	DOWN_ARROW ("5de81f67-d32f-4ccf-b35e-437f06c46ea1","http://textures.minecraft.net/texture/5e68b8eb4568dbc3ca1a18bb87ce8465b6d576439afffe545dacf439c03e4677");
	
	private String url;
	private UUID uuid;
	
	TextureDatabase(String rawUUID, String url){
		this.url = url;
		this.uuid = UUID.fromString(rawUUID);
	}
	
	public String getURL(){
		return url;
	}
	
	public UUID getUUID(){
		return uuid;
	}
	
	public static TextureDatabase getTexture(UUID inputUUID){
		for(TextureDatabase tex : TextureDatabase.values()){
			if(tex.getUUID().equals(inputUUID)){
				return tex;
			}
		}
		return null;
	}
	
	public static TextureDatabase getTexture(String inputName){
		for(TextureDatabase tex : TextureDatabase.values()){
			if(tex.name().equalsIgnoreCase(inputName)){
				return tex;
			}
		}
		return null;
	}
	
	
}

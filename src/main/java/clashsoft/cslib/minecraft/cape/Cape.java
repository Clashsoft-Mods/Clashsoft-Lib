package clashsoft.cslib.minecraft.cape;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class Cape
{
	protected String			name;
	protected ResourceLocation	location;
	
	public Cape(String name)
	{
		this.setName(name);
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ResourceLocation getLocation()
	{
		return this.location;
	}
	
	public void loadTexture(AbstractClientPlayer player)
	{
		ResourceLocation location = this.getLocation();
		player.func_152121_a(MinecraftProfileTexture.Type.CAPE, location);
	}
	
	public boolean isTextureLoaded(AbstractClientPlayer player)
	{
		ResourceLocation location = player.getLocationCape();
		return location == this.getLocation();
	}
	
	public void setName(String name)
	{
		this.name = name;
		this.location = new ResourceLocation("capes/" + name);
	}
}

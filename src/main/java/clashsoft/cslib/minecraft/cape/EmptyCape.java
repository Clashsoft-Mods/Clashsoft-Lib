package clashsoft.cslib.minecraft.cape;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public final class EmptyCape extends Cape
{
	public EmptyCape(String name)
	{
		super(name);
	}
	
	@Override
	public ResourceLocation getLocation()
	{
		return null;
	}
	
	@Override
	public boolean isTextureLoaded(AbstractClientPlayer player)
	{
		return player.getLocationCape() == null;
	}
}
package clashsoft.cslib.minecraft.cape;

import net.minecraft.client.entity.AbstractClientPlayer;

public final class DefaultCape extends Cape
{
	DefaultCape(String name)
	{
		super(name);
	}
	
	@Override
	public boolean isTextureLoaded(AbstractClientPlayer player)
	{
		Cape cape = CapeHelper.getCape(player);
		if (cape == null)
		{
			cape = Capes.noCape;
		}
		return cape.isTextureLoaded(player);
	}
	
	@Override
	public void loadTexture(AbstractClientPlayer player)
	{
		Cape cape = CapeHelper.getCape(player);
		if (cape == null || cape == this)
		{
			cape = Capes.noCape;
		}
		cape.loadTexture(player);
	}
}
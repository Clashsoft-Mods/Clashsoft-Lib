package clashsoft.cslib.minecraft.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

/**
 * The Class CustomPotion.
 */
public class CustomPotion extends Potion
{
	private ResourceLocation	iconFile;
	private boolean				instant;
	private int					customColor	= -1;
	private boolean				bad;
	
	public CustomPotion(String name, int color, boolean bad)
	{
		this(PotionRegistry.getID(name), name, color, bad);
	}
	
	public CustomPotion(int id, String name, int color, boolean bad)
	{
		super(id, bad, color);
		PotionRegistry.add(this, id, name);
		this.setPotionName(name);
		this.bad = bad;
	}
	
	public CustomPotion setIcon(ResourceLocation iconFile, int x, int y)
	{
		this.iconFile = iconFile;
		this.setIconIndex(x, y);
		return this;
	}
	
	public CustomPotion setCustomColor(int color)
	{
		if (color > 0)
		{
			this.customColor = color;
		}
		return this;
	}
	
	public CustomPotion setIsInstant(boolean instant)
	{
		this.instant = instant;
		return this;
	}
	
	@Override
	public int getStatusIconIndex()
	{
		if (this.iconFile != null)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(this.iconFile);
		}
		return super.getStatusIconIndex();
	}
	
	public int getCustomColor()
	{
		return this.customColor;
	}
	
	@Override
	public boolean isInstant()
	{
		return this.instant;
	}
	
	@Override
	public boolean isBadEffect()
	{
		return this.bad;
	}
	
	@Deprecated
	public static void expandPotionList(int size)
	{
		PotionRegistry.expandPotionList(size);
	}
}

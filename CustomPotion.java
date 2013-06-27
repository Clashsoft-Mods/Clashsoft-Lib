package clashsoft.clashsoftapi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class CustomPotion extends Potion
{
	private String customIconFile;
	private boolean instant;
	private int customColor;
	private boolean isBadEffect;
	
	public CustomPotion(String name, boolean bad, int color, boolean instant, String iconFile, int iconX, int iconY)
	{
		this(name, bad, color, instant, iconFile, iconX, iconY, -1);
	}
	
	public CustomPotion(String name, boolean bad, int color, boolean instant, String iconFile, int iconX, int iconY, int customColor)
	{
		super(getNextFreeID(), bad, color);
		this.setPotionName(name);
		this.instant = instant;
		this.setIconIndex(iconX, iconY);
		this.customColor = customColor;
		this.isBadEffect = bad;
		this.customIconFile = iconFile;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(customIconFile);
		return super.getStatusIconIndex();
	}
	
	public int getCustomColor()
	{
		return customColor;
	}
	
	public boolean isInstant()
	{
		return instant;
	}
	
	public boolean isBadEffect()
	{
		return isBadEffect;
	}
	
	public static int getNextFreeID()
	{
		int id = 32;
		for (int i = 0; i < potionTypes.length; i++)
		{
			if (potionTypes[i] == null)
			{
				id = i;
			}
		}
		return id;
	}

}

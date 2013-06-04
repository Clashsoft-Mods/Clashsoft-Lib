package clashsoft.clashsoftapi;

import clashsoft.mods.morepotions.ClientProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class CustomPotion extends Potion {

	private boolean instant;
	private int customColor;
	private boolean isBadEffect;
	
	public CustomPotion(String name, boolean bad, int color, boolean instant, int iconX, int iconY)
	{
		this(name, bad, color, instant, iconX, iconY, -1);
	}
	
	public CustomPotion(String name, boolean bad, int color, boolean instant, int iconX, int iconY, int customColor)
	{
		super(getNextFreeID(), bad, color);
		this.setPotionName(name);
		this.instant = instant;
		this.setIconIndex(iconX, iconY);
		this.customColor = customColor;
		this.isBadEffect = bad;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.customEffects);
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

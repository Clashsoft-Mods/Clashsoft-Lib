package clashsoft.clashsoftapi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class CustomPotion extends Potion
{
	private ResourceLocation	customIconFile;
	private boolean				instant;
	private int					customColor;
	private boolean				bad;
	
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
		this.bad = bad;
		this.customIconFile = new ResourceLocation(iconFile);
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
	
	@Override
	public boolean isInstant()
	{
		return instant;
	}
	
	public boolean getIsGoodOrNotGoodEffect()
	{
		return bad;
	}
	
	public static int getNextFreeID()
	{
		System.out.println("Searching for free potion id...");
		int id = 32;
		for (int i = 32; i < potionTypes.length; i++)
		{
			if (potionTypes[i] == null)
			{
				id = i;
				break;
			}
		}
		System.out.println("Free potion id found: " + id);
		return id;
	}
}

package clashsoft.clashsoftapi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Potion;

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
		Minecraft.getMinecraft().func_110434_K().func_110577_a(customIconFile);
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
	
	static
	{
		expandPotionList();
	}
	
	public static void expandPotionList()
	{
		Potion[] potionTypes = null;
		
		for (Field f : Potion.class.getDeclaredFields())
		{
			f.setAccessible(true);
			try
			{
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a"))
				{
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					
					potionTypes = (Potion[]) f.get(null);
					final Potion[] newPotionTypes = new Potion[1024];
					for (int i = 0; i < newPotionTypes.length; i++)
					{
						if (i < Potion.potionTypes.length)
							newPotionTypes[i] = Potion.potionTypes[i];
						else
							newPotionTypes[i] = null;
					}
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e)
			{
				System.err.println("Severe error, please report this to the mod author:");
				System.err.println(e);
			}
		}
	}
	
}

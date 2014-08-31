package clashsoft.cslib.minecraft.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class OreBase
{
	public static OreBase[]	oreBases	= new OreBase[16];
	private static int		nextID		= 0;
	
	public static OreBase	stone		= new OreBaseStone(0, "stone", 1F).register();
	public static OreBase	gravel		= new OreBase(1, "gravel", 0.8F).register();
	public static OreBase	dirt		= new OreBase(2, "dirt", 0.7F).register();
	
	public final int		id;
	public final String		name;
	
	public float			amountMultiplier;
	public float			xpMultiplier;
	
	public String			iconName;
	protected IIcon			icon;
	
	public OreBase(String name)
	{
		this(nextID(), name, 1F);
	}
	
	public OreBase(String name, float multiplier)
	{
		this(nextID(), name, multiplier);
	}
	
	public OreBase(int id, String name)
	{
		this(id, name, 1F);
	}
	
	public OreBase(int id, String name, float multiplier)
	{
		if (id < 0 || id >= oreBases.length)
		{
			throw new IllegalArgumentException("Invalid Ore Base ID");
		}
		
		this.id = id;
		this.name = name;
		this.iconName = name;
		
		this.amountMultiplier = multiplier;
		this.xpMultiplier = multiplier;
	}
	
	public static int nextID()
	{
		return nextID++;
	}
	
	public OreBase register()
	{
		oreBases[this.id] = this;
		return this;
	}
	
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icon = iconRegister.registerIcon(this.iconName);
	}
	
	public OreBase setIconName(String iconName)
	{
		this.iconName = iconName;
		return this;
	}
	
	public OreBase setAmountMultiplier(float amountMultiplier)
	{
		this.amountMultiplier = amountMultiplier;
		return this;
	}
	
	public OreBase setXpMultiplier(float xpMultiplier)
	{
		this.xpMultiplier = xpMultiplier;
		return this;
	}
	
	public String getUnlocalizedName(BlockOre2 ore)
	{
		return ore.getUnlocalizedName() + "." + this.name;
	}
	
	/**
	 * Returns true if the texture for the given {@link BlockOre2} {@code ore}
	 * shows the full block with pre-applied ore texture. This can be used for
	 * custom textures for certain ores.
	 * 
	 * @param ore
	 *            the ore
	 * @return true if the texture shows the full block with pre-applied ore
	 *         texture.
	 */
	public boolean isOverlayTexture(BlockOre2 ore)
	{
		return false;
	}
	
	/**
	 * Returns the texture for the given {@link BlockOre2} {@code ore} in the
	 * given {@code pass}. {@code pass} can have the following states:
	 * <p>
	 * <li><b>-1: Overlay Texture</b>: The texture should show the full block,
	 * with pre-applied ore texture. Used if and if only
	 * {@link #isOverlayTexture(BlockOre2)} return true.
	 * <li><b>0: Base Texture</b>: The texture of the base block / stone block,
	 * without the applied ore texture.
	 * <li><b>1: Ore Texture</b>: The texture of the ore block. This texture
	 * should be partially transparent and is overlayed on the Base Texture.
	 * 
	 * @param ore
	 *            the ore block
	 * @param pass
	 *            the pass
	 * @return the texture
	 */
	public IIcon getTexture(BlockOre2 ore, int pass)
	{
		if (pass == 1)
		{
			return ore.getOreIcon(this.id);
		}
		return this.icon;
	}
}

package clashsoft.cslib.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

/**
 * The Class CustomEnchantment.
 */
public class CustomEnchantment extends Enchantment
{
	/** The max level. */
	private int	maxLevel;
	
	/** The min level. */
	private int	minLevel;
	
	/**
	 * Instantiates a new custom enchantment.
	 * 
	 * @param id
	 *            the id
	 * @param weigth
	 *            the weigth
	 * @param minLevel
	 *            the min level
	 * @param maxLevel
	 *            the max level
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 */
	public CustomEnchantment(int id, String name, int weigth, int minLevel, int maxLevel, EnumEnchantmentType type)
	{
		super(id, new ResourceLocation(name), weigth, type);
		EnchantmentRegistry.add(this, id, name);
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.name = name;
	}
	
	/**
	 * Instantiates a new custom enchantment.
	 * 
	 * @param weigth
	 *            the weigth
	 * @param minLevel
	 *            the min level
	 * @param maxLevel
	 *            the max level
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 */
	public CustomEnchantment(int weigth, int minLevel, int maxLevel, EnumEnchantmentType type, String name)
	{
		this(EnchantmentRegistry.getID(name), name, weigth, minLevel, maxLevel, type);
	}
	
	@Override
	public int getMinLevel()
	{
		return this.maxLevel;
	}
	
	@Override
	public int getMaxLevel()
	{
		return this.minLevel;
	}
}

package clashsoft.clashsoftapi;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class CustomEnchantment extends Enchantment
{
	private int	maxLevel;
	private int	minLevel;
	
	public CustomEnchantment(int par1, int par2, int par3, EnumEnchantmentType par4EnumEnchantmentType, String par5)
	{
		super(getNextFreeID(), par1, par4EnumEnchantmentType);
		maxLevel = par2;
		minLevel = par3;
		this.name = par5;
	}
	
	/**
	 * Returns the minimum level that the enchantment can have.
	 */
	@Override
	public int getMinLevel()
	{
		return maxLevel;
	}
	
	/**
	 * Returns the maximum level that the enchantment can have.
	 */
	@Override
	public int getMaxLevel()
	{
		return minLevel;
	}
	
	private static int getNextFreeID()
	{
		int id = 32;
		for (int i = 0; i < enchantmentsList.length; i++)
		{
			if (enchantmentsList[i] == null)
			{
				id = i;
			}
		}
		return id;
	}
	
}

package clashsoft.cslib.minecraft.util;

import gnu.trove.strategy.HashingStrategy;
import clashsoft.cslib.minecraft.crafting.SimpleRecipeManager;
import clashsoft.cslib.minecraft.item.CSStacks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * An {@link HashingStrategy} for comparing {@link ItemStack}s. Use the
 * {@link #instance} if you want to compare the items and item damage, for
 * example in a {@link SimpleRecipeManager}. If you do want to compare the stack
 * size, you can use the {@link #stackSizeInstance}.
 * 
 * @author Clashsoft
 */
public class ItemStackHash implements HashingStrategy<ItemStack>
{
	/**
	 * True if the equals method should also compare the stack size.
	 */
	private boolean							compareStackSize;
	
	public static ItemStackHash	instance			= new ItemStackHash(false);
	public static ItemStackHash	stackSizeInstance	= new ItemStackHash(true);
	
	public ItemStackHash(boolean stackSize)
	{
		this.compareStackSize = stackSize;
	}
	
	@Override
	public int computeHashCode(ItemStack stack)
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (stack.getItem() == null ? 0 : Item.getIdFromItem(stack.getItem()));
		result = prime * result + stack.getItemDamage();
		if (this.compareStackSize)
		{
			result = prime * result + stack.stackSize;
		}
		result = prime * result + (stack.stackTagCompound == null ? 0 : stack.stackTagCompound.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(ItemStack o1, ItemStack o2)
	{
		if (CSStacks.equals(o1, o2))
		{
			if (this.compareStackSize)
			{
				return o1.stackSize == o2.stackSize;
			}
			return true;
		}
		return false;
	}
}

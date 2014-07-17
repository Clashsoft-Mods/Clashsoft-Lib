package clashsoft.cslib.minecraft.stack;

import gnu.trove.strategy.HashingStrategy;
import clashsoft.cslib.minecraft.crafting.SimpleRecipeManager;

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
	private boolean				compareStackSize;
	
	public static ItemStackHash	instance			= new ItemStackHash(false);
	public static ItemStackHash	stackSizeInstance	= new ItemStackHash(true);
	
	public ItemStackHash(boolean stackSize)
	{
		this.compareStackSize = stackSize;
	}
	
	@Override
	public int computeHashCode(ItemStack stack)
	{
		return this.compareStackSize ? CSStacks.stackHashCode(stack) : CSStacks.itemHashCode(stack);
	}
	
	@Override
	public boolean equals(ItemStack o1, ItemStack o2)
	{
		return this.compareStackSize ? CSStacks.stackEquals(o1, o2) : CSStacks.itemEquals(o1, o2);
	}
}

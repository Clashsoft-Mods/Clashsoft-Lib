package clashsoft.cslib.stack;

import gnu.trove.strategy.HashingStrategy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import clashsoft.cslib.crafting.manager.SimpleRecipeManager;

/**
 * An {@link HashingStrategy} for comparing {@link ItemStack}s. Use the
 * {@link #itemInstance} if you want to compare the items and item damage, for
 * example in a {@link SimpleRecipeManager}. If you do want to compare the stack
 * size, you can use the {@link #stackInstance}.
 * 
 * @author Clashsoft
 */
public class ItemStackHash implements HashingStrategy<ItemStack>
{
	private static final long	serialVersionUID	= -2377987946481368016L;
	
	/**
	 * True if the equals method should also compare the stack size.
	 */
	private boolean				compareStackSize;
	
	public static ItemStackHash	itemInstance		= new ItemStackHash(false);
	public static ItemStackHash	stackInstance		= new ItemStackHash(true);
	
	public ItemStackHash(boolean stackSize)
	{
		this.compareStackSize = stackSize;
	}
	
	@Override
	public int computeHashCode(ItemStack stack)
	{
		if (stack != null)
		{
			int id = Item.getIdFromItem(stack.getItem());
			if (this.compareStackSize)
			{
				id |= stack.stackSize << 8;
			}
		}
		return 0;
	}
	
	@Override
	public boolean equals(ItemStack o1, ItemStack o2)
	{
		return this.compareStackSize ? CSStacks.stackEquals(o1, o2) : CSStacks.itemEquals(o1, o2);
	}
}

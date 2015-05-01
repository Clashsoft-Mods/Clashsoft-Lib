package clashsoft.cslib.stack;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackFactory extends ItemStackTable
{
	public static final StackFactory	instance	= new StackFactory();
	
	public static ItemStack create(Block block)
	{
		return instance.createStack(Item.getItemFromBlock(block), 1, 0);
	}
	
	public static ItemStack create(Block block, int stackSize)
	{
		return instance.createStack(Item.getItemFromBlock(block), stackSize, 0);
	}
	
	public static ItemStack create(Block block, int stackSize, int metadata)
	{
		return instance.createStack(Item.getItemFromBlock(block), stackSize, metadata);
	}
	
	public static ItemStack create(Item item)
	{
		return instance.createStack(item, 1, 0);
	}
	
	public static ItemStack create(Item item, int stackSize)
	{
		return instance.createStack(item, stackSize, 0);
	}
	
	public static ItemStack create(Item item, int stackSize, int metadata)
	{
		return instance.createStack(item, stackSize, metadata);
	}
	
	public ItemStack createStack(Item item, int stackSize, int metadata)
	{
		ItemStack stack = this.get(item, stackSize, metadata);
		if (stack == null)
		{
			stack = new ItemStack(item, stackSize, metadata);
			this.put(item, stackSize, metadata, stack);
		}
		return stack;
	}
}

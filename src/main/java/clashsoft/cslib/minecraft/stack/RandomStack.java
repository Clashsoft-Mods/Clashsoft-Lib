package clashsoft.cslib.minecraft.stack;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class RandomStack
{
	public Item	item;
	public int	metadata;
	
	public RandomStack(Item item, int metadata)
	{
		this.item = item;
		this.metadata = metadata;
	}
	
	public RandomStack(Block block, int metadata)
	{
		this(Item.getItemFromBlock(block), metadata);
	}
	
	public abstract int getStackSize(Random random);
	
	public ItemStack toItemStack(Random random)
	{
		return new ItemStack(this.item, this.getStackSize(random), this.metadata);
	}
}

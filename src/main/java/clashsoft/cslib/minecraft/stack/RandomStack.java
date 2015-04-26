package clashsoft.cslib.minecraft.stack;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.RegistryDelegate;

public abstract class RandomStack
{
	protected Item					item;
	protected int					metadata;
	
	public RegistryDelegate<Item>	delegate;
	
	public RandomStack(Item item, int metadata)
	{
		this.item = item;
		this.delegate = item.delegate;
		this.metadata = metadata;
	}
	
	public RandomStack(Block block, int metadata)
	{
		this(Item.getItemFromBlock(block), metadata);
	}
	
	public Item getItem()
	{
		return this.delegate != null ? this.delegate.get() : this.item;
	}
	
	public int getMetadata()
	{
		return this.metadata;
	}
	
	public abstract int getStackSize(Random random);
	
	public ItemStack toItemStack(Random random)
	{
		return new ItemStack(this.getItem(), this.getStackSize(random), this.metadata);
	}
}

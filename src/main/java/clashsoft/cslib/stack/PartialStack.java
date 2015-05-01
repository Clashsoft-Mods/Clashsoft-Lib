package clashsoft.cslib.stack;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import dyvil.random.RandomUtils;

public class PartialStack extends RandomStack
{
	public Item		item;
	public int		metadata;
	public float	stackSize	= 1F;
	
	public PartialStack(Item item)
	{
		super(item, 0);
	}
	
	public PartialStack(Block block)
	{
		super(block, 0);
	}
	
	public PartialStack(Item item, float stackSize)
	{
		this(item, stackSize, 0);
	}
	
	public PartialStack(Block block, float stackSize)
	{
		this(block, stackSize, 0);
	}
	
	public PartialStack(Item item, float stackSize, int metadata)
	{
		super(item, metadata);
		this.stackSize = stackSize;
	}
	
	public PartialStack(Block block, float stackSize, int metadata)
	{
		super(block, metadata);
		this.stackSize = stackSize;
	}
	
	@Override
	public int getStackSize(Random random)
	{
		return RandomUtils.nextInt(random, this.stackSize);
	}
}

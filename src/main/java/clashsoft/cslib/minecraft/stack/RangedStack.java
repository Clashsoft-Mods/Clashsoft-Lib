package clashsoft.cslib.minecraft.stack;

import java.util.Random;

import clashsoft.cslib.random.CSRandom;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RangedStack extends RandomStack
{
	public int minSize;
	public int maxSize;
	
	public RangedStack(Item item)
	{
		super(item, 0);
	}
	
	public RangedStack(Block block)
	{
		super(block, 0);
	}
	
	public RangedStack(Item item, int minSize, int maxSize)
	{
		this(item, minSize, maxSize, 0);
	}
	
	public RangedStack(Block block, int minSize, int maxSize)
	{
		this(block, minSize, maxSize, 0);
	}
	
	public RangedStack(Item item, int minSize, int maxSize, int metadata)
	{
		super(item, metadata);
		this.minSize = minSize;
		this.maxSize = maxSize;
	}
	
	public RangedStack(Block block, int minSize, int maxSize, int metadata)
	{
		super(block, metadata);
		this.minSize = minSize;
		this.maxSize = maxSize;
	}
	
	@Override
	public int getStackSize(Random random)
	{
		return CSRandom.nextInt(random, this.minSize, this.maxSize);
	}
}

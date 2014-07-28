package clashsoft.cslib.minecraft.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.oredict.OreDictionary;

public class OreGen extends WorldGenerator
{
	private Block	block;
	private int		metadata;
	private Block	target;
	private int		targetMetadata	= OreDictionary.WILDCARD_VALUE;
	
	private int		count;
	
	private int		minY;
	private int		maxY;
	
	public OreGen(Block block, int count)
	{
		this(block, count, Blocks.stone);
	}
	
	public OreGen(Block block, int count, Block target)
	{
		this.block = block;
		this.count = count;
		this.target = target;
	}
	
	public OreGen(Block block, int meta, int count, Block target)
	{
		this(block, count, target);
		this.metadata = meta;
	}
	
	public OreGen generate(Block block)
	{
		this.block = block;
		return this;
	}
	
	public OreGen generate(Block block, int metadata)
	{
		this.block = block;
		this.metadata = metadata;
		return this;
	}
	
	public OreGen replace(Block target)
	{
		this.target = target;
		return this;
	}
	
	public OreGen replace(Block target, int metadata)
	{
		this.target = target;
		this.targetMetadata = metadata;
		return this;
	}
	
	public OreGen amount(int count)
	{
		this.count = count;
		return this;
	}
	
	public OreGen below(int maxY)
	{
		this.maxY = maxY;
		return this;
	}
	
	public OreGen above(int minY)
	{
		this.minY = minY;
		return this;
	}
	
	public OreGen between(int minY, int maxY)
	{
		this.minY = minY;
		this.maxY = maxY;
		return this;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		float f = random.nextFloat() * 3.141593F;
		double d0 = x + 8 + MathHelper.sin(f) * this.count / 8.0F;
		double d1 = x + 8 - (MathHelper.sin(f) * this.count / 8.0F);
		double d2 = z + 8 + MathHelper.cos(f) * this.count / 8.0F;
		double d3 = z + 8 - (MathHelper.cos(f) * this.count / 8.0F);
		double d4 = y + random.nextInt(3) - 2;
		double d5 = y + random.nextInt(3) - 2;
		
		for (int l = 0; l <= this.count; ++l)
		{
			double d6 = d0 + (d1 - d0) * l / this.count;
			double d7 = d4 + (d5 - d4) * l / this.count;
			double d8 = d2 + (d3 - d2) * l / this.count;
			double d9 = random.nextDouble() * this.count / 16.0D;
			double d10 = (MathHelper.sin(l * 3.141593F / this.count) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * 3.141593F / this.count) + 1.0F) * d9 + 1.0D;
			int x0 = MathHelper.floor_double(d6 - (d10 / 2.0D));
			int y0 = MathHelper.floor_double(d7 - (d11 / 2.0D));
			int z0 = MathHelper.floor_double(d8 - (d10 / 2.0D));
			int x2 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int y2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int z2 = MathHelper.floor_double(d8 + d10 / 2.0D);
			
			for (int x1 = x0; x1 <= x2; ++x1)
			{
				double d12 = (x1 + 0.5D - d6) / d10 / 2.0D;
				
				if (d12 * d12 >= 1.0D)
					continue;
				
				for (int y1 = y0; y1 <= y2; ++y1)
				{
					double d13 = (y1 + 0.5D - d7) / d11 / 2.0D;
					
					if (d12 * d12 + d13 * d13 >= 1.0D)
						continue;
					
					for (int z1 = z0; z1 <= z2; ++z1)
					{
						double d14 = (z1 + 0.5D - d8) / d10 / 2.0D;
						
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && this.isReplacable(world, x1, y1, z1))
						{
							world.setBlock(x1, y1, z1, this.block, this.metadata, 2);
						}
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean isReplacable(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if (block.isReplaceableOreGen(world, x, y, z, this.target))
		{
			int metadata = world.getBlockMetadata(x, y, z);
			return this.targetMetadata == OreDictionary.WILDCARD_VALUE || this.targetMetadata == metadata;
		}
		return false;
	}
}

package clashsoft.cslib.world.gen;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.google.common.base.Predicate;

public class OreGen extends WorldGenerator
{
	public IBlockState				ore;
	public Predicate<IBlockState>	target;
	
	public int						amount;
	
	public int						veigns;
	public int						minY;
	public int						maxY;
	
	public Set<BiomeGenBase>		includedBiomes;
	public Set<BiomeGenBase>		excludedBiomes;
	
	public OreGen()
	{
	}
	
	public OreGen(int amount, int veigns, int above, int below)
	{
		this.amount = amount;
		this.veigns = veigns;
		this.minY = above;
		this.maxY = below;
	}
	
	public OreGen(int amount, int veigns, int below)
	{
		this.amount = amount;
		this.veigns = veigns;
		this.maxY = below;
	}
	
	public OreGen(IBlockState ore, int amount)
	{
		this(ore, amount, BlockHelper.forBlock(Blocks.stone));
	}
	
	public OreGen(IBlockState ore, int amount, Predicate<IBlockState> target)
	{
		this.ore = ore;
		this.amount = amount;
		this.target = target;
	}
	
	public OreGen generate(IBlockState block)
	{
		this.ore = block;
		return this;
	}
	
	public OreGen replace(Predicate<IBlockState> target)
	{
		this.target = target;
		return this;
	}
	
	public OreGen amount(int amount)
	{
		this.amount = amount;
		return this;
	}
	
	public OreGen veigns(int veigns)
	{
		this.veigns = veigns;
		return this;
	}
	
	public OreGen below(int maxY)
	{
		this.minY = 0;
		this.maxY = maxY;
		return this;
	}
	
	public OreGen above(int minY)
	{
		this.minY = minY;
		this.maxY = 256;
		return this;
	}
	
	public OreGen between(int minY, int maxY)
	{
		this.minY = minY;
		this.maxY = maxY;
		return this;
	}
	
	public OreGen inBiome(BiomeGenBase biome)
	{
		if (this.includedBiomes == null)
		{
			this.includedBiomes = new HashSet();
		}
		this.includedBiomes.add(biome);
		return this;
	}
	
	public OreGen notInBiome(BiomeGenBase biome)
	{
		if (this.excludedBiomes == null)
		{
			this.excludedBiomes = new HashSet();
		}
		this.excludedBiomes.add(biome);
		return this;
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		int diff = this.maxY - this.minY + 1;
		if (diff <= 1 || this.veigns == 0 || this.amount == 0)
			return false;
		
		for (int i = 0; i < this.veigns; i++)
		{
			int x1 = pos.getX() + random.nextInt(16);
			int y1 = this.minY + random.nextInt(diff);
			int z1 = pos.getY() + random.nextInt(16);
			this.generateVeign(world, random, x1, y1, z1);
		}
		return true;
	}
	
	public void generateVeign(World world, Random random, int x, int y, int z)
	{
		if (!this.isValidBiome(world, new BlockPos(x, y, z)))
			return;
		
		float f = random.nextFloat() * 3.141593F;
		int c = this.amount;
		double d0 = x + 8 + MathHelper.sin(f) * c / 8.0F;
		double d1 = x + 8 - MathHelper.sin(f) * c / 8.0F;
		double d2 = z + 8 + MathHelper.cos(f) * c / 8.0F;
		double d3 = z + 8 - MathHelper.cos(f) * c / 8.0F;
		double d4 = y + random.nextInt(3) - 2;
		double d5 = y + random.nextInt(3) - 2;
		
		for (int l = 0; l <= c; ++l)
		{
			double d6 = d0 + (d1 - d0) * l / c;
			double d7 = d4 + (d5 - d4) * l / c;
			double d8 = d2 + (d3 - d2) * l / c;
			double d9 = random.nextDouble() * c / 16.0D;
			double d10 = (MathHelper.sin(l * 3.141593F / c) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * 3.141593F / c) + 1.0F) * d9 + 1.0D;
			int x0 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int y0 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int z0 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int x2 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int y2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int z2 = MathHelper.floor_double(d8 + d10 / 2.0D);
			
			for (int x1 = x0; x1 <= x2; ++x1)
			{
				double d12 = (x1 + 0.5D - d6) / d10 / 2.0D;
				
				if (d12 * d12 >= 1.0D)
				{
					continue;
				}
				
				for (int y1 = y0; y1 <= y2; ++y1)
				{
					double d13 = (y1 + 0.5D - d7) / d11 / 2.0D;
					
					if (d12 * d12 + d13 * d13 >= 1.0D)
					{
						continue;
					}
					
					for (int z1 = z0; z1 <= z2; ++z1)
					{
						double d14 = (z1 + 0.5D - d8) / d10 / 2.0D;
						
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
						{
							BlockPos pos = new BlockPos(x1, y1, z1);
							if (this.isReplacable(world, pos))
							{
								world.setBlockState(pos, this.ore);
							}
						}
					}
				}
			}
		}
	}
	
	public boolean isReplacable(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock().isReplaceableOreGen(world, pos, this.target);
	}
	
	public boolean isValidBiome(World world, BlockPos pos)
	{
		if (this.includedBiomes == null && this.excludedBiomes == null)
			return true;
		
		BiomeGenBase biome = world.getBiomeGenForCoords(pos);
		if (this.includedBiomes != null && !this.includedBiomes.contains(biome))
			return false;
		if (this.excludedBiomes != null && this.excludedBiomes.contains(biome))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OreGen [block=").append(this.ore);
		builder.append(", target=").append(this.target);
		builder.append(", amount=").append(this.amount);
		builder.append(", veigns=").append(this.veigns);
		builder.append(", minY=").append(this.minY);
		builder.append(", maxY=").append(this.maxY);
		if (this.includedBiomes != null)
		{
			builder.append(", includedBiomes=").append(this.includedBiomes);
		}
		if (this.excludedBiomes != null)
		{
			builder.append(", excludedBiomes=").append(this.excludedBiomes);
		}
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + this.amount;
		result = prime * result + (this.ore == null ? 0 : this.ore.hashCode());
		result = prime * result + (this.excludedBiomes == null ? 0 : this.excludedBiomes.hashCode());
		result = prime * result + (this.includedBiomes == null ? 0 : this.includedBiomes.hashCode());
		result = prime * result + this.maxY;
		result = prime * result + this.minY;
		result = prime * result + (this.target == null ? 0 : this.target.hashCode());
		result = prime * result + this.veigns;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		OreGen other = (OreGen) obj;
		if (this.amount != other.amount)
			return false;
		if (!this.ore.equals(other.ore))
			return false;
		if (this.excludedBiomes == null)
		{
			if (other.excludedBiomes != null)
				return false;
		}
		else if (!this.excludedBiomes.equals(other.excludedBiomes))
			return false;
		if (this.includedBiomes == null)
		{
			if (other.includedBiomes != null)
				return false;
		}
		else if (!this.includedBiomes.equals(other.includedBiomes))
			return false;
		if (this.maxY != other.maxY)
			return false;
		if (this.minY != other.minY)
			return false;
		if (!this.target.equals(other.target))
			return false;
		if (this.veigns != other.veigns)
			return false;
		return true;
	}
}

package clashsoft.cslib.minecraft.world.gen;

import java.util.Random;
import java.util.Set;

import clashsoft.cslib.collections.ArraySet;
import clashsoft.cslib.logging.CSLog;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.oredict.OreDictionary;

public class OreGen extends WorldGenerator
{
	public Block				block;
	public int					metadata;
	public Block				target			= Blocks.stone;
	public int					targetMetadata	= OreDictionary.WILDCARD_VALUE;
	
	public int					amount;
	
	public int					veigns;
	public int					minY;
	public int					maxY;
	
	public Set<BiomeGenBase>	includedBiomes;
	public Set<BiomeGenBase>	excludedBiomes;
	
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
	
	public OreGen(Block block, int amount)
	{
		this(block, amount, Blocks.stone);
	}
	
	public OreGen(Block block, int amount, Block target)
	{
		this.block = block;
		this.amount = amount;
		this.target = target;
	}
	
	public OreGen(Block block, int meta, int amount, Block target)
	{
		this(block, amount, target);
		this.metadata = meta;
	}
	
	public OreGen generate(Block block)
	{
		this.block = block;
		this.metadata = 0;
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
		this.targetMetadata = 0;
		return this;
	}
	
	public OreGen replace(Block target, int metadata)
	{
		this.target = target;
		this.targetMetadata = metadata;
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
			this.includedBiomes = new ArraySet();
		}
		this.includedBiomes.add(biome);
		return this;
	}
	
	public OreGen notInBiome(BiomeGenBase biome)
	{
		if (this.excludedBiomes == null)
		{
			this.excludedBiomes = new ArraySet();
		}
		this.excludedBiomes.add(biome);
		return this;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		if (this.block == null)
		{
			CSLog.error("Cannot generate Ores when the block is set to null!");
			return false;
		}
		if (this.target == null)
		{
			CSLog.error("Cannot generate Ores when the target is set to null!");
			return false;
		}
		
		int diff = this.maxY - this.minY + 1;
		if (diff <= 1 || this.veigns == 0 || this.amount == 0)
		{
			return false;
		}
		
		for (int i = 0; i < this.veigns; i++)
		{
			int x1 = x + random.nextInt(16);
			int y1 = this.minY + random.nextInt(diff);
			int z1 = z + random.nextInt(16);
			this.generateVeign(world, random, x1, y1, z1);
		}
		return true;
	}
	
	public void generateVeign(World world, Random random, int x, int y, int z)
	{
		if (!this.isValidBiome(world, x, y, z))
		{
			return;
		}
		
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
						
						if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && this.isReplacable(world, x1, y1, z1))
						{
							world.setBlock(x1, y1, z1, this.block, this.metadata, 2);
						}
					}
				}
			}
		}
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
	
	public boolean isValidBiome(World world, int x, int y, int z)
	{
		if (this.includedBiomes == null && this.excludedBiomes == null)
		{
			// Fast
			return true;
		}
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		if (this.includedBiomes != null && !this.includedBiomes.contains(biome))
		{
			return false;
		}
		if (this.excludedBiomes != null && this.excludedBiomes.contains(biome))
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("OreGen [block=").append(this.block);
		if (this.metadata != 0)
		{
			builder.append(", metadata=").append(this.metadata);
		}
		if (this.target != Blocks.stone || this.targetMetadata != 0)
		{
			builder.append(", target=").append(this.target);
			builder.append(", targetMetadata=").append(this.targetMetadata);
		}
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
		result = prime * result + (this.block == null ? 0 : this.block.hashCode());
		result = prime * result + (this.excludedBiomes == null ? 0 : this.excludedBiomes.hashCode());
		result = prime * result + (this.includedBiomes == null ? 0 : this.includedBiomes.hashCode());
		result = prime * result + this.maxY;
		result = prime * result + this.metadata;
		result = prime * result + this.minY;
		result = prime * result + (this.target == null ? 0 : this.target.hashCode());
		result = prime * result + this.targetMetadata;
		result = prime * result + this.veigns;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (this.getClass() != obj.getClass())
		{
			return false;
		}
		OreGen other = (OreGen) obj;
		if (this.amount != other.amount)
		{
			return false;
		}
		if (this.block != other.block)
		{
			return false;
		}
		if (this.excludedBiomes == null)
		{
			if (other.excludedBiomes != null)
			{
				return false;
			}
		}
		else if (!this.excludedBiomes.equals(other.excludedBiomes))
		{
			return false;
		}
		if (this.includedBiomes == null)
		{
			if (other.includedBiomes != null)
			{
				return false;
			}
		}
		else if (!this.includedBiomes.equals(other.includedBiomes))
		{
			return false;
		}
		if (this.maxY != other.maxY)
		{
			return false;
		}
		if (this.metadata != other.metadata)
		{
			return false;
		}
		if (this.minY != other.minY)
		{
			return false;
		}
		if (this.target != other.target)
		{
			return false;
		}
		if (this.targetMetadata != other.targetMetadata)
		{
			return false;
		}
		if (this.veigns != other.veigns)
		{
			return false;
		}
		return true;
	}
}

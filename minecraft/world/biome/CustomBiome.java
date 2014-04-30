package clashsoft.cslib.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class CustomBiome extends BiomeGenBase implements ICustomBiome
{
	public CustomBiome(int id)
	{
		super(id);
	}
	
	public CustomBiome(int id, boolean register)
	{
		super(id, register);
	}
	
	@Override
	public Block getTopBlock(int x, int y, int z)
	{
		return this.topBlock;
	}
	
	@Override
	public Block getFillerBlock(int x, int y, int z)
	{
		return this.fillerBlock;
	}
	
	@Override
	public Block getStoneBlock(int x, int y, int z)
	{
		return Blocks.stone;
	}
	
	@Override
	public byte getTopMetadata(int x, int y, int z)
	{
		return 0;
	}
	
	@Override
	public byte getFillerMetadata(int x, int y, int z)
	{
		return 0;
	}
	
	@Override
	public byte getStoneMetadata(int x, int y, int z)
	{
		return 0;
	}
	
	@Override
	public void genTerrainBlocks(World block, Random random, Block[] blocks, byte[] metadatas, int x, int z, double noise)
	{
		Block filler = this.fillerBlock;
		Block topBlock = this.topBlock;
		Block block1 = filler;
		byte metadata = 0;
		int k = -1;
		int l = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int x1 = x & 0xF;
		int z1 = z & 0xF;
		int k1 = blocks.length / 256;
		
		for (int y = 255; y >= 0; --y)
		{
			int index = (z1 * 16 + x1) * k1 + y;
			
			if (y <= random.nextInt(5))
			{
				blocks[index] = Blocks.bedrock;
			}
			else
			{
				Block block2 = blocks[index];
				
				if (block2 != null && block2.getMaterial() != Material.air)
				{
					if (block2 != filler)
						continue;
					
					if (k == -1)
					{
						if (l <= 0)
						{
							topBlock = null;
							block1 = this.getStoneBlock(x, y, z);
							metadata = this.getStoneMetadata(x, y, z);
						}
						else if ((y > 58) && (y < 65))
						{
							block1 = this.getFillerBlock(x, index, z);
							metadata = this.getFillerMetadata(x, y, z);
						}
						
						if (y < 63 && (topBlock == null || topBlock.getMaterial() == Material.air))
						{
							if (getFloatTemperature(x, y, z) < 0.15F)
							{
								topBlock = Blocks.ice;
								metadata = 0;
							}
							else
							{
								topBlock = Blocks.water;
								metadata = 0;
							}
						}
						
						k = l;
						
						if (y >= 62)
						{
							blocks[index] = topBlock;
							metadatas[index] = metadata;
						}
						else if (y < 56 - l)
						{
							topBlock = null;
							block1 = this.getStoneBlock(x, y, z);
							metadata = this.getStoneMetadata(x, y, z);
							blocks[index] = Blocks.gravel;
						}
						else
						{
							blocks[index] = block1;
							metadatas[index] = metadata;
						}
					}
					else if (k > 0)
					{
						--k;
						blocks[index] = block1;
						
						if (k == 0 && block1 == Blocks.sand)
						{
							k = random.nextInt(4) + Math.max(0, y - 63);
							block1 = Blocks.sandstone;
						}
					}
				}
				else
				{
					k = -1;
				}
			}
		}
	}
}

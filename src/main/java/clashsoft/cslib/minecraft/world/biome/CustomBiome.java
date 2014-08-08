package clashsoft.cslib.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class CustomBiome extends BiomeGenBase implements ICustomBiome
{
	public Block stoneBlock = Blocks.stone;
	
	public CustomBiome(int id)
	{
		super(id);
	}
	
	public CustomBiome(int id, boolean register)
	{
		super(id, register);
	}
	
	@Override
	public Block getTopBlock()
	{
		return this.topBlock;
	}
	
	@Override
	public Block getFillerBlock()
	{
		return this.fillerBlock;
	}
	
	@Override
	public Block getStoneBlock()
	{
		return this.stoneBlock;
	}
	
	@Override
	public byte getTopMetadata()
	{
		return 0;
	}
	
	@Override
	public byte getFillerMetadata()
	{
		return 0;
	}
	
	@Override
	public byte getStoneMetadata()
	{
		return 0;
	}
	
	@Override
	public int getBedrockHeight()
	{
		return 5;
	}
	
	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metadatas, int x, int z, double noise)
	{
		int count = blocks.length >> 8;
		int x1 = x & 0xF;
		int z1 = z & 0xF;
		int index1 = ((z1 << 4) + x1) * count;
		int grassHeight = -1;
		int randomNoise = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		
		int bedrock = this.getBedrockHeight();
		Block stone = this.getStoneBlock();
		byte stonem = this.getStoneMetadata();
		Block top = this.getTopBlock();
		byte topm = this.getTopMetadata();
		Block filler = this.getFillerBlock();
		byte fillerm = this.getFillerMetadata();
		
		for (int y = 255; y >= 0; --y)
		{
			int index = index1 + y;
			
			if (y <= random.nextInt(bedrock))
            {
                blocks[index] = Blocks.bedrock;
                continue;
            }
			
			Block block = blocks[index];
			
			if (block != null && block.getMaterial() != Material.air)
			{
				if (grassHeight == -1)
				{
					grassHeight = y;
					
					blocks[index] = top;
					metadatas[index] = topm;
				}
				else if (y >= grassHeight - randomNoise)
				{
					blocks[index] = filler;
					metadatas[index] = fillerm;
				}
				else
				{
					blocks[index] = stone;
					metadatas[index] = stonem;
				}
			}
			// Air block
			else
			{
				grassHeight = -1;
			}
		}
	}
}

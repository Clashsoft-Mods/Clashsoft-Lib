package clashsoft.cslib.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class CustomBiome extends BiomeGenBase implements ICustomBiome
{
	public byte		topMetadata;
	public byte		fillerMetadata;
	public Block	stoneBlock	= Blocks.stone;
	public byte		stoneMetadata;
	
	public Block	waterBlock	= Blocks.water;
	public byte		waterMetadata;
	
	public boolean	isOcean;
	
	public CustomBiome(int id)
	{
		super(id);
	}
	
	public CustomBiome(int id, boolean register)
	{
		super(id, register);
	}
	
	@Override
	public TempCategory getTempCategory()
	{
		return this.isOcean ? TempCategory.OCEAN : super.getTempCategory();
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
	public Block getWaterBlock()
	{
		return this.waterBlock;
	}
	
	@Override
	public byte getTopMetadata()
	{
		return this.topMetadata;
	}
	
	@Override
	public byte getFillerMetadata()
	{
		return this.fillerMetadata;
	}
	
	@Override
	public byte getStoneMetadata()
	{
		return this.stoneMetadata;
	}
	
	@Override
	public byte getWaterMetadata()
	{
		return this.waterMetadata;
	}
	
	@Override
	public int getBedrockHeight()
	{
		return 5;
	}
	
	@Override
	public int getWaterLevel()
	{
		return this.isOcean ? 64 : 0;
	}
	
	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metadatas, int x, int z, double noise)
	{
		int count = blocks.length >> 8;
		int x1 = x & 0xF;
		int z1 = z & 0xF;
		int index1 = ((z1 << 4) + x1) * count;
		int randomNoise = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int grassHeight = -1;
		boolean foundTop = false;
		
		int bedrock = this.getBedrockHeight();
		boolean genBedrock = bedrock > 0;
		int waterLevel = this.getWaterLevel();
		
		Block top = this.getTopBlock();
		byte topm = this.getTopMetadata();
		Block filler = this.getFillerBlock();
		byte fillerm = this.getFillerMetadata();
		Block stone = this.getStoneBlock();
		byte stonem = this.getStoneMetadata();
		Block water = this.getWaterBlock();
		byte waterm = this.getWaterMetadata();
		
		for (int y = 255; y >= 0; --y)
		{
			int index = index1 + y;
			
			if (genBedrock && y <= random.nextInt(bedrock))
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
					foundTop = true;
					
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
			else
			{
				grassHeight = -1;
				if (!foundTop && y < waterLevel)
				{
					blocks[index] = water;
					metadatas[index] = waterm;
				}
			}
		}
	}
}

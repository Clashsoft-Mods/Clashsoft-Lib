package clashsoft.cslib.minecraft.world.biome;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class CustomBiome extends BiomeGenBase implements ICustomBiome
{
	public IBlockState stoneBlock;
	public IBlockState waterBlock;
	
	public int		waterLevel;
	
	public CustomBiome(int id)
	{
		super(id);
		this.addSpawnEntries();
	}
	
	public CustomBiome(int id, boolean register)
	{
		super(id, register);
		this.addSpawnEntries();
	}
	
	public void addSpawnEntries()
	{
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
	}
	
	@Override
	public TempCategory getTempCategory()
	{
		return this.waterLevel > 0 ? TempCategory.OCEAN : super.getTempCategory();
	}
	
	@Override
	public IBlockState getTopBlock()
	{
		return this.topBlock;
	}
	
	@Override
	public IBlockState getFillerBlock()
	{
		return this.fillerBlock;
	}
	
	@Override
	public IBlockState getStoneBlock()
	{
		return this.stoneBlock;
	}
	
	@Override
	public IBlockState getWaterBlock()
	{
		return this.waterBlock;
	}
	
	@Override
	public int getBedrockHeight()
	{
		return 5;
	}
	
	@Override
	public int getWaterLevel()
	{
		return this.waterLevel;
	}
	
	@Override
	public void genTerrainBlocks(World world, Random random, ChunkPrimer primer, int x, int z, double noise)
	{
		int x1 = x & 0xF;
		int z1 = z & 0xF;
		int index1 = ((z1 << 4) + x1) << 8;
		int randomNoise = (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int grassHeight = -1;
		boolean foundTop = false;
		
		int bedrockHeight = this.getBedrockHeight();
		boolean genBedrock = bedrockHeight > 0;
		int waterLevel = this.getWaterLevel();
		
		IBlockState top = this.getTopBlock();
		IBlockState filler = this.getFillerBlock();
		IBlockState stone = this.getStoneBlock();
		IBlockState water = this.getWaterBlock();
		IBlockState bedrock = Blocks.bedrock.getDefaultState();
		
		for (int y = 255; y >= 0; --y)
		{
			int index = index1 | y;
			if (genBedrock && y <= random.nextInt(bedrockHeight))
			{
				primer.setBlockState(index, bedrock);
				continue;
			}
			
			IBlockState block = primer.getBlockState(index);
			
			if (block != null && block.getBlock().getMaterial() == Material.air)
			{
				if (grassHeight == -1)
				{
					grassHeight = y;
					foundTop = true;
					
					primer.setBlockState(index, top);
				}
				else if (y >= grassHeight - randomNoise)
				{
					primer.setBlockState(index, filler);
				}
				else
				{
					primer.setBlockState(index, stone);
				}
			}
			else
			{
				grassHeight = -1;
				if (!foundTop && y < waterLevel)
				{
					primer.setBlockState(index, water);
				}
			}
		}
	}
}

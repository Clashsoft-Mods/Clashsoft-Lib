package clashsoft.cslib.world.biome;

import net.minecraft.block.state.IBlockState;

public interface ICustomBiome
{
	public IBlockState getTopBlock();
	
	public IBlockState getFillerBlock();
	
	public IBlockState getStoneBlock();
	
	public IBlockState getWaterBlock();
	
	public int getBedrockHeight();
	
	public int getWaterLevel();
}

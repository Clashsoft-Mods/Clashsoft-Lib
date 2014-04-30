package clashsoft.cslib.minecraft.world.biome;

import net.minecraft.block.Block;

public interface ICustomBiome
{
	public Block getTopBlock(int x, int y, int z);
	
	public Block getFillerBlock(int x, int y, int z);
	
	public Block getStoneBlock(int x, int y, int z);
	
	public int getTopMetadata(int x, int y, int z);
	
	public int getFillerMetadata(int x, int y, int z);
	
	public int getStoneMetadata(int x, int y, int z);
}

package clashsoft.cslib.minecraft.world.biome;

import net.minecraft.block.Block;

public interface ICustomBiome
{
	public Block getTopBlock();
	
	public Block getFillerBlock();
	
	public Block getStoneBlock();
	
	public Block getWaterBlock();
	
	public byte getTopMetadata();
	
	public byte getFillerMetadata();
	
	public byte getStoneMetadata();
	
	public byte getWaterMetadata();
	
	public int getBedrockHeight();
	
	public int getWaterLevel();
}

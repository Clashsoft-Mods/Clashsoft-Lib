package clashsoft.cslib.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.MapGenCaves;

public class CustomCaveGen extends MapGenCaves
{
	@Override
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop)
	{
		data[index] = Blocks.air;
	}
}

package clashsoft.cslib.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class CustomCaveGen extends MapGenCaves
{
	private static final IBlockState	STATE	= Blocks.air.getDefaultState();
	
	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up)
	{
		data.setBlockState(x, y, z, STATE);
	}
}

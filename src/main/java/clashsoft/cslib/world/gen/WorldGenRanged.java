package clashsoft.cslib.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import clashsoft.cslib.stack.StackFactory;

public class WorldGenRanged extends WorldGenerator
{
	public int			amount;
	public int			rangeX;
	public int			rangeY;
	public int			rangeZ;
	
	public IBlockState	block;
	
	public WorldGenRanged(IBlockState block)
	{
		this(64, 8, 4, 8, block);
	}
	
	public WorldGenRanged(int amount, int rangeX, int rangeY, int rangeZ, IBlockState block)
	{
		this.amount = amount;
		this.rangeX = rangeX;
		this.rangeY = rangeY;
		this.rangeZ = rangeZ;
		
		this.block = block;
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		ItemStack stack = StackFactory.create(this.block.getBlock(), 1, this.block.getBlock().getMetaFromState(this.block));
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		for (int i = 0; i < this.amount; ++i)
		{
			int x1 = x + random.nextInt(this.rangeX) - random.nextInt(this.rangeX);
			int y1 = y + random.nextInt(this.rangeY) - random.nextInt(this.rangeY);
			int z1 = z + random.nextInt(this.rangeZ) - random.nextInt(this.rangeZ);
			
			BlockPos pos1 = new BlockPos(x1, y1, z1);
			if (world.isAirBlock(pos1) && this.block.getBlock().canReplace(world, pos, EnumFacing.UP, stack))
			{
				this.func_175903_a(world, pos1, this.block);
			}
		}
		
		return true;
	}
}

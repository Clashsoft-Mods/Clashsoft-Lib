package clashsoft.cslib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomLog extends BlockCustomPillar
{
	public BlockCustomLog(String name)
	{
		super(Material.wood, name);
	}
	
	public BlockCustomLog(String[] names)
	{
		super(Material.wood, names);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(2F);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		final byte range = 4;
		final int range1 = range + 1;
		
		if (world.isAreaLoaded(pos.add(-range1, -range1, -range1), pos.add(range1, range1, range1)))
		{
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			for (int k1 = -range; k1 <= range; ++k1)
			{
				for (int l1 = -range; l1 <= range; ++l1)
				{
					for (int i2 = -range; i2 <= range; ++i2)
					{
						BlockPos pos1 = new BlockPos(x + k1, y + l1, z + i2);
						world.getBlockState(pos1).getBlock().beginLeavesDecay(world, pos);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canSustainLeaves(IBlockAccess world, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos)
	{
		return true;
	}
}

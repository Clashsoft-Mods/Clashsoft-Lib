package clashsoft.cslib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomLamp extends CustomBlock
{
	public static final PropertyBool	activeProperty	= PropertyBool.create("active");
	
	public int[]						lightValues;
	
	public BlockCustomLamp(String[] names)
	{
		super(Material.redstoneLight, names, null);
	}
	
	public void setLightValue(int metadata, float value)
	{
		this.setLightValue(metadata, (int) (value * 15F));
	}
	
	public void setLightValue(int metadata, int value)
	{
		if (this.lightValues == null)
		{
			this.lightValues = new int[16];
		}
		this.lightValues[metadata] = value;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
	{
		int metadata = (int) world.getBlockState(pos).getValue(typeProperty);
		if (this.lightValues == null || metadata < 0 || metadata >= this.lightValues.length)
			return this.lightValue;
		return this.lightValues[metadata];
	}
	
	private void updateState(World world, BlockPos pos, IBlockState state)
	{
		if (world.isRemote)
			return;
		
		if ((boolean) state.getValue(activeProperty))
		{
			if (!world.isBlockPowered(pos))
			{
				world.setBlockState(pos, state.withProperty(activeProperty, false), 2);
			}
		}
		else if (world.isBlockPowered(pos))
		{
			world.setBlockState(pos, state.withProperty(activeProperty, true), 2);
		}
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		this.updateState(world, pos, state);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		this.updateState(world, pos, state);
	}
}

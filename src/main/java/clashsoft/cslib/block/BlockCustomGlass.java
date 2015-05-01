package clashsoft.cslib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockCustomGlass extends CustomBlock
{
	public boolean	renderSide;
	public boolean	isTransparent;
	
	public BlockCustomGlass(Material material, String[] names)
	{
		super(material, names, null);
		this.setStepSound(soundTypeGlass);
		this.setLightOpacity(0);
	}
	
	public BlockCustomGlass(Material material, String name)
	{
		super(material, new String[] { name }, null);
		this.setStepSound(soundTypeGlass);
		this.setLightOpacity(0);
	}
	
	public BlockCustomGlass setRenderSide(boolean renderSide)
	{
		this.renderSide = renderSide;
		return this;
	}
	
	public BlockCustomGlass setTransparent(boolean isTransparent)
	{
		this.isTransparent = isTransparent;
		return this;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		Block block = world.getBlockState(pos).getBlock();
		
		return !this.renderSide && block == this ? false : super.shouldSideBeRendered(world, pos, side);
	}
}

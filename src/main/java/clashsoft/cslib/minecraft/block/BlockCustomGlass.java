package clashsoft.cslib.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockCustomGlass extends CustomBlock
{
	public boolean	renderSide;
	public boolean	isTransparent;
	
	public BlockCustomGlass(Material material, String[] names, Object icons)
	{
		super(material, names, icons, null);
	}
	
	public BlockCustomGlass(Material material, String name, String iconName)
	{
		super(material, name, iconName, null);
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
	public boolean renderAsNormalBlock()
    {
        return false;
    }

	@Override
	public int getRenderBlockPass()
	{
		return this.isTransparent ? 1 : 0;
	}
	
    @Override
	protected boolean canSilkHarvest()
    {
        return true;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		
		return !this.renderSide && block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}
}

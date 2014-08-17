package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.common.CSLibProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlock2D implements ISimpleBlockRenderingHandler
{
	public static RenderItem	itemRender	= RenderItem.getInstance();
	
	@Override
	public int getRenderId()
	{
		return CSLibProxy.BLOCK2D_RENDER_ID;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		IIcon icon = block.getIcon(1, metadata);
		itemRender.renderIcon(0, 0, icon, 16, 16);
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer)
	{
		return renderer.renderBlockByRenderType(block, x, y, z);
	}
	
	@Override
	public boolean shouldRender3DInInventory(int paramInt)
	{
		return false;
	}
	
}

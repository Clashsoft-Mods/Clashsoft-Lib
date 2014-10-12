package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlock2D extends RenderBlockSimple
{
	public static RenderBlock2D	instance	= new RenderBlock2D(CSLibProxy.BLOCK2D_RENDER_ID);
	public static RenderItem	itemRender	= RenderItem.getInstance();
	
	public RenderBlock2D(int renderID)
	{
		super(renderID);
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		IIcon icon = block.getIcon(1, metadata);
		itemRender.renderIcon(0, 0, icon, 16, 16);
	}
	
	@Override
	public boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer, boolean inventory)
	{
		if (inventory)
		{
			renderer.renderBlockAsItem(block, metadata, 1F);
			return true;
		}
		return renderer.renderBlockByRenderType(block, x, y, z);
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return false;
	}
}

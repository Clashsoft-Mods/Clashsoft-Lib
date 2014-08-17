package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.block.BlockCustomBush;
import clashsoft.cslib.minecraft.common.CSLibProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockBush implements ISimpleBlockRenderingHandler
{
	@Override
	public int getRenderId()
	{
		return CSLibProxy.CUSTOMBUSH_RENDER_ID;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		BlockCustomBush bush = (BlockCustomBush) block;
		
		renderer.renderCrossedSquares(block, x, y, z);
		
		int i1 = world.getBlockMetadata(x, y, z);
		
		if (i1 == bush.fullGrownMetadata)
		{
			renderer.overrideBlockTexture = bush.bushIcon;
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		renderer.overrideBlockTexture = null;
		
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int metadata)
	{
		return false;
	}
}

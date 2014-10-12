package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.block.BlockCustomBush;
import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockBush extends RenderBlockSimple
{
	public static RenderBlockBush	instance	= new RenderBlockBush(CSLibProxy.CUSTOMBUSH_RENDER_ID);
	
	public RenderBlockBush(int renderID)
	{
		super(renderID);
	}
	
	@Override
	public boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer)
	{
		BlockCustomBush bush = (BlockCustomBush) block;
		
		renderer.renderCrossedSquares(block, x, y, z);
		
		if (metadata >= bush.fullGrownMetadata)
		{
			renderer.overrideBlockTexture = bush.bushIcon;
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		renderer.overrideBlockTexture = null;
		
		return true;
	}
}

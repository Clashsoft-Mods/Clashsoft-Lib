package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.block.IBlockRenderPass;
import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockMulti extends RenderBlockSimple
{
	public static RenderBlockMulti	instance	= new RenderBlockMulti(CSLibProxy.MULTI_RENDER_ID);
	
	public static int				renderPass;
	private static int				overrideRenderID;
	
	public RenderBlockMulti(int renderID)
	{
		super(renderID);
	}
	
	@Override
	public boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer, boolean inventory)
	{
		int passes = 1;
		IBlockRenderPass blockPass = null;
		if (block instanceof IBlockRenderPass)
		{
			blockPass = (IBlockRenderPass) block;
			passes = blockPass.getRenderPasses(metadata);
		}
		
		for (int i = 0; i < passes; i++)
		{
			renderPass = i;
			overrideRenderID = blockPass != null ? blockPass.getRenderID(metadata, i) : 0;
			
			if (inventory)
			{
				renderer.renderBlockAsItem(block, metadata, 1F);
			}
			else
			{
				renderer.renderBlockByRenderType(block, x, y, z);
			}
		}
		
		return passes > 0;
	}
	
	@Override
	public int getOverrideRenderID()
	{
		return overrideRenderID;
	}
}

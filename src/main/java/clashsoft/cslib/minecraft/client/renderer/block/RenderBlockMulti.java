package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.block.IBlockRenderPass;
import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;


public class RenderBlockMulti extends RenderBlockSimple
{
	public static RenderBlockMulti instance = new RenderBlockMulti();
	
	public static int renderPass = 0;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		this.rendering = true;
		
		int passes = 1;
		if (block instanceof IBlockRenderPass)
		{
			passes = ((IBlockRenderPass) block).getRenderPasses(metadata);
		}
		
		for (int i = 0; i < passes; i++)
		{
			renderPass = i;
			renderer.renderBlockAsItem(block, metadata, 1F);
		}
		
		this.rendering = false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		this.rendering = true;
		
		int passes = 1;
		if (block instanceof IBlockRenderPass)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			passes = ((IBlockRenderPass) block).getRenderPasses(metadata);
		}
		
		for (int i = 0; i < passes; i++)
		{
			renderPass = i;
			this.renderPass(i, world, x, y, z, block, renderer);
		}
		
		this.rendering = false;
		return false;
	}
	
	protected void renderPass(int pass, IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer)
	{
		renderer.renderBlockByRenderType(block, x, y, z);
	}

	@Override
	protected int getRenderID()
	{
		return CSLibProxy.MULTI_RENDER_ID;
	}
}

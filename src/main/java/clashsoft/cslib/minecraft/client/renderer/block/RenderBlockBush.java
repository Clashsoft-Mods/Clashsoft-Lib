package clashsoft.cslib.minecraft.client.renderer.block;

import clashsoft.cslib.minecraft.block.BlockCustomBush;
import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderBlockBush extends RenderBlockSimple
{
	public static RenderBlockBush	instance	= new RenderBlockBush(CSLibProxy.CUSTOMBUSH_RENDER_ID);
	
	public RenderBlockBush(int renderID)
	{
		super(renderID);
	}
	
	@Override
	public boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer, boolean inventory)
	{
		BlockCustomBush bush = (BlockCustomBush) block;
		Tessellator tessellator = Tessellator.instance;
		
		if (inventory)
		{
			tessellator.startDrawingQuads();
			
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.drawCrossedSquares(bush.stemIcon, -0.5D, -0.5D, -0.5D, 1.0F);
			
			drawStandartBlock(block, metadata, renderer);
			
			tessellator.draw();
		}
		else
		{
			renderer.overrideBlockTexture = bush.stemIcon;
			renderer.renderCrossedSquares(block, x, y, z);
			renderer.overrideBlockTexture = null;
			
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		return true;
	}
}

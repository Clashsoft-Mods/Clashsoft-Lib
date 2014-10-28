package clashsoft.cslib.minecraft.client.renderer.block;

import org.lwjgl.opengl.GL11;

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
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			
			tessellator.startDrawingQuads();
			renderer.drawCrossedSquares(bush.stemIcon, 0D, 0D, 0D, 1.0F);
			renderer.setRenderBounds(bush.bushMinX, bush.bushMinY, bush.bushMinZ, bush.bushMaxX, bush.bushMaxY, bush.bushMaxZ);
			drawStandartBlock(block, metadata, renderer);
			tessellator.draw();
		}
		else
		{
			renderer.overrideBlockTexture = bush.stemIcon;
			renderer.renderCrossedSquares(block, x, y, z);
			renderer.overrideBlockTexture = null;
			
			if (metadata >= bush.fullGrownMetadata)
			{
				renderer.renderStandardBlock(block, x, y, z);
			}
		}
		
		return true;
	}
}

package clashsoft.cslib.minecraft.client.renderer.block;

import org.lwjgl.opengl.GL11;

import clashsoft.cslib.minecraft.world.FakeWorld;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public abstract class RenderBlockSimple implements ISimpleBlockRenderingHandler
{
	public static FakeWorld	fakeWorld	= FakeWorld.instance;
	
	public int				renderID;
	public boolean			rendering;
	
	public RenderBlockSimple(int renderID)
	{
		this.renderID = renderID;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		this.rendering = true;
		
		// Setup Fake World
		FakeWorld world = fakeWorld.block(block, metadata);
		IBlockAccess blockAccess = renderer.blockAccess;
		renderer.blockAccess = world;
		
		// Transform view
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		
		// Draw
		this.renderBlock(FakeWorld.instance, 0, 0, 0, block, metadata, renderer, true);
		
		// Reset Fake World
		fakeWorld.reset();
		renderer.blockAccess = blockAccess;
		
		this.rendering = false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		this.rendering = true;
		this.renderBlock(world, x, y, z, block, world.getBlockMetadata(x, y, z), renderer, false);
		this.rendering = false;
		return true;
	}
	
	public abstract boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer, boolean inventory);
	
	@Override
	public final int getRenderId()
	{
		return this.rendering ? this.getOverrideRenderID() : this.renderID;
	}
	
	public int getOverrideRenderID()
	{
		return 0;
	}
	
	public static void drawStandartBlock(Block block, int metadata, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);
		
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
		
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
		
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metadata));
		
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metadata));
		
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metadata));
		
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metadata));
	}
}

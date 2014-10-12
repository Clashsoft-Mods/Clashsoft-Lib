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
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		// Draw
		Tessellator.instance.startDrawingQuads();
		this.renderBlock(FakeWorld.instance, 0, 0, 0, block, metadata, renderer);
		Tessellator.instance.draw();
		
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
		this.renderBlock(world, x, y, z, block, world.getBlockMetadata(x, y, z), renderer);
		this.rendering = false;
		return true;
	}
	
	public abstract boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, int metadata, RenderBlocks renderer);
	
	@Override
	public final int getRenderId()
	{
		return this.rendering ? this.getOverrideRenderID() : this.renderID;
	}
	
	public int getOverrideRenderID()
	{
		return 0;
	}
}

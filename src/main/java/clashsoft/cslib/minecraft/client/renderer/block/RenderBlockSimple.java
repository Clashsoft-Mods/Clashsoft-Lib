package clashsoft.cslib.minecraft.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

public abstract class RenderBlockSimple implements ISimpleBlockRenderingHandler
{
	protected boolean renderItem;
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		this.renderItem = true;
		renderer.renderBlockAsItem(block, metadata, 1F);
		this.renderItem = false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}
	
	@Override
	public int getRenderId()
	{
		return this.renderItem ? 0 : this.getRenderID();
	}
	
	protected abstract int getRenderID();
}

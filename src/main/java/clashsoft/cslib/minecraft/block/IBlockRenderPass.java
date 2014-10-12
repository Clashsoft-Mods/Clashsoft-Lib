package clashsoft.cslib.minecraft.block;

public interface IBlockRenderPass
{
	public int getRenderPasses(int metadata);
	
	public int getRenderID(int metadata, int pass);
}

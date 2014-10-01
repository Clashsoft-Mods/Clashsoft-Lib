package clashsoft.cslib.minecraft.block.ore;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class OreBaseNetherrack extends OreBase
{
	public IIcon	quartzIcon;
	
	OreBaseNetherrack(String name, float multiplier)
	{
		super(name, multiplier);
	}
	
	@Override
	public String getUnlocalizedName(BlockOre2 ore)
	{
		return ore.getUnlocalizedName();
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icon = iconRegister.registerIcon("netherrack");
		this.quartzIcon = iconRegister.registerIcon("quartz_ore");
	}
	
	@Override
	public boolean isOverlayTexture(BlockOre2 ore)
	{
		return ore == Blocks.quartz_ore;
	}
	
	@Override
	public IIcon getTexture(BlockOre2 ore, int pass)
	{
		if (ore == Blocks.quartz_ore)
		{
			return this.quartzIcon;
		}
		return this.icon;
	}
}

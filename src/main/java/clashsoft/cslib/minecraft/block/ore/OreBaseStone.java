package clashsoft.cslib.minecraft.block.ore;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class OreBaseStone extends OreBase
{
	public IIcon	coalIcon;
	public IIcon	ironIcon;
	public IIcon	goldIcon;
	public IIcon	diamondIcon;
	public IIcon	emeraldIcon;
	public IIcon	redstoneIcon;
	public IIcon	lapisIcon;
	
	OreBaseStone(String name, float multiplier)
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
		this.coalIcon = iconRegister.registerIcon("coal_ore");
		this.ironIcon = iconRegister.registerIcon("iron_ore");
		this.goldIcon = iconRegister.registerIcon("gold_ore");
		this.diamondIcon = iconRegister.registerIcon("diamond_ore");
		this.emeraldIcon = iconRegister.registerIcon("emerald_ore");
		this.redstoneIcon = iconRegister.registerIcon("redstone_ore");
		this.lapisIcon = iconRegister.registerIcon("lapis_ore");
	}
	
	@Override
	public boolean isOverlayTexture(BlockOre2 ore)
	{
		return ore == Blocks.coal_ore || ore == Blocks.iron_ore || ore == Blocks.gold_ore || ore == Blocks.diamond_ore || ore == Blocks.emerald_ore || ore == Blocks.redstone_ore || ore == Blocks.lit_redstone_ore || ore == Blocks.lapis_ore;
	}
	
	@Override
	public IIcon getTexture(BlockOre2 ore, int pass)
	{
		if (ore == Blocks.coal_ore)
		{
			return this.coalIcon;
		}
		else if (ore == Blocks.iron_ore)
		{
			return this.ironIcon;
		}
		else if (ore == Blocks.gold_ore)
		{
			return this.goldIcon;
		}
		else if (ore == Blocks.diamond_ore)
		{
			return this.diamondIcon;
		}
		else if (ore == Blocks.emerald_ore)
		{
			return this.emeraldIcon;
		}
		else if (ore == Blocks.redstone_ore || ore == Blocks.lit_redstone_ore)
		{
			return this.redstoneIcon;
		}
		else if (ore == Blocks.lapis_ore)
		{
			return this.lapisIcon;
		}
		return this.icon;
	}
}
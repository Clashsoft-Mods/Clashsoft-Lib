package clashsoft.cslib.minecraft.block;

import java.util.List;
import java.util.Random;

import clashsoft.cslib.minecraft.client.renderer.block.RenderBlockMulti;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOre2 extends BlockOre implements IBlockRenderPass, ICustomBlock
{
	public OreBase getBase(int metadata)
	{
		return OreBase.oreBases[metadata & 15];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		for (int i = 0; i < OreBase.oreBases.length; i++)
		{
			OreBase base = OreBase.oreBases[i];
			if (base != null)
			{
				base.registerIcons(iconRegister);
			}
		}
	}
	
	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		int i = super.quantityDropped(metadata, fortune, random);
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return (int) (base.amountMultiplier * i);
		}
		return i;
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
	{
		int i = super.getExpDrop(world, metadata, fortune);
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return (int) (base.xpMultiplier * i);
		}
		return i;
	}
	
	@Override
	public int getRenderType()
	{
		return RenderBlockMulti.instance.getRenderId();
	}
	
	@Override
	public int getRenderPasses(int metadata)
	{
		OreBase base = this.getBase(metadata);
		if (base == null || base.isOverlayTexture(this))
		{
			return 1;
		}
		return 2;
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		OreBase base = this.getBase(metadata);
		if (base == null)
		{
			return this.blockIcon;
		}
		if (base.isOverlayTexture(this))
		{
			return base.getTexture(this, -1);
		}
		return base.getTexture(this, RenderBlockMulti.renderPass);
	}
	
	public IIcon getOreIcon(int metadata)
	{
		return this.blockIcon;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		OreBase base = this.getBase(stack.getItemDamage());
		if (base != null)
		{
			return base.getUnlocalizedName(this);
		}
		return this.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		CustomBlock.addInformation(this, stack, list);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < OreBase.oreBases.length; i++)
		{
			OreBase base = OreBase.oreBases[i];
			if (base != null)
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}

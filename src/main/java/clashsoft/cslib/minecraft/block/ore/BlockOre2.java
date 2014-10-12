package clashsoft.cslib.minecraft.block.ore;

import java.util.List;
import java.util.Random;

import clashsoft.cslib.math.CSMath;
import clashsoft.cslib.minecraft.block.CustomBlock;
import clashsoft.cslib.minecraft.block.IBlockRenderPass;
import clashsoft.cslib.minecraft.block.ICustomBlock;
import clashsoft.cslib.minecraft.client.renderer.block.RenderBlockMulti;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOre2 extends BlockOre implements IBlockRenderPass, ICustomBlock
{
	private OreBase[]	bases;
	
	public BlockOre2(String type)
	{
		this.bases = OreBase.getOreBases(type);
	}
	
	public OreBase getBase(int metadata)
	{
		if (this.bases == null || metadata < 0 || metadata >= this.bases.length)
		{
			return null;
		}
		return this.bases[metadata];
	}
	
	@Override
	public int getHarvestLevel(int metadata)
	{
		int i = super.getHarvestLevel(metadata);
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			i += base.harvestLevel;
			return i < 0 ? 0 : i;
		}
		return i;
	}
	
	@Override
	public String getHarvestTool(int metadata)
	{
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return base.harvestTool;
		}
		return super.getHarvestTool(metadata);
	}
	
	@Override
	public boolean isToolEffective(String type, int metadata)
	{
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return type.equals(base.harvestTool);
		}
		return super.isToolEffective(type, metadata);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		float f = super.getBlockHardness(world, x, y, z);
		OreBase base = this.getBase(world.getBlockMetadata(x, y, z));
		if (base != null)
		{
			return base.hardness + f;
		}
		return f;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		float f = super.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
		OreBase base = this.getBase(world.getBlockMetadata(x, y, z));
		if (base != null)
		{
			return base.resistance + f;
		}
		return f;
	}
	
	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		int i = super.quantityDropped(metadata, fortune, random);
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return CSMath.ceiling_float(base.amountMultiplier * i);
		}
		return i;
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
	{
		int i = super.getExpDrop(world, metadata, fortune);
		OreBase base = this.getBase(metadata);
		if (base != null)
		{
			return CSMath.ceiling_float(base.xpMultiplier * i);
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
	public int getRenderID(int metadata, int pass)
	{
		return 0;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);
		
		if (this.bases == null)
		{
			return;
		}
		for (OreBase base : this.bases)
		{
			if (base != null)
			{
				base.registerIcons(iconRegister);
			}
		}
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
		if (this.bases == null)
		{
			return;
		}
		
		for (int i = 0; i < this.bases.length; i++)
		{
			OreBase base = this.bases[i];
			if (base != null)
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}

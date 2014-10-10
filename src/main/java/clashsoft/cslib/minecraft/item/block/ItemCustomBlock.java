package clashsoft.cslib.minecraft.item.block;

import java.util.List;

import clashsoft.cslib.minecraft.block.CustomBlock;
import clashsoft.cslib.minecraft.block.ICustomBlock;
import clashsoft.cslib.minecraft.lang.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * The Class ItemCustomBlock.
 */
public class ItemCustomBlock extends ItemBlock
{
	/** The block. */
	public final Block		theBlock;
	protected ICustomBlock	customBlock;
	
	/**
	 * Instantiates a new item custom block.
	 * 
	 * @param itemID
	 *            the item id
	 * @param block
	 *            the block
	 */
	public ItemCustomBlock(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		
		this.theBlock = block;
		if (block instanceof ICustomBlock)
		{
			this.customBlock = (ICustomBlock) block;
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		Block block = world.getBlock(x, y, z);
		if (block == Blocks.flower_pot)
		{
			TileEntityFlowerPot flowerPot = (TileEntityFlowerPot) world.getTileEntity(x, y, z);
			if (flowerPot.getFlowerPotItem() == null)
			{
				flowerPot.func_145964_a(stack.getItem(), stack.getItemDamage());
				return true;
			}
		}
		
		return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	public boolean canPlaceInFlowerPot()
	{
		return this.field_150939_a.getMaterial() == Material.plants && this.field_150939_a.getRenderType() == 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return this.theBlock.getIcon(2, damage);
	}
	
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return I18n.getString(this.getUnlocalizedName(stack) + ".name");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.customBlock != null ? this.customBlock.getUnlocalizedName(stack) : this.getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return this.theBlock.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (this.customBlock != null)
		{
			this.customBlock.addInformation(stack, player, list);
		}
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		if (this.customBlock != null && this.customBlock instanceof CustomBlock)
		{
			CreativeTabs[] tabs = ((CustomBlock) this.customBlock).tabs;
			if (tabs != null)
			{
				return tabs;
			}
		}
		return super.getCreativeTabs();
	}
}

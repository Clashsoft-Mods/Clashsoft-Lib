package clashsoft.clashsoftapi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemCustomBlock extends ItemBlock
{
	private Block	theBlock;
	
	public ItemCustomBlock(int par1, Block par2Block)
	{
		super(par1);
		this.theBlock = par2Block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1)
	{
		return this.theBlock.getIcon(2, par1);
	}
	
	/**
	 * Returns the metadata of the block which this Item (ItemBlock) can place
	 */
	@Override
	public int getMetadata(int par1)
	{
		return par1;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
	{
		return StatCollector.translateToLocal(theBlock.getUnlocalizedName() + "." + par1ItemStack.getItemDamage());
	}
	
	@Override
	/**
	 * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
	 * different names based on their damage or NBT.
	 */
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return theBlock.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	}
	
	@Override
	/**
	 * Returns the unlocalized name of this item.
	 */
	public String getUnlocalizedName()
	{
		return theBlock.getUnlocalizedName() + ".0";
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack != null)
		{
			// TODO Block Descriptions
		}
	}
	
	/**
	 * Gets a list of tabs that items belonging to this class can display on,
	 * combined properly with getSubItems allows for a single item to span many
	 * sub-items across many tabs.
	 * 
	 * @return A list of all tabs that this item could possibly be one.
	 */
	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		if (theBlock instanceof CustomBlock)
		{
			List<CreativeTabs> list = Arrays.asList(((CustomBlock) theBlock).tabs);
			HashSet set = new HashSet();
			set.addAll(list);
			Object[] ret1 = set.toArray();
			return Arrays.copyOf(ret1, ret1.length, CreativeTabs[].class);
		}
		return super.getCreativeTabs();
	}
}

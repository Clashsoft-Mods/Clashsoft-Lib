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

/**
 * The Class ItemCustomBlock.
 */
public class ItemCustomBlock extends ItemBlock
{
	/** The block. */
	private Block	theBlock;
	
	/**
	 * Instantiates a new item custom block.
	 * 
	 * @param itemID
	 *            the item id
	 * @param block
	 *            the block
	 */
	public ItemCustomBlock(int itemID, Block block)
	{
		super(itemID);
		this.theBlock = block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemBlock#getIconFromDamage(int)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage)
	{
		return this.theBlock.getIcon(2, damage);
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getMetadata(int)
	 */
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getItemStackDisplayName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StatCollector.translateToLocal(theBlock.getUnlocalizedName() + "." + stack.getItemDamage());
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemBlock#getUnlocalizedName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return theBlock.getUnlocalizedName() + "." + stack.getItemDamage();
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemBlock#getUnlocalizedName()
	 */
	@Override
	public String getUnlocalizedName()
	{
		return theBlock.getUnlocalizedName() + ".0";
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#addInformation(net.minecraft.item.ItemStack, net.minecraft.entity.player.EntityPlayer, java.util.List, boolean)
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getCreativeTabs()
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

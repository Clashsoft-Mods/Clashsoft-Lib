package clashsoft.clashsoftapi;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemCustomBlock extends ItemBlock
{	
	private CustomBlock theBlock;

	public ItemCustomBlock(int par1, Block par2Block)
	{
		super(par1);
		this.theBlock = (CustomBlock)par2Block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

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
	public int getMetadata(int par1)
	{
		return par1;
	}

	@Override
	public String getLocalizedName(ItemStack par1ItemStack)
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
			//if (par1ItemStack.getItemDamage() < theBlock.descriptions.length && descriptions[par1ItemStack.getItemDamage()] != null && descriptions[par1ItemStack.getItemDamage()] != "")
			//{
			//String[] lines = descriptions[par1ItemStack.getItemDamage()].split("\n");
			//for (String s : lines)
			//{
			//par3List.add(StatCollector.translateToLocal(s));
			//}
			//}
			//par3List.add("Ore Dictionary Name: " + OreDictionary.getOreName(OreDictionary.getOreID(par1ItemStack)));
		}
	}
}

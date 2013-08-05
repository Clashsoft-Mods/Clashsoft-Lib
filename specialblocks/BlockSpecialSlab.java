package clashsoft.clashsoftapi.specialblocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpecialSlab extends BlockHalfSlab
{
	public int otherSlab;
	
	/** The list of the types of step blocks. */
	public String[] names;
	public String[] topIcons;
	public String[] sideIcons;

	private Icon[] TopIcons;
	private Icon[] SideIcons;

	public BlockSpecialSlab(int par1, String[] names, String[] topIcons, String[] sideIcons, int par2, boolean doubleSlab)
	{
		super(par1, doubleSlab, Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.names = names;
		this.topIcons = topIcons;
		this.TopIcons = new Icon[topIcons.length];
		this.sideIcons = sideIcons;
		this.SideIcons = new Icon[sideIcons.length];
		this.otherSlab = par2;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		if (this.isDoubleSlab && (par2 & 8) != 0)
		{
			par1 = 1;
		}

		return par1 == 1 || par1 == 0 ? TopIcons[par2 & 7] : SideIcons[par2 & 7];
	}

	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		for (int i = 0; i < topIcons.length; i++)
		{
			TopIcons[i] = par1IconRegister.registerIcon(topIcons[i]);
			SideIcons[i] = par1IconRegister.registerIcon(sideIcons[i]);
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return this.blockID;
	}

	/**
	 * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	 * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	 */
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this.blockID, 2, par1 & 7);
	}

	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int j = 0; j < names.length; ++j)
		{
			par3List.add(new ItemStack(par1, 1, j));
		}
	}
	
	/**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return !isDoubleSlab ? this.blockID : this.otherSlab;
    }
    
    public void addNames()
    {	
    	for (int i = 0; i < names.length; i++)
    	{
    		LanguageRegistry.addName(new ItemStack(this, 1, i), names[i]);
    	}
    }

	@Override
	public String getFullSlabName(int i)
	{
		return names[i];
	}
}

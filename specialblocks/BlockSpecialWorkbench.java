package clashsoft.clashsoftapi.specialblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSpecialWorkbench extends Block
{
	public String[]	names;
	public String[]	topIcons;
	public String[]	sideIcons;
	public String[]	side2Icons;
	public String[]	bottomIcons;
	
	public Icon[]	TopIcons;
	public Icon[]	SideIcons;
	public Icon[]	Side2Icons;
	public Icon[]	BottomIcons;
	
	public BlockSpecialWorkbench(int par1, String[] names, String[] topIcons, String[] sideIcons, String[] side2Icons, String[] bottomIcons)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		
		this.names = names;
		this.topIcons = topIcons;
		this.sideIcons = sideIcons;
		this.side2Icons = side2Icons;
		this.bottomIcons = bottomIcons;
		this.TopIcons = new Icon[topIcons.length];
		this.SideIcons = new Icon[sideIcons.length];
		this.Side2Icons = new Icon[side2Icons.length];
		this.BottomIcons = new Icon[bottomIcons.length];
	}
	
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		if (par1 == 1)
			return TopIcons[par2];
		else if (par1 == 0)
			return BottomIcons[par2];
		else if (par1 == 2 || par1 == 4)
			return SideIcons[par2];
		else
			return Side2Icons[par2];
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
			Side2Icons[i] = par1IconRegister.registerIcon(side2Icons[i]);
			BottomIcons[i] = par1IconRegister.registerIcon(bottomIcons[i]);
		}
		
		topIcons = sideIcons = side2Icons = bottomIcons = null;
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			par5EntityPlayer.displayGUIWorkbench(par2, par3, par4);
			return true;
		}
	}
	
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < names.length; i++)
		{
			par3List.add(new ItemStack(this, 1, i));
		}
	}
	
	public void addNames()
	{
		for (int i = 0; i < names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(this, 1, i), names[i]);
		}
	}
}

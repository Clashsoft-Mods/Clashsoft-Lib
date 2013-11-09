package clashsoft.clashsoftapi.block;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCustomWorkbench extends Block implements ICustomBlock
{
	public String[]	names;
	public String[]	topIconNames;
	public String[]	sideIconNames;
	public String[]	side2IconNames;
	public String[]	bottomIconNames;
	
	public Icon[]	topIcons;
	public Icon[]	sideIcons;
	public Icon[]	side2Icons;
	public Icon[]	bottomIcons;
	
	public BlockCustomWorkbench(int par1, String[] names, String[] topIcons, String[] sideIcons, String[] side2Icons, String[] bottomIcons)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		
		this.names = names;
		this.topIconNames = topIcons;
		this.sideIconNames = sideIcons;
		this.side2IconNames = side2Icons;
		this.bottomIconNames = bottomIcons;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		if (par1 == 1)
			return topIcons[par2];
		else if (par1 == 0)
			return bottomIcons[par2];
		else if (par1 == 2 || par1 == 4)
			return sideIcons[par2];
		else
			return side2Icons[par2];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.topIcons = new Icon[topIcons.length];
		this.sideIcons = new Icon[sideIcons.length];
		this.side2Icons = new Icon[side2Icons.length];
		this.bottomIcons = new Icon[bottomIcons.length];
		
		for (int i = 0; i < topIconNames.length; i++)
		{
			topIcons[i] = par1IconRegister.registerIcon(topIconNames[i]);
			sideIcons[i] = par1IconRegister.registerIcon(sideIconNames[i]);
			side2Icons[i] = par1IconRegister.registerIcon(side2IconNames[i]);
			bottomIcons[i] = par1IconRegister.registerIcon(bottomIconNames[i]);
		}
		
		topIconNames = sideIconNames = side2IconNames = bottomIconNames = null;
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
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
	@Override
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

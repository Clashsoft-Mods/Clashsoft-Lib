package clashsoft.cslib.minecraft.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCustomLog extends BlockRotatedPillar implements ICustomBlock
{
	public String[]	names, topIconNames, sideIconNames;
	
	@SideOnly(Side.CLIENT)
	public Icon[]	sideIcons;
	@SideOnly(Side.CLIENT)
	public Icon[]	topIcons;
	
	public BlockCustomLog(int blockID, String[] names, String[] topIcons, String[] sideIcons)
	{
		super(blockID, Material.wood);
		this.setStepSound(soundWoodFootstep);
		
		this.names = names;
		this.topIconNames = topIcons;
		this.sideIconNames = sideIcons;
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}
	
	/**
	 * Called on server worlds only when the block has been replaced by a
	 * different block ID, or the same block with a different metadata value,
	 * but before the new metadata value is set. Args: World, x, y, z, old block
	 * ID, old metadata
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		byte b0 = 4;
		int j1 = b0 + 1;
		
		if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
		{
			for (int k1 = -b0; k1 <= b0; ++k1)
			{
				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
						
						if (Block.blocksList[j2] != null)
						{
							Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
						}
					}
				}
			}
		}
	}
	
	/**
	 * The icon for the side of the block.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	protected Icon getSideIcon(int par1)
	{
		return this.sideIcons[par1];
	}
	
	/**
	 * The icon for the tops and bottoms of the block.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	protected Icon getEndIcon(int par1)
	{
		return this.topIcons[par1];
	}
	
	/**
	 * returns a number between 0 and 3
	 */
	public static int limitToValidMetadata(int par0)
	{
		return par0 & 3;
	}
	
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < this.names.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}
	
	/**
	 * When this method is called, your block should register all the icons it
	 * needs with the given IconRegister. This is the only chance you get to
	 * register icons.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.topIcons = new Icon[topIconNames.length];
		this.sideIcons = new Icon[sideIconNames.length];
		
		for (int i = 0; i < this.topIconNames.length; ++i)
			this.topIcons[i] = par1IconRegister.registerIcon(topIconNames[i]);
		for (int i = 0; i < this.sideIconNames.length; ++i)
			this.sideIcons[i] = par1IconRegister.registerIcon(sideIconNames[i]);
	}
	
	@Override
	public boolean canSustainLeaves(World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public boolean isWood(World world, int x, int y, int z)
	{
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#getDamageValue(net.minecraft.world.World,
	 * int, int, int)
	 */
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) & 3;
	}
	
	@Override
	public void addNames()
	{
		for (int i = 0; i < this.names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(this, 1, i), this.names[i]);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
	}
}

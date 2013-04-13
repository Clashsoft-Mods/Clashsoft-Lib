package clashsoft.clashsoftapi;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class CustomBlock extends Block
{
	private String[] names;
	private String[][] textures;
	private Icon[][] icons;
	private boolean opaque;
	private int renderType;
	private String textureFile;
	private ItemStack[] drops;
	
	/**
	 * @param par1 Block ID
	 * @param par2Material Material
	 * @param par3 Names (Will be used to check how many subblocks should be added)
	 * @param par4 Block Textures (Metadata, Side 6!)
	 * @param par5 isOpaqueCube
	 * @param par6 renderType
	 * @param par7 textureFile
	 * @param par8 ItemStack dropped
	 * @param par9 CreativeTab
	 */
	public CustomBlock(int par1, Material par2Material, String[] par3, String[][] par4, boolean par5, int par6, String par7, ItemStack[] par8, CreativeTabs par9)
	{
		super(par1, par2Material);
		names = par3;
		for (String s : names) { LanguageRegistry.addName(this, s); }
		textures = par4;
		opaque = par5;
		renderType = par6;
		textureFile = par7;
		drops = par8;
		this.setCreativeTab(par9);
		
		GameRegistry.registerBlock(this);
	}
	
	public CustomBlock(int par1, Material par2Material, String par3, String par4, boolean par5, int par6, String par7, ItemStack par8, CreativeTabs par9)
	{
		this(par1, par2Material, new String[] {par3}, new String[][]{{par4, par4, par4, par4, par4, par4}}, par5, par6, par7, new ItemStack[]{par8}, par9);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return opaque;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return renderType == 0;
	}
	
	@Override
	public int getRenderType()
	{
		return renderType;
	}
	
	@Override
	/**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return icons[par2][par1];
    }
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		for (int i = 0; i < textures.length; i++)
		{
			for (int j = 0; j < textures[i].length; j++)
			{
				icons[i][j] = par1IconRegister.registerIcon(textures[i][j]);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems)
	{
		for (int ix = 0; ix < names.length; ix++)
		{
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
	
    /**
     * Metadata and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1. 
     * 
     * @param meta Blocks Metadata
     * @param fortune Current item fortune level
     * @param random Random number generator
     * @return The number of items to drop
     */
    @Override
	public int quantityDropped(int meta, int fortune, Random random)
    {
        return drops[meta].stackSize;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return drops[par1].itemID;
    }
    
    @Override
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return drops[par1].getItemDamage();
    }
}

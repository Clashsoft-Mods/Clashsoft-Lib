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
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CustomBlock extends Block
{
	private String[] names;
	private String[][] textures;
	private Icon[][] icons;
	private boolean opaque;
	private int renderType;
	private ItemStack[] drops;
	private float[] hardnesses;
	private CreativeTabs[] tabs;
	
	/**
	 * @param par1 Block ID
	 * @param par2Material Material
	 * @param par3 Names (Will be used to check how many subblocks should be added)
	 * @param par4 Block Textures (Metadata, Side 6!)
	 * @param par5 isOpaqueCube
	 * @param par6 renderType
	 * @param par7 textureFile
	 * @param par8 CreativeTab
	 */
	public CustomBlock(int par1, Material par2Material, String[] par3, String[][] par4, boolean par5, int par6, CreativeTabs[] par7)
	{
		super(par1, par2Material);
		names = par3;
		textures = par4;
		icons = new Icon[textures.length][6];
		opaque = par5;
		renderType = par6;
		drops = new ItemStack[names.length];
		hardnesses = new float[names.length];
		tabs = par7;
		this.setCreativeTab(par7[0]);
	}
	
	public CustomBlock(int par1, Material par2Material, String[] par3, String[] par4, boolean par5, int par6, CreativeTabs[] par7)
	{
		this(par1, par2Material, par3, metadataToSideArray(par4), par5, par6, par7);
	}
	
	public CustomBlock(int par1, Material par2Material, String par3, String par4, boolean par5, int par6, CreativeTabs par7)
	{
		this(par1, par2Material, new String[] {par3}, new String[][]{{par4, par4, par4, par4, par4, par4}}, par5, par6, new CreativeTabs[]{par7});
	}
	
	public CustomBlock(int par1, Material par2Material, String[] par3, String[] par4, CreativeTabs[] par7)
	{
		this(par1, par2Material, par3, metadataToSideArray(par4), true, 0, par7);
	}
	
	public CustomBlock(int par1, Material par2Material, String par3, String par4, CreativeTabs par7)
	{
		this(par1, par2Material, new String[] {par3}, new String[][]{{par4, par4, par4, par4, par4, par4}}, true, 0, new CreativeTabs[]{par7});
	}
	
	private static String[][] metadataToSideArray(String[] par1)
	{
		String[][] s = new String[par1.length][6];
		for (int i = 0; i < par1.length; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				s[i][j] = par1[i];
			}
		}
		return s;
	}
	
	public CustomBlock setHardnesses(float[] hardnesses)
	{
		this.hardnesses = hardnesses;
		return this;
	}
	
	public CustomBlock setDrops(ItemStack[] drops)
	{
		this.drops = drops;
		return this;
	}
	
	public CustomBlock setDrops(int metadata, ItemStack drop)
	{
		drops[metadata] = drop;
		return this;
	}
	
	public String[] getNames()
	{
		return names;
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
    public Icon getIcon(int par1, int par2)
    {
        return icons[par2][par1];
    }
	
	/**
     * Returns the block hardness at a location. Args: world, x, y, z
     */
    public float getBlockHardness(World par1World, int par2, int par3, int par4)
    {
        if (par1World.getBlockMetadata(par2, par3, par4) < hardnesses.length)
        {
        	return hardnesses[par1World.getBlockMetadata(par2, par3, par4)];
        }
        return this.blockHardness;
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
		for (int i = 0; i < names.length; i++)
		{
			if (i < tabs.length)
			{
				if (tab == tabs[i])
				{
					subItems.add(new ItemStack(this, 1, i));
				}
			}
			else
			{
				if (tab == tabs[tabs.length - 1])
				{
					subItems.add(new ItemStack(this, 1, i));
				}
			}
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
    	if (drops[meta] != null)
    		return drops[meta].stackSize;
    	return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
    	if (drops[par1] != null)
    		return drops[par1].itemID;
    	return this.blockID;
    }
    
    @Override
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
    	if (drops[par1] != null)
    		return drops[par1].getItemDamage();
    	return par1;
    }
    
    public void addNames()
    {	
    	for (int i = 0; i < names.length; i++)
    	{
    		System.out.println(this.getUnlocalizedName() + "." + i);
    		LanguageRegistry.addName(new ItemStack(
    				this, 1, 
    				i), 
    				names[i]);
    	}
    }
}

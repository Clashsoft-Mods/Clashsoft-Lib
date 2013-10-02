package clashsoft.clashsoftapi;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * The Class CustomBlock.
 */
public class CustomBlock extends Block
{
	
	/** The names. */
	private String[]			names;
	
	/** The textures. */
	private String[][]			textures;
	
	/** The icons. */
	private Icon[][]			icons;
	
	/** The opaque. */
	private boolean				opaque;
	
	/** The render type. */
	private int					renderType;
	
	/** The drops. */
	private ItemStack[]			drops;
	
	/** The hardnesses. */
	private float[]				hardnesses;
	
	/** The tabs. */
	protected CreativeTabs[]	tabs;
	
	/** The disabled. */
	private boolean[]			disabled;
	
	/**
	 * Instantiates a new custom block.
	 * 
	 * @param blockID
	 *            the block id
	 * @param material
	 *            the material
	 * @param displayNames
	 *            the display names
	 * @param iconNames
	 *            the icon names
	 * @param opaque
	 *            the opaque
	 * @param renderType
	 *            the render type
	 * @param creativeTabs
	 *            the creative tabs
	 */
	public CustomBlock(int blockID, Material material, String[] displayNames, String[][] iconNames, boolean opaque, int renderType, CreativeTabs[] creativeTabs)
	{
		super(blockID, material);
		this.names = displayNames;
		this.textures = iconNames;
		this.icons = new Icon[this.textures.length][6];
		this.opaque = opaque;
		this.renderType = renderType;
		this.drops = new ItemStack[this.names.length];
		this.hardnesses = new float[this.names.length];
		this.disabled = new boolean[this.names.length];
		for (int i = 0; i < this.disabled.length; i++)
		{
			this.disabled[i] = displayNames[i] == "" || displayNames[i].contains("%&") || (iconNames[i][0] == "" || iconNames[i][1] == "" || iconNames[i][2] == "" || iconNames[i][3] == "" || iconNames[i][4] == "" || iconNames[i][5] == "");
		}
		this.tabs = creativeTabs;
		this.setCreativeTab(creativeTabs[0]);
	}
	
	/**
	 * Instantiates a new custom block.
	 * 
	 * @param blockID
	 *            the block id
	 * @param material
	 *            the material
	 * @param displayNames
	 *            the display names
	 * @param iconNames
	 *            the icon names
	 * @param opaque
	 *            the opaque
	 * @param renderType
	 *            the render type
	 * @param creativeTabs
	 *            the creative tabs
	 */
	public CustomBlock(int blockID, Material material, String[] displayNames, String[] iconNames, boolean opaque, int renderType, CreativeTabs[] creativeTabs)
	{
		this(blockID, material, displayNames, iconMetadataToSideArray(iconNames), opaque, renderType, creativeTabs);
	}
	
	/**
	 * Instantiates a new custom block.
	 * 
	 * @param blockID
	 *            the block id
	 * @param material
	 *            the material
	 * @param displayName
	 *            the display name
	 * @param iconNames
	 *            the icon names
	 * @param opaque
	 *            the opaque
	 * @param renderType
	 *            the render type
	 * @param creativeTabs
	 *            the creative tabs
	 */
	public CustomBlock(int blockID, Material material, String displayName, String iconNames, boolean opaque, int renderType, CreativeTabs creativeTabs)
	{
		this(blockID, material, new String[] { displayName }, new String[][] { { iconNames, iconNames, iconNames, iconNames, iconNames, iconNames } }, opaque, renderType, new CreativeTabs[] { creativeTabs });
	}
	
	/**
	 * Instantiates a new custom block.
	 * 
	 * @param blockID
	 *            the block id
	 * @param material
	 *            the material
	 * @param displayNames
	 *            the display names
	 * @param iconNames
	 *            the icon names
	 * @param creativeTabs
	 *            the creative tabs
	 */
	public CustomBlock(int blockID, Material material, String[] displayNames, String[] iconNames, CreativeTabs[] creativeTabs)
	{
		this(blockID, material, displayNames, iconMetadataToSideArray(iconNames), true, 0, creativeTabs);
	}
	
	/**
	 * Instantiates a new custom block.
	 * 
	 * @param blockID
	 *            the block id
	 * @param material
	 *            the material
	 * @param displayName
	 *            the display name
	 * @param iconName
	 *            the icon name
	 * @param creativeTab
	 *            the creative tab
	 */
	public CustomBlock(int blockID, Material material, String displayName, String iconName, CreativeTabs creativeTab)
	{
		this(blockID, material, new String[] { displayName }, new String[][] { { iconName, iconName, iconName, iconName, iconName, iconName } }, true, 0, new CreativeTabs[] { creativeTab });
	}
	
	/**
	 * Icon metadata to side array.
	 * 
	 * @param metadataArray
	 *            the metadata array
	 * @return the string[][]
	 */
	private static String[][] iconMetadataToSideArray(String[] metadataArray)
	{
		String[][] s = new String[metadataArray.length][6];
		for (int i = 0; i < metadataArray.length; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				s[i][j] = metadataArray[i];
			}
		}
		return s;
	}
	
	/**
	 * Disable metadata.
	 * 
	 * @param metadata
	 *            the metadata
	 * @return the custom block
	 */
	public CustomBlock disableMetadata(int metadata)
	{
		this.disabled[metadata] = true;
		return this;
	}
	
	/**
	 * Sets the hardness.
	 * 
	 * @param metadata
	 *            the metadata
	 * @param hardness
	 *            the hardness
	 * @return the custom block
	 */
	public CustomBlock setHardness(int metadata, float hardness)
	{
		this.hardnesses[metadata] = hardness;
		return this;
	}
	
	/**
	 * Sets the hardnesses.
	 * 
	 * @param hardnessArray
	 *            the hardness array
	 * @return the custom block
	 */
	public CustomBlock setHardnesses(float[] hardnessArray)
	{
		this.hardnesses = hardnessArray;
		return this;
	}
	
	/**
	 * Sets the drops.
	 * 
	 * @param drops
	 *            the drops
	 * @return the custom block
	 */
	public CustomBlock setDrops(ItemStack[] drops)
	{
		this.drops = drops;
		return this;
	}
	
	/**
	 * Sets the drops.
	 * 
	 * @param metadata
	 *            the metadata
	 * @param drop
	 *            the drop
	 * @return the custom block
	 */
	public CustomBlock setDrops(int metadata, ItemStack drop)
	{
		this.drops[metadata] = drop;
		return this;
	}
	
	/**
	 * Gets the names.
	 * 
	 * @return the names
	 */
	public String[] getNames()
	{
		return this.names;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#isOpaqueCube()
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return this.opaque;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#renderAsNormalBlock()
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return this.renderType == 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#getRenderType()
	 */
	@Override
	public int getRenderType()
	{
		return this.renderType;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#getIcon(int, int)
	 */
	@Override
	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public Icon getIcon(int par1, int par2)
	{
		if (par2 < this.icons.length && par1 < this.icons[par2].length)
			return this.icons[par2][par1];
		return this.icons[0][0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * net.minecraft.block.Block#getBlockHardness(net.minecraft.world.World,
	 * int, int, int)
	 */
	@Override
	/**
	 * Returns the block hardness at a location. Args: world, x, y, z
	 */
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlockMetadata(par2, par3, par4) < this.hardnesses.length && this.hardnesses[par1World.getBlockMetadata(par2, par3, par4)] > 0)
		{
			return this.hardnesses[par1World.getBlockMetadata(par2, par3, par4)];
		}
		return this.blockHardness;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * net.minecraft.block.Block#registerIcons(net.minecraft.client.renderer
	 * .texture.IconRegister)
	 */
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		for (int i = 0; i < this.textures.length; i++)
		{
			for (int j = 0; j < this.textures[i].length; j++)
			{
				if (!this.textures[i][j].contains("%&"))
					this.icons[i][j] = par1IconRegister.registerIcon(this.textures[i][j]);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#getSubBlocks(int,
	 * net.minecraft.creativetab.CreativeTabs, java.util.List)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			if (i < this.tabs.length)
			{
				if (tab == this.tabs[i] && !this.disabled[i])
				{
					subItems.add(new ItemStack(this, 1, i));
				}
			}
			else
			{
				if (tab == this.tabs[this.tabs.length - 1] && !this.disabled[i])
				{
					subItems.add(new ItemStack(this, 1, i));
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#quantityDropped(int, int, java.util.Random)
	 */
	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		if (this.drops[meta] != null)
			return this.drops[meta].stackSize;
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#idDropped(int, java.util.Random, int)
	 */
	@Override
	public int idDropped(int metadata, Random random, int fortune)
	{
		if (this.drops[metadata] != null)
			return this.drops[metadata].itemID;
		return this.blockID;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#damageDropped(int)
	 */
	@Override
	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	public int damageDropped(int metadata)
	{
		if (this.drops[metadata] != null)
			return this.drops[metadata].getItemDamage();
		return metadata;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.minecraft.block.Block#getDamageValue(net.minecraft.world.World,
	 * int, int, int)
	 */
	@Override
	/**
	 * Get the block's damage value (for use with pick block).
	 */
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	/**
	 * Adds the names.
	 */
	public void addNames()
	{
		for (int i = 0; i < this.names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(this, 1, i), this.names[i]);
		}
	}
}

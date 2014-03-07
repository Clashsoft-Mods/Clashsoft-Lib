package clashsoft.cslib.minecraft.block;

import java.util.List;
import java.util.Random;

import clashsoft.cslib.minecraft.lang.I18n;
import clashsoft.cslib.util.CSString;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * The Class CustomBlock.
 */
public class CustomBlock extends Block implements ICustomBlock
{
	public String[]			names;
	public String[]			iconNames;
	public String[][]		iconNames2D;
	public IIcon[]			icons;
	public IIcon[][]		icons2D;
	
	public boolean			opaque;
	public int				renderType;
	
	public ItemStack[]		drops;
	public float[]			hardnesses;
	public CreativeTabs[]	tabs;
	public boolean[]		enabled;
	
	public static String[] applyDomain(String[] names, String domain)
	{
		return CSString.concatAll(names, domain + ":", "");
	}
	
	protected CustomBlock(Material material, String[] names, CreativeTabs[] creativeTabs)
	{
		super(material);
		this.setBlockName(names[0]);
		
		this.names = names;
		
		this.drops = new ItemStack[this.names.length];
		this.hardnesses = new float[this.names.length];
		this.enabled = new boolean[this.names.length];
		
		for (int i = 0; i < this.enabled.length; i++)
		{
			this.enabled[i] = !(names[i] == null || names[i].isEmpty() || names[i].startsWith("%&"));
		}
		
		this.tabs = creativeTabs;
		if (this.tabs != null)
		{
			this.setCreativeTab(creativeTabs[0]);
		}
	}
	
	public CustomBlock(Material material, String[] names, String[][] iconNames, CreativeTabs[] tabs)
	{
		this(material, names, tabs);
		this.iconNames2D = iconNames;
	}
	
	public CustomBlock(Material material, String[] names, String[] iconNames, CreativeTabs[] tabs)
	{
		this(material, names, tabs);
		this.iconNames = iconNames;
	}
	
	public CustomBlock(Material material, String[] names, String domain, CreativeTabs[] tabs)
	{
		this(material, names, applyDomain(names, domain), tabs);
	}
	
	public CustomBlock(Material material, String name, String iconName, CreativeTabs tab)
	{
		this(material, new String[] { name }, new String[][] { {
				iconName,
				iconName,
				iconName,
				iconName,
				iconName,
				iconName } }, new CreativeTabs[] { tab });
	}
	
	/**
	 * Sets the given metadata to be shown in the creative inventory or not.
	 * 
	 * @param metadata
	 *            the metadata
	 * @return the custom block
	 */
	public CustomBlock setEnabled(int metadata, boolean flag)
	{
		this.enabled[metadata] = flag;
		return this;
	}
	
	/**
	 * Sets the hardness.
	 * 
	 * @param hardness
	 *            the hardness
	 * @return the custom block
	 */
	@Override
	public CustomBlock setHardness(float hardness)
	{
		return this.setHardness(0, hardness);
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
	public CustomBlock setHardnesses(float... hardnessArray)
	{
		this.hardnesses = hardnessArray;
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
	 * Sets the drops.
	 * 
	 * @param drops
	 *            the drops
	 * @return the custom block
	 */
	public CustomBlock setDrops(ItemStack... drops)
	{
		this.drops = drops;
		return this;
	}
	
	public CustomBlock setCreativeTab(int metadata, CreativeTabs tab)
	{
		this.tabs[metadata] = tab;
		return this;
	}
	
	public CustomBlock setCreativeTabs(CreativeTabs... tabs)
	{
		this.tabs = tabs;
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
	
	@Override
	public boolean isOpaqueCube()
	{
		return this.opaque;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return this.renderType == 0;
	}
	
	@Override
	public int getRenderType()
	{
		return this.renderType;
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		if (this.icons2D != null)
		{
			try
			{
				return this.icons2D[metadata][side];
			}
			catch (ArrayIndexOutOfBoundsException ex)
			{
				return this.icons2D[0][side];
			}
		}
		else
		{
			try
			{
				return this.icons[metadata];
			}
			catch (ArrayIndexOutOfBoundsException ex)
			{
				return this.icons[0];
			}
		}
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) < this.hardnesses.length && this.hardnesses[world.getBlockMetadata(x, y, z)] > 0)
		{
			return this.hardnesses[world.getBlockMetadata(x, y, z)];
		}
		return this.blockHardness;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		if (this.iconNames2D != null)
		{
			int len = this.iconNames2D.length;
			this.icons2D = new IIcon[len][6];
			
			for (int i = 0; i < len; i++)
			{
				String[] sides = this.iconNames2D[i];
				for (int j = 0; j < sides.length; j++)
				{
					this.icons2D[i][j] = iconRegister.registerIcon(sides[j]);
				}
			}
		}
		else
		{
			int len = this.iconNames.length;
			this.icons = new IIcon[len];
			
			for (int i = 0; i<  len; i++)
			{
				this.icons[i] = iconRegister.registerIcon(this.iconNames[i]);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			if (this.enabled[i])
			{
				if (this.tabs == null)
				{
					if (creativeTab == super.getCreativeTabToDisplayOn())
					{
						list.add(new ItemStack(this, 1, i));
					}
				}
				else if (i < this.tabs.length && creativeTab == this.tabs[i])
				{
					list.add(new ItemStack(this, 1, i));
				}
				else if (creativeTab == this.tabs[this.tabs.length - 1])
				{
					list.add(new ItemStack(this, 1, i));
				}
			}
		}
	}
	
	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		if (this.drops[metadata] != null)
		{
			return this.drops[metadata].stackSize;
		}
		return 1;
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		if (this.drops[metadata] != null)
		{
			return this.drops[metadata].getItem();
		}
		return super.getItemDropped(metadata, random, fortune);
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		if (this.drops[metadata] != null)
		{
			return this.drops[metadata].getItemDamage();
		}
		return metadata;
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int metadata = stack.getItemDamage();
		if (metadata < this.names.length)
		{
			return this.getUnlocalizedName() + "." + this.names[metadata];
		}
		return this.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		String key = this.getUnlocalizedName(stack) + ".desc";
		String desc = I18n.getString(key);
		if (desc != key)
		{
			list.addAll(CSString.lineList(desc));
		}
	}
}

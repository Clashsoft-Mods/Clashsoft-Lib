package clashsoft.cslib.minecraft.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import clashsoft.cslib.minecraft.client.icon.IIconSupplier;
import clashsoft.cslib.minecraft.client.icon.IconSupplier;
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
	public static final String[]	DEFAULT_NAMES	= { null };
	
	public String[]					names;
	public IIconSupplier			iconSupplier;
	
	public boolean					opaque;
	public int						renderType;
	
	public ItemStack[]				drops;
	public float[]					hardnesses;
	public CreativeTabs[]			tabs;
	public boolean[]				enabled;
	
	protected CustomBlock(Material material, String[] names, CreativeTabs[] tabs)
	{
		super(material);
		this.setBlockName(names[0]);
		
		this.names = names;
		this.opaque = true;
		this.lightOpacity = 255;
		
		this.drops = new ItemStack[this.names.length];
		this.hardnesses = new float[this.names.length];
		this.enabled = new boolean[this.names.length];
		Arrays.fill(this.enabled, true);
		
		this.tabs = tabs;
		if (this.tabs != null)
		{
			this.setCreativeTab(tabs[0]);
		}
	}
	
	public CustomBlock(Material material, String[] names, Object icons, CreativeTabs[] tabs)
	{
		this(material, names, tabs);
		this.iconSupplier = IconSupplier.create(icons);
	}
	
	public CustomBlock(Material material, String name, String iconName, CreativeTabs tab)
	{
		this(material, new String[] { name }, new IconSupplier(iconName), null);
		this.setCreativeTab(tab);
	}
	
	// SETTERS SECTION
	
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
	
	public CreativeTabs getCreativeTab(int metadata)
	{
		if (this.tabs == null)
		{
			return this.getCreativeTabToDisplayOn();
		}
		return this.tabs[metadata % this.tabs.length];
	}
	
	// RENDER SECTION
	
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
	
	// ICON SECTION
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return this.iconSupplier.getIcon(metadata, side);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.iconSupplier.registerIcons(iconRegister);
	}
	
	// NAME SECTION
	
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
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName(this, stack, this.names);
	}
	
	/**
	 * Ensures shared access for all class implementing {@link ICustomBlock}.
	 * This is usually inlined, so we have a win-win.
	 * 
	 * @param block
	 *            the ICustomBlock instance
	 * @param stack
	 *            the stack
	 * @param names
	 *            the names
	 * @return the unlocalized name
	 */
	public static String getUnlocalizedName(Block block, ItemStack stack, String[] names)
	{
		int metadata = stack.getItemDamage();
		String name = names[metadata % names.length];
		if (name != null)
		{
			return block.getUnlocalizedName() + "." + name;
		}
		return block.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		addInformation(this, stack, list);
	}
	
	public static void addInformation(ICustomBlock block, ItemStack stack, List<String> list)
	{
		String key = block.getUnlocalizedName(stack) + ".desc";
		String desc = I18n.getString(key);
		if (desc != key)
		{
			String[] lines = CSString.lineArray(desc);
			for (String s : lines)
			{
				list.add(s);
			}
		}
	}
	
	// OTHER PROPERTIES
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		if (metadata < this.hardnesses.length)
		{
			return this.hardnesses[metadata];
		}
		return this.blockHardness;
	}
	
	// DROP SECTION
	
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
	
	// SUB BLOCKS
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			if (this.enabled[i] && tab == this.getCreativeTab(i))
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}

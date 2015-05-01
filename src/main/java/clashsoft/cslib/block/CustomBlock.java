package clashsoft.cslib.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import clashsoft.cslib.util.I18n;
import dyvil.string.StringUtils;

/**
 * The Class
 */
public class CustomBlock extends Block implements ICustomBlock
{
	public static final String[]		DEFAULT_NAMES	= { null };
	public static final PropertyInteger	typeProperty	= PropertyInteger.create("type", 0, 16);
	
	public String[]						names;
	
	public boolean						opaque;
	
	public ItemStack[]					drops;
	public float[]						hardnesses;
	public CreativeTabs[]				tabs;
	public boolean[]					enabled;
	
	protected CustomBlock(Material material, String[] names, CreativeTabs[] tabs)
	{
		super(material);
		this.setUnlocalizedName(names[0]);
		
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
			return this.getCreativeTabToDisplayOn();
		return this.tabs[metadata % this.tabs.length];
	}
	
	// RENDER SECTION
	
	@Override
	public boolean isOpaqueCube()
	{
		return this.opaque;
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
			return block.getUnlocalizedName() + "." + name;
		return block.getUnlocalizedName();
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, typeProperty);
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
			String[] lines = StringUtils.lines(desc);
			for (String s : lines)
			{
				list.add(s);
			}
		}
	}
	
	// OTHER PROPERTIES
	
	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		int metadata = (int) world.getBlockState(pos).getValue(typeProperty);
		if (metadata < this.hardnesses.length)
			return this.hardnesses[metadata];
		return this.blockHardness;
	}
	
	// DROP SECTION
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		int metadata = (int) state.getValue(typeProperty);
		if (this.drops[metadata] != null)
			return this.drops[metadata].stackSize;
		return 1;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		int metadata = (int) state.getValue(typeProperty);
		if (this.drops[metadata] != null)
			return this.drops[metadata].getItem();
		return super.getItemDropped(state, random, fortune);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		int metadata = (int) state.getValue(typeProperty);
		if (this.drops[metadata] != null)
			return this.drops[metadata].getItemDamage();
		return metadata;
	}
	
	// SUB BLOCKS
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			if (this.enabled[i] && (tab == null || tab == this.getCreativeTab(i)))
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}

package clashsoft.clashsoftapi;

import java.util.Arrays;
import java.util.List;

import clashsoft.clashsoftapi.util.IItemMetadataList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

/**
 * The Class CustomItem.
 */
public class CustomItem extends Item
{
	
	/** The descriptions. */
	private String[]	displayNames, iconNames, descriptions;
	
	/** The icons. */
	private Icon[]		icons;
	
	/** The disabled. */
	private boolean[]	disabled;
	
	/** The display list. */
	protected List<? extends IItemMetadataList>	metalist, displayList;
	
	/**
	 * Instantiates a new custom item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param metalist
	 *            the metalist
	 * @param displaylist
	 *            the displaylist
	 */
	public CustomItem(int itemID, List<? extends IItemMetadataList> metalist, List<? extends IItemMetadataList> displaylist)
	{
		super(itemID);
		this.metalist = metalist;
		this.displayList = metalist;
	}
	
	/**
	 * Instantiates a new custom item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param displayNames
	 *            the display names
	 * @param iconNames
	 *            the icon names
	 * @param descriptions
	 *            the descriptions
	 */
	public CustomItem(int itemID, String[] displayNames, String[] iconNames, String[] descriptions)
	{
		super(itemID);
		this.displayNames = displayNames;
		this.iconNames = iconNames;
		this.icons = new Icon[this.iconNames.length];
		this.disabled = new boolean[this.displayNames.length];
		for (int i = 0; i < this.disabled.length; i++)
		{
			this.disabled[i] = iconNames[i] == "" || displayNames[i] == "" || displayNames[i].contains("%&");
		}
		this.setHasSubtypes(displayNames.length > 1);
		this.descriptions = descriptions;
	}
	
	/**
	 * Instantiates a new custom item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param displayNames
	 *            the display names
	 * @param iconNames
	 *            the icon names
	 */
	public CustomItem(int itemID, String[] displayNames, String[] iconNames)
	{
		this(itemID, displayNames, iconNames, new String[] { "" });
	}
	
	/**
	 * Instantiates a new custom item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param displayName
	 *            the display name
	 * @param iconName
	 *            the icon name
	 * @param description
	 *            the description
	 */
	public CustomItem(int itemID, String displayName, String iconName, String description)
	{
		this(itemID, new String[] { displayName }, new String[] { iconName }, new String[] { description });
	}
	
	/**
	 * Instantiates a new custom item.
	 * 
	 * @param itemID
	 *            the item id
	 * @param displayName
	 *            the display name
	 * @param iconName
	 *            the icon name
	 */
	public CustomItem(int itemID, String displayName, String iconName)
	{
		this(itemID, new String[] { displayName }, new String[] { iconName });
	}
	
	/**
	 * Checks for item metadata list.
	 * 
	 * @return true, if successful
	 */
	public boolean hasItemMetadataList()
	{
		return this.metalist != null && this.displayList != null;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getItemDisplayName(net.minecraft.item.ItemStack)
	 */
	@Override
	public String getItemDisplayName(ItemStack stack)
	{
		String ret = this.hasItemMetadataList() ? this.metalist.get(stack.getItemDamage()).getName() : this.displayNames[stack.getItemDamage()];
		return StatCollector.translateToLocal(ret.replace("%&", ""));
	}
	
	/**
	 * Disable metadata.
	 * 
	 * @param metadata
	 *            the metadata
	 * @return the custom item
	 */
	public CustomItem disableMetadata(int... metadata)
	{
		if (metadata != null)
			for (int i : metadata)
			{
				this.disabled[i] = true;
			}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getIconFromDamage(int)
	 */
	@Override
	public Icon getIconFromDamage(int damage)
	{
		return this.icons[damage];
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#registerIcons(net.minecraft.client.renderer.texture.IconRegister)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		if (this.hasItemMetadataList())
			for (int i = 0; i < this.metalist.size(); i++)
			{
				String s = this.metalist.get(i).getIconName();
				if (!s.contains("%&"))
					this.icons[i] = iconRegister.registerIcon(s);
			}
		else
			for (int i = 0; i < this.iconNames.length; i++)
			{
				if (this.iconNames[i] != null && !this.iconNames[i].contains("%&"))
				{
					this.icons[i] = iconRegister.registerIcon(this.iconNames[i]);
				}
			}
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#addInformation(net.minecraft.item.ItemStack, net.minecraft.entity.player.EntityPlayer, java.util.List, boolean)
	 */
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (stack != null)
		{
			if (this.hasItemMetadataList())
				list.addAll(this.metalist.get(stack.getItemDamage()).getDescription());
			else if (stack.getItemDamage() < this.descriptions.length)
			{
				String s = this.descriptions[stack.getItemDamage()];
				if (s == "")
					return;
				list.addAll(Arrays.asList(s.split("\n")));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#getSubItems(int, net.minecraft.creativetab.CreativeTabs, java.util.List)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int itemID, CreativeTabs creativeTab, List list)
	{
		if (this.hasItemMetadataList())
			for (IItemMetadataList item : this.displayList)
				list.add(item.asStack());
		else
			for (int i = 0; i < this.displayNames.length; i++)
			{
				if (!this.disabled[i])
					list.add(new ItemStack(this, 1, i));
			}
	}
}

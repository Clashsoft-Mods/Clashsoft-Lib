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

public class CustomItem extends Item
{
	private String[]	names, textures, descriptions;
	private Icon[]		icons;
	
	private boolean[]	disabled;
	
	protected List<? extends IItemMetadataList>	list, displayList;
	
	public CustomItem(int par1, List<? extends IItemMetadataList> list, List<? extends IItemMetadataList> displaylist)
	{
		super(par1);
		this.list = list;
		this.displayList = list;
	}
	
	public CustomItem(int par1, String[] par2, String[] par3, String[] par4)
	{
		super(par1);
		names = par2;
		textures = par3;
		icons = new Icon[textures.length];
		disabled = new boolean[names.length];
		for (int i = 0; i < disabled.length; i++)
		{
			disabled[i] = par3[i] == "" || par2[i] == "" || par2[i].contains("%&");
		}
		this.setHasSubtypes(par2.length > 1);
		descriptions = par4;
	}
	
	public CustomItem(int par1, String[] par2, String[] par3)
	{
		this(par1, par2, par3, new String[] { "" });
	}
	
	public CustomItem(int par1, String par2, String par3, String par4)
	{
		this(par1, new String[] { par2 }, new String[] { par3 }, new String[] { par4 });
	}
	
	public CustomItem(int par1, String par2, String par3)
	{
		this(par1, new String[] { par2 }, new String[] { par3 });
	}
	
	public boolean hasItemMetadataList()
	{
		return list != null && displayList != null;
	}
	
	@Override
	public String getItemDisplayName(ItemStack is)
	{
		String ret = hasItemMetadataList() ? list.get(is.getItemDamage()).getName() : names[is.getItemDamage()];
		return StatCollector.translateToLocal(ret.replace("%&", ""));
	}
	
	public CustomItem disableMetadata(int... metadata)
	{
		if (metadata != null)
			for (int i : metadata)
			{
				disabled[i] = true;
			}
		return this;
	}
	
	@Override
	public Icon getIconFromDamage(int i)
	{
		return icons[i];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		if (hasItemMetadataList())
			for (int i = 0; i < list.size(); i++)
			{
				String s = list.get(i).getIconName();
				if (!s.contains("%&"))
					this.icons[i] = par1IconRegister.registerIcon(s);
			}
		else
			for (int i = 0; i < textures.length; i++)
			{
				if (textures[i] != null && !textures[i].contains("%&"))
				{
					this.icons[i] = par1IconRegister.registerIcon(textures[i]);
				}
			}
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack != null)
		{
			if (hasItemMetadataList())
				par3List.addAll(this.list.get(par1ItemStack.getItemDamage()).getDescription());
			else if (par1ItemStack.getItemDamage() < descriptions.length)
			{
				String s = this.descriptions[par1ItemStack.getItemDamage()];
				if (s == "")
					return;
				par3List.addAll(Arrays.asList(s.split("\n")));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if (hasItemMetadataList())
			for (IItemMetadataList item : displayList)
				par3List.add(item.asStack());
		else
			for (int i = 0; i < names.length; i++)
			{
				if (!disabled[i])
					par3List.add(new ItemStack(par1, 1, i));
			}
	}
}

package clashsoft.clashsoftapi;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class CustomItem extends Item
{
	private String[] names;
	private String[] textures;
	private String[] descriptions;
	private Icon[] icons;
	
	private boolean[] disabled;
	
	public CustomItem(int par1, String[] par2, String[] par3, String[] par4)
	{
		super(par1);
		names = par2;
		textures = par3;
		icons = new Icon[textures.length];
		disabled = new boolean[names.length];
		for (int i = 0; i < disabled.length; i++) { disabled[i] = par3[i] == "" || par2[i] == "" || par2[i].contains("%&"); }
		this.setHasSubtypes(par2.length > 1);
		descriptions = par4;
	}
	
	public CustomItem(int par1, String[] par2, String[] par3)
	{
		this(par1, par2, par3, new String[]{});
	}
	
	@Override
	public String getItemDisplayName(ItemStack is)
	{
		return StatCollector.translateToLocal(names[is.getItemDamage()].replace("%&", ""));
	}
	
	public CustomItem disableMetadata(int... metadata)
	{
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
        for (int i = 0; i < textures.length; i++)
        {
        	if (textures[i] != null && !textures[i].contains("%&"))
        	{
        		this.
        		icons[i] = par1IconRegister.registerIcon(textures[i]);
        	}
        }
    }
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if (par1ItemStack != null)
		{
			if (par1ItemStack.getItemDamage() < descriptions.length && descriptions[par1ItemStack.getItemDamage()] != null && descriptions[par1ItemStack.getItemDamage()] != "")
			{
				String[] lines = descriptions[par1ItemStack.getItemDamage()].split("\n");
				for (String s : lines)
				{
					par3List.add(StatCollector.translateToLocal(s));
				}
			}
			//par3List.add("Ore Dictionary Name: " + OreDictionary.getOreName(OreDictionary.getOreID(par1ItemStack)));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < names.length; i++)
        {
        	if (!disabled[i])
        		par3List.add(new ItemStack(par1, 1, i));
        }
    }
}

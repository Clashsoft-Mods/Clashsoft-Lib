package clashsoft.clashsoftapi;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class CustomItem extends Item
{
	private String[] names;
	private String[] textures;
	private Icon[] icons;
	
	public CustomItem(int par1, String[] par2, String[] par3)
	{
		super(par1);
		names = par2;
		textures = par3;
		icons = new Icon[textures.length];
	}
	
	@Override
	public String getItemDisplayName(ItemStack is)
	{
		return StatCollector.translateToLocal("item." + names[is.getItemDamage()] + ".name");
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
        	if (textures[i] != null)
        	{
        		this.
        		icons[i] = par1IconRegister.registerIcon(textures[i]);
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
        for (int i = 0; i < names.length; i++)
        {
        	par3List.add(new ItemStack(par1, 1, i));
        }
    }
}

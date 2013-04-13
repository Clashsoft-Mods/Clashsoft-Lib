package clashsoft.clashsoftapi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

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
	}
	
	@Override
	public String getItemDisplayName(ItemStack is)
	{
		return names[is.getItemDamage()];
	}
	
	@Override
	public Icon getIconFromDamage(int i)
	{
		return icons[i];
	}

	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) //Registers the Icons
    {
        for (int i = 0; i < textures.length; i++)
        {
        	this.icons[i] = par1IconRegister.registerIcon(textures[i]);
        }
    }
}

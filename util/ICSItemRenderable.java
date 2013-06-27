package clashsoft.clashsoftapi.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface ICSItemRenderable
{
	public Icon getIcon(ItemStack par1ItemStack);
	public int getGlintColor(ItemStack par1ItemStack);
}

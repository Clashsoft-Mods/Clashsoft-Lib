package clashsoft.cslib.inventory.itemmatcher;

import net.minecraft.item.ItemStack;

public interface IItemMatcher
{
	public boolean isItemValid(ItemStack stack);
}

package clashsoft.cslib.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingHelper extends Item
{
	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		ItemStack copy = stack.copy();
		if (copy.attemptDamageItem(1, itemRand))
		{
			copy = null;
		}
		return copy;
	}
}

package clashsoft.cslib.stack;

import gnu.trove.map.hash.TCustomHashMap;
import net.minecraft.item.ItemStack;

public class ItemStackHashMap<V> extends TCustomHashMap<ItemStack, V>
{
	public ItemStackHashMap()
	{
		super(ItemStackHash.itemInstance);
	}
	
	public ItemStackHashMap(boolean stackSize)
	{
		super(stackSize ? ItemStackHash.stackInstance : ItemStackHash.itemInstance);
	}
}

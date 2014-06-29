package clashsoft.cslib.minecraft.util;

import gnu.trove.map.hash.TCustomHashMap;

import net.minecraft.item.ItemStack;

public class ItemStackHashMap<V> extends TCustomHashMap<ItemStack, V>
{
	public ItemStackHashMap()
	{
		super(ItemStackHash.instance);
	}
	
	public ItemStackHashMap(boolean stackSize)
	{
		super(stackSize ? ItemStackHash.stackSizeInstance : ItemStackHash.instance);
	}
}

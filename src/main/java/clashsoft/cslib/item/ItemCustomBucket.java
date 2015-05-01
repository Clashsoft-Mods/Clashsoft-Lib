package clashsoft.cslib.item;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemCustomBucket extends ItemBucket
{
	public ItemCustomBucket(Block block)
	{
		super(block);
		this.setContainerItem(Items.bucket);
	}
}

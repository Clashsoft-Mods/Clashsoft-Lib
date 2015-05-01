package clashsoft.cslib.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import clashsoft.cslib.stack.CSStacks;

public class CustomCreativeTab extends CreativeTabs
{
	public Item			item;
	public int			metadata;
	
	public ItemStack	stack;
	
	public CustomCreativeTab(String label)
	{
		this(label, CSStacks.air);
	}
	
	public CustomCreativeTab(String label, ItemStack stack)
	{
		this(label, null, 0);
		this.stack = stack;
	}
	
	public CustomCreativeTab(String label, Item item, int metadata)
	{
		super(label);
		this.item = item;
		this.metadata = metadata;
	}
	
	public void setIconItemStack(ItemStack stack)
	{
		this.stack = stack;
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		if (this.stack == null)
		{
			if (this.item != null)
			{
				this.stack = new ItemStack(this.item, 1, this.metadata);
			}
			else
			{
				this.stack = CSStacks.air;
			}
		}
		return this.stack;
	}
	
	@Override
	public Item getTabIconItem()
	{
		return this.item;
	}
	
	@Override
	public int getIconItemDamage()
	{
		return this.metadata;
	}
}

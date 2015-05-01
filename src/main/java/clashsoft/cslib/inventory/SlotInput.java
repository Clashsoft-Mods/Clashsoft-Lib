package clashsoft.cslib.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import clashsoft.cslib.inventory.itemmatcher.IItemMatcher;

public class SlotInput extends Slot2
{
	public IItemMatcher	iItemMatcher;
	
	public SlotInput(IInventory inventory, int slotID, int x, int y)
	{
		this(inventory, slotID, x, y, null);
	}
	
	public SlotInput(IInventory inventory, int slotID, int x, int y, IItemMatcher iItemMatcher)
	{
		super(inventory, slotID, x, y);
		this.iItemMatcher = iItemMatcher;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return this.iItemMatcher == null ? this.inventory.isItemValidForSlot(this.getSlotIndex(), stack) : this.iItemMatcher.isItemValid(stack);
	}
}

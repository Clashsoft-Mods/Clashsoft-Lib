package clashsoft.cslib.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerInventory extends Container
{
	public IInventory inventory;
	
	public ContainerInventory(EntityPlayer player, IInventory inventory)
	{
		this.inventory = inventory;
	}
	
	public void addInventorySlots()
	{
		this.addInventorySlots(0, 0);
	}
	
	public void addInventorySlots(int xOffset, int yOffset)
	{
		int k = 8 + xOffset;
		int l = 84 + xOffset;
		int m = 142 + xOffset;
		
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inventory, 9 + j + i * 9, k + j * 18, l + i * 18));
			}
		}
		
		for (int i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventory, i, k + i * 18, m));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.inventory.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
	{
		int inv_low = this.inventory.getSizeInventory();
		int inv_high = inv_low + 27;
		int hotbar_low = inv_low + 36;
		int hotbar_high = hotbar_low + 9; 
		
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (slotID < inv_low)
			{
				if (!this.mergeItemStack(itemstack1, inv_low, inv_high, false))
				{
					return null;
				}
			}
			else if (slotID >= inv_low && slotID < hotbar_high && this.tryMerge(player, slotID, itemstack1))
			{
				return null;
			}
			else if (slotID >= inv_low && slotID < inv_high)
			{
				if (!this.mergeItemStack(itemstack1, hotbar_low, hotbar_high, false))
				{
					return null;
				}
			}
			else if (slotID >= hotbar_low && slotID < hotbar_high)
			{
				if (!this.mergeItemStack(itemstack1, inv_low, inv_high, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, inv_low, hotbar_high, false))
			{
				return null;
			}
			
			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, itemstack1);
		}
		
		return itemstack;
	}
	
	public boolean tryMerge(EntityPlayer player, int slot, ItemStack stack)
	{
		return false;
	}
}

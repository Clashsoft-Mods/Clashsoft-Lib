package clashsoft.cslib.minecraft.tileentity;

import clashsoft.cslib.minecraft.stack.CSStacks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityInventory extends TileEntity implements IInventory
{
	public String		name;
	public ItemStack[]	itemStacks;
	
	public TileEntityInventory()
	{
		this.itemStacks = new ItemStack[this.getSizeInventory()];
	}
	
	public TileEntityInventory(int size)
	{
		this.itemStacks = new ItemStack[size];
	}
	
	public boolean mergeStack(ItemStack stack, int start, int end)
	{
		int slotID = CSStacks.mergeItemStack(this.itemStacks, start, end, stack);
		if (slotID != -1)
		{
			this.onSlotChanged(slotID);
			return true;
		}
		return false;
	}
	
	@Override
	public abstract int getSizeInventory();
	
	public final boolean rangeCheck(int slotID)
	{
		return slotID >= 0 && slotID < this.itemStacks.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int slotID)
	{
		if (this.rangeCheck(slotID))
		{
			return this.itemStacks[slotID];
		}
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int slotID, int amount)
	{
		if (this.rangeCheck(slotID) && this.itemStacks[slotID] != null)
		{
			ItemStack itemstack;
			
			if (this.itemStacks[slotID].stackSize <= amount)
			{
				itemstack = this.itemStacks[slotID];
				this.itemStacks[slotID] = null;
				this.onSlotChanged(slotID);
				return itemstack;
			}
			else
			{
				itemstack = this.itemStacks[slotID].splitStack(amount);
				
				if (this.itemStacks[slotID].stackSize == 0)
				{
					this.itemStacks[slotID] = null;
				}
				
				this.onSlotChanged(slotID);
				
				return itemstack;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slotID)
	{
		if (this.rangeCheck(slotID) && this.itemStacks[slotID] != null)
		{
			ItemStack itemstack = this.itemStacks[slotID];
			this.itemStacks[slotID] = null;
			return itemstack;
		}
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int slotID, ItemStack stack)
	{
		if (this.rangeCheck(slotID))
		{
			this.itemStacks[slotID] = stack;
			this.onSlotChanged(slotID);
			
			if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			{
				stack.stackSize = this.getInventoryStackLimit();
			}
		}
	}
	
	public void onSlotChanged(int slotID)
	{
		this.markDirty();
	}
	
	@Override
	public String getName()
	{
		if (this.name != null)
		{
			return this.name;
		}
		else
		{
			return new ItemStack(this.blockType, 1, this.getBlockMetadata()).getDisplayName();
		}
	}
	
	@Override
	public boolean hasCustomName()
	{
		return this.name != null;
	}
	
	public void setInventoryName(String name)
	{
		this.name = name;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack stack)
	{
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		this.itemStacks = new ItemStack[this.getSizeInventory()];
		
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte slot = nbt1.getByte("Slot");
			this.itemStacks[slot] = ItemStack.loadItemStackFromNBT(nbt1);
		}
		
		if (nbt.hasKey("CustomName"))
		{
			this.name = nbt.getString("CustomName");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();
		
		for (int i = 0; i < this.itemStacks.length; ++i)
		{
			if (this.itemStacks[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		
		nbt.setTag("Items", list);
		
		if (this.hasCustomName())
		{
			nbt.setString("CustomName", this.name);
		}
	}
}

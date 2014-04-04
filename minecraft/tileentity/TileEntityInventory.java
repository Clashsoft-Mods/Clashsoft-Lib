package clashsoft.cslib.minecraft.tileentity;

import clashsoft.cslib.minecraft.item.CSStacks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityInventory extends TileEntity implements IInventory
{
	public String		name;
	public int			size;
	public ItemStack[]	itemStacks;
	
	public TileEntityInventory()
	{
	}
	
	public TileEntityInventory(int size)
	{
		this.size = size;
		this.itemStacks = new ItemStack[size];
	}
	
	public void mergeStack(ItemStack stack, int start, int end)
	{
		CSStacks.mergeItemStack(this.itemStacks, start, end, stack);
	}
	
	@Override
	public int getSizeInventory()
	{
		return this.size;
	}
	
	@Override
	public ItemStack getStackInSlot(int slotID)
	{
		return this.itemStacks[slotID];
	}
	
	@Override
	public ItemStack decrStackSize(int slotID, int amount)
	{
		if (this.itemStacks[slotID] != null)
		{
			ItemStack itemstack;
			
			if (this.itemStacks[slotID].stackSize <= amount)
			{
				itemstack = this.itemStacks[slotID];
				this.itemStacks[slotID] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.itemStacks[slotID].splitStack(amount);
				
				if (this.itemStacks[slotID].stackSize == 0)
				{
					this.itemStacks[slotID] = null;
				}
				
				return itemstack;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slotID)
	{
		if (this.itemStacks[slotID] != null)
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
		this.itemStacks[slotID] = stack;
		
		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.name : this.blockType.getUnlocalizedName();
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return this.name != null && !this.name.isEmpty();
	}
	
	public void setInvName(String name)
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
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
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
		
		NBTTagList list = nbt.getTagList("Items", 10);
		this.itemStacks = new ItemStack[this.getSizeInventory()];
		
		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = list.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			
			if (b0 >= 0 && b0 < this.itemStacks.length)
			{
				this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		if (nbt.hasKey("CustomName"))
		{
			this.name = nbt.getString("CustomName");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		
		for (int i = 0; i < this.itemStacks.length; ++i)
		{
			if (this.itemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.itemStacks[i].writeToNBT(nbttagcompound1);
				list.appendTag(nbttagcompound1);
			}
		}
		
		nbt.setTag("Items", list);
		
		if (this.hasCustomInventoryName())
		{
			nbt.setString("CustomName", this.name);
		}
	}
	
	@Override
	public void closeInventory()
	{
	}
	
	@Override
	public void openInventory()
	{
	}
}

package clashsoft.cslib.minecraft.stack;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackTable
{
	protected Entry[]		hashArray;
	protected int			size;
	protected int			capacity;
	protected final float	loadFactor;
	protected volatile int	modCount;
	
	public ItemStackTable()
	{
		this(16, 0.75F);
	}
	
	public ItemStackTable(int size, float loadFactor)
	{
		this.capacity = (int) (size * loadFactor);
		this.loadFactor = loadFactor;
		this.hashArray = new Entry[size];
	}
	
	private static int hash(Item item, int stackSize, int metadata)
	{
		return hash(CSStacks.hashCode(item, stackSize, metadata));
	}
	
	private static int hash(int hash)
	{
		hash ^= hash >>> 20 ^ hash >>> 12;
		return hash ^ hash >>> 7 ^ hash >>> 4;
	}
	
	private static int getHashIndex(int hash, int size)
	{
		return hash & size - 1;
	}
	
	protected int size()
	{
		return this.size;
	}
	
	public ItemStack get(Item item, int stackSize, int metadata)
	{
		Entry entry = this.getEntry(item, stackSize, metadata);
		return entry == null ? null : entry.value;
	}
	
	public boolean contains(Item item, int stackSize, int metadata)
	{
		return this.getEntry(item, stackSize, metadata) != null;
	}
	
	protected final Entry getEntry(Item item, int stackSize, int metadata)
	{
		int i = hash(item, stackSize, metadata);
		for (Entry entry = this.hashArray[getHashIndex(i, this.hashArray.length)]; entry != null; entry = entry.next)
		{
			if (entry.item == item && entry.stackSize == stackSize && entry.metadata == metadata)
			{
				return entry;
			}
		}
		return null;
	}
	
	public ItemStack put(Item item, int stackSize, int metadata, ItemStack stack)
	{
		int i = hash(item, stackSize, metadata);
		int j = getHashIndex(i, this.hashArray.length);
		for (Entry localEntry = this.hashArray[j]; localEntry != null; localEntry = localEntry.next)
		{
			if (localEntry.keyEquals(item, stackSize, metadata))
			{
				return localEntry.setValue(stack);
			}
		}
		
		this.size++;
		this.modCount++;
		this.createKey(i, item, stackSize, metadata, stack, j);
		
		return null;
	}
	
	private void resizeTable(int newSize)
	{
		Entry[] src = this.hashArray;
		int i = src.length;
		
		if (i == 1073741824)
		{
			this.capacity = 2147483647;
			return;
		}
		
		Entry[] dest = new Entry[newSize];
		this.copyHashTableTo(dest);
		this.hashArray = dest;
		this.capacity = (int) (newSize * this.loadFactor);
	}
	
	private void copyHashTableTo(Entry[] dest)
	{
		Entry[] src = this.hashArray;
		int i = dest.length;
		for (int j = 0; j < src.length; ++j)
		{
			Entry entry = src[j];
			if (entry != null)
			{
				src[j] = null;
				do
				{
					Entry localEntry = entry.next;
					int k = getHashIndex(entry.hash, i);
					entry.next = dest[k];
					dest[k] = entry;
					entry = localEntry;
				}
				while (entry != null);
			}
		}
	}
	
	protected ItemStack remove(Item item, int stackSize, int metadata)
	{
		Entry entry = this.removeKey(item, stackSize, metadata);
		return entry == null ? null : entry.value;
	}
	
	protected final Entry removeKey(Item item, int stackSize, int metadata)
	{
		int hash = hash(item, stackSize, metadata);
		int index = getHashIndex(hash, this.hashArray.length);
		Entry entry1 = this.hashArray[index];
		Entry entry2 = entry1;
		
		while (entry2 != null)
		{
			Entry next = entry2.next;
			if (entry2.keyEquals(item, stackSize, metadata))
			{
				this.modCount++;
				this.size--;
				if (entry1 == entry2)
				{
					this.hashArray[index] = next;
				}
				else
				{
					entry1.next = next;
				}
				return entry2;
			}
			entry1 = entry2;
			entry2 = next;
		}
		
		return entry2;
	}
	
	private void createKey(int hash, Item item, int stackSize, int metadata, ItemStack object, int index)
	{
		Entry entry = this.hashArray[index];
		this.hashArray[index] = new Entry(hash, item, stackSize, metadata, object, entry);
		if (this.size++ >= this.capacity)
		{
			this.resizeTable(2 * this.hashArray.length);
		}
	}
	
	protected static final class Entry
	{
		final Item	item;
		final int	stackSize;
		final int	metadata;
		
		ItemStack	value;
		Entry		next;
		int			hash;
		
		Entry(int h, Item i, int s, int m, ItemStack v, Entry n)
		{
			this.item = i;
			this.stackSize = s;
			this.metadata = m;
			this.value = v;
			this.next = n;
			this.hash = h;
		}
		
		public Item getItem()
		{
			return this.item;
		}
		
		public int getStackSize()
		{
			return this.stackSize;
		}
		
		public int getMetadata()
		{
			return this.metadata;
		}
		
		public ItemStack getValue()
		{
			return this.value;
		}
		
		public ItemStack setValue(ItemStack newValue)
		{
			ItemStack oldValue = this.value;
			this.value = newValue;
			return oldValue;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof Entry))
			{
				return false;
			}
			Entry e = (Entry) o;
			if (this.keyEquals(e.item, e.stackSize, e.metadata))
			{
				return CSStacks.equals(this.value, e.value);
			}
			return false;
		}
		
		public boolean keyEquals(Item item, int stackSize, int metadata)
		{
			return CSStacks.equals(this.item, this.stackSize, this.metadata, item, stackSize, metadata);
		}
		
		@Override
		public int hashCode()
		{
			return CSStacks.hashCode(this.item, this.stackSize, this.metadata) ^ Objects.hashCode(this.value);
		}
		
		@Override
		public String toString()
		{
			return CSStacks.toString(this.item, this.stackSize, this.metadata) + "=" + this.value;
		}
	}
}

package clashsoft.cslib.stack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import clashsoft.cslib.crafting.CSCrafting;

public class CSStacks
{
	public static ItemStack	air						= StackFactory.create(Blocks.air);
	
	public static ItemStack	birch_log				= StackFactory.create(Blocks.log, 1, 2);
	public static ItemStack	birch_planks			= StackFactory.create(Blocks.planks, 1, 2);
	
	public static ItemStack	dirt					= StackFactory.create(Blocks.dirt);
	
	public static ItemStack	end_stone				= StackFactory.create(Blocks.end_stone);
	
	public static ItemStack	fire					= StackFactory.create(Blocks.fire);
	
	public static ItemStack	glass_block				= StackFactory.create(Blocks.glass);
	public static ItemStack	glass_pane				= StackFactory.create(Blocks.glass_pane);
	public static ItemStack	glowstone				= StackFactory.create(Blocks.glowstone);
	public static ItemStack	gold_block				= StackFactory.create(Blocks.gold_block);
	
	public static ItemStack	ice						= StackFactory.create(Blocks.ice);
	
	public static ItemStack	jungle_log				= StackFactory.create(Blocks.log, 1, 3);
	public static ItemStack	jungle_planks			= StackFactory.create(Blocks.planks, 1, 3);
	
	public static ItemStack	lapis_block				= StackFactory.create(Blocks.lapis_block);
	
	public static ItemStack	oak_log					= StackFactory.create(Blocks.log, 1, 0);
	public static ItemStack	oak_planks				= StackFactory.create(Blocks.planks, 1, 0);
	public static ItemStack	obsidian				= StackFactory.create(Blocks.obsidian);
	
	public static ItemStack	pumpkin					= StackFactory.create(Blocks.pumpkin);
	
	public static ItemStack	redstone_block			= StackFactory.create(Blocks.redstone_block);
	
	public static ItemStack	sandstone				= StackFactory.create(Blocks.sandstone);
	public static ItemStack	spruce_log				= StackFactory.create(Blocks.log, 1, 1);
	public static ItemStack	spruce_planks			= StackFactory.create(Blocks.planks, 1, 1);
	
	// public static ItemStack = StackFactory.create(Items.);
	
	public static ItemStack	apple					= StackFactory.create(Items.apple);
	
	public static ItemStack	beef					= StackFactory.create(Items.beef);
	public static ItemStack	blaze_powder			= StackFactory.create(Items.blaze_powder);
	public static ItemStack	blaze_rod				= StackFactory.create(Items.blaze_rod);
	public static ItemStack	bone					= StackFactory.create(Items.bone);
	public static ItemStack	bonemeal				= StackFactory.create(Items.dye, 1, 15);
	public static ItemStack	bowl					= StackFactory.create(Items.bowl);
	public static ItemStack	bread					= StackFactory.create(Items.bread);
	public static ItemStack	brick					= StackFactory.create(Items.brick);
	public static ItemStack	bucket					= StackFactory.create(Items.bucket);
	
	public static ItemStack	carrot					= StackFactory.create(Items.carrot);
	public static ItemStack	char_coal				= StackFactory.create(Items.coal, 1, 1);
	public static ItemStack	chicken					= StackFactory.create(Items.chicken);
	public static ItemStack	clay					= StackFactory.create(Items.clay_ball);
	public static ItemStack	coal					= StackFactory.create(Items.coal);
	public static ItemStack	cocoa					= StackFactory.create(Items.dye, 1, 3);
	
	public static ItemStack	diamond					= StackFactory.create(Items.diamond);
	public static ItemStack	dye_red					= StackFactory.create(Items.dye, 1, 1);
	public static ItemStack	dye_light_green			= StackFactory.create(Items.dye, 1, 10);
	
	public static ItemStack	egg						= StackFactory.create(Items.egg);
	public static ItemStack	emerald					= StackFactory.create(Items.emerald);
	public static ItemStack	ender_eye				= StackFactory.create(Items.ender_eye);
	public static ItemStack	ender_pearl				= StackFactory.create(Items.ender_pearl);
	
	public static ItemStack	fermented_spider_eye	= StackFactory.create(Items.fermented_spider_eye);
	public static ItemStack	fish					= StackFactory.create(Items.fish);
	public static ItemStack	flint					= StackFactory.create(Items.flint);
	
	public static ItemStack	ghast_tear				= StackFactory.create(Items.ghast_tear);
	public static ItemStack	glowstone_dust			= StackFactory.create(Items.glowstone_dust);
	public static ItemStack	golden_carrot			= StackFactory.create(Items.golden_carrot);
	public static ItemStack	gold_ingot				= StackFactory.create(Items.gold_ingot);
	public static ItemStack	gold_nugget				= StackFactory.create(Items.gold_nugget);
	
	public static ItemStack	iron_ingot				= StackFactory.create(Items.iron_ingot);
	
	public static ItemStack	lapis_lazuli			= StackFactory.create(Items.dye, 1, 4);
	public static ItemStack	lava_bucket				= StackFactory.create(Items.lava_bucket);
	public static ItemStack	leather					= StackFactory.create(Items.leather);
	
	public static ItemStack	magma_cream				= StackFactory.create(Items.magma_cream);
	public static ItemStack	melon					= StackFactory.create(Items.melon);
	public static ItemStack	milk_bucket				= StackFactory.create(Items.milk_bucket);
	
	public static ItemStack	nether_brick			= StackFactory.create(Items.netherbrick);
	public static ItemStack	nether_star				= StackFactory.create(Items.nether_star);
	
	public static ItemStack	paper					= StackFactory.create(Items.paper);
	public static ItemStack	porkchop				= StackFactory.create(Items.porkchop);
	public static ItemStack	potato					= StackFactory.create(Items.potato);
	
	public static ItemStack	quartz					= StackFactory.create(Items.quartz);
	
	public static ItemStack	reeds					= StackFactory.create(Items.reeds);
	public static ItemStack	redstone_dust			= StackFactory.create(Items.redstone);
	
	public static ItemStack	slimeball				= StackFactory.create(Items.slime_ball);
	public static ItemStack	snowball				= StackFactory.create(Items.snowball);
	public static ItemStack	speckled_melon			= StackFactory.create(Items.speckled_melon);
	public static ItemStack	spider_eye				= StackFactory.create(Items.spider_eye);
	public static ItemStack	stick					= StackFactory.create(Items.stick);
	public static ItemStack	sugar					= StackFactory.create(Items.sugar);
	
	public static ItemStack	water_bucket			= StackFactory.create(Items.water_bucket);
	public static ItemStack	wheat					= StackFactory.create(Items.wheat);
	public static ItemStack	wither_skull			= StackFactory.create(Items.skull, 1, 1);
	
	public static boolean equals(ItemStack input, ItemStack target)
	{
		return itemEquals(input, target);
	}
	
	public static boolean stackEquals(ItemStack input, ItemStack target)
	{
		if (input == target)
			return true;
		else if (input == null)
			return target == null;
		else if (target == null)
			return false;
		return equals(input.getItem(), input.stackSize, input.getItemDamage(), target.getItem(), target.stackSize, target.getItemDamage());
	}
	
	public static boolean itemEquals(ItemStack input, ItemStack target)
	{
		if (input == target)
			return true;
		else if (input == null)
			return target == null;
		else if (target == null)
			return false;
		return equals(input.getItem(), input.getItemDamage(), target.getItem(), target.getItemDamage());
	}
	
	public static boolean equals(Item item1, int meta1, Item item2, int meta2)
	{
		return item1 == item2 && (meta1 == meta2 || meta1 == CSCrafting.WILDCARD_VALUE || meta2 == CSCrafting.WILDCARD_VALUE);
	}
	
	public static boolean equals(Item item1, int size1, int meta1, Item item2, int size2, int meta2)
	{
		return item1 == item2 && size1 == size2 && (meta1 == meta2 || meta1 == CSCrafting.WILDCARD_VALUE || meta2 == CSCrafting.WILDCARD_VALUE);
	}
	
	public static int hashCode(ItemStack stack)
	{
		return itemHashCode(stack);
	}
	
	public static int itemHashCode(ItemStack stack)
	{
		return stack == null ? 0 : hashCode(stack.getItem(), 1, stack.getItemDamage());
	}
	
	public static int stackHashCode(ItemStack stack)
	{
		return stack == null ? 0 : hashCode(stack.getItem(), stack.stackSize, stack.getItemDamage());
	}
	
	public static int hashCode(Item item, int stackSize, int metadata)
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (item == null ? 0 : Item.getIdFromItem(item));
		result = prime * result + stackSize;
		result = prime * result + metadata;
		return result;
	}
	
	public static String toString(ItemStack stack)
	{
		return stack == null ? "null" : toString(stack.getItem(), stack.stackSize, stack.getItemDamage());
	}
	
	public static String toString(Item item, int stackSize, int metadata)
	{
		return new StringBuilder(20).append(stackSize).append(" x ").append(item.getUnlocalizedName()).append(":").append(metadata).toString();
	}
	
	public static int mergeItemStack(ItemStack[] stacks, int start, ItemStack stack)
	{
		return mergeItemStack(stacks, start, stacks.length, stack);
	}
	
	public static int mergeItemStack(ItemStack[] stacks, int start, int end, ItemStack stack)
	{
		int i = -1;
		int max = stack.getMaxStackSize();
		ItemStack stack1;
		
		for (int j = start; j < end && stack.isStackable() && stack.stackSize > 0; j++)
		{
			stack1 = stacks[j];
			if (stack1 != null && equals(stack, stack1))
			{
				int k = stack1.stackSize + stack.stackSize;
				if (k <= max)
				{
					stack.stackSize = 0;
					stack1.stackSize = k;
					i = j;
				}
				else if (stack1.stackSize < max)
				{
					stack.stackSize -= max - stack1.stackSize;
					stack1.stackSize = max;
					i = j;
				}
			}
		}
		
		if (stack.stackSize > 0)
		{
			for (int j = start; j < end; j++)
			{
				stack1 = stacks[j];
				if (stack1 == null)
				{
					stacks[j] = stack.copy();
					stack.stackSize = 0;
					return j;
				}
			}
		}
		
		return i;
	}
	
	public static NBTTagCompound getNBT(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null)
		{
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}
}

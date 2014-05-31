package clashsoft.cslib.minecraft.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IRecipeManager
{
	
	public abstract void addRecipe(Block block, ItemStack output);
	
	public abstract void addRecipe(Item item, ItemStack output);
	
	public abstract void addRecipe(ItemStack input, ItemStack output);
	
	public abstract ItemStack getResult(ItemStack ingredient);
	
}
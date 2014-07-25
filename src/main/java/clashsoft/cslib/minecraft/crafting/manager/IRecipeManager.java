package clashsoft.cslib.minecraft.crafting.manager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IRecipeManager
{
	/**
	 * Adds a new recipe.
	 * 
	 * @param block
	 *            the block
	 * @param output
	 *            the output
	 */
	public void addRecipe(Block block, ItemStack output);
	
	/**
	 * Adds a new recipe.
	 * 
	 * @param item
	 *            the item
	 * @param output
	 *            the output
	 */
	public void addRecipe(Item item, ItemStack output);
	
	/**
	 * Adds a new recipe.
	 * 
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 */
	public void addRecipe(ItemStack input, ItemStack output);
	
	/**
	 * Returns the crafting result for the given {@link ItemStack}
	 * {@code ingredient}
	 * 
	 * @param ingredient
	 *            the ingredient
	 * @return the output
	 */
	public ItemStack getResult(ItemStack ingredient);
}
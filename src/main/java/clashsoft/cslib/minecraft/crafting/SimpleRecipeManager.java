package clashsoft.cslib.minecraft.crafting;

import java.util.Map;

import clashsoft.cslib.minecraft.stack.ItemStackHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * A simple crafting recipe manager. This manager functions like the furnace
 * crafting manager for one-to-one recipes, but doesn't support experiene
 * output.
 * 
 * @author Clashsoft
 */
public class SimpleRecipeManager implements IRecipeManager
{
	private Map<ItemStack, ItemStack>	recipeMap	= new ItemStackHashMap();
	
	protected SimpleRecipeManager()
	{
	}
	
	@Override
	public void addRecipe(Block block, ItemStack output)
	{
		this.addRecipe(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), output);
	}
	
	@Override
	public void addRecipe(Item item, ItemStack output)
	{
		this.addRecipe(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), output);
	}
	
	@Override
	public void addRecipe(ItemStack input, ItemStack output)
	{
		this.recipeMap.put(input, output.copy());
	}
	
	@Override
	public ItemStack getResult(ItemStack input)
	{
		return this.recipeMap.get(input);
	}
}

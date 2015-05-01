package clashsoft.cslib.crafting.loader;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import clashsoft.cslib.crafting.CSCrafting;

public class FurnaceRecipeLoader extends CustomRecipeLoader
{
	public static FurnaceRecipeLoader	instance	= new FurnaceRecipeLoader();
	
	public FurnaceRecipeLoader()
	{
		super("furnace");
	}
	
	@Override
	public void addRecipe(Item input, int inputMeta, Item output, int outputMeta, int amount, float exp)
	{
		CSCrafting.addFurnaceRecipe(new ItemStack(input, 1, inputMeta), new ItemStack(output, amount, outputMeta), exp);
	}
}

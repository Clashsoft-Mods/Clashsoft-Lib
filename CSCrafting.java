package clashsoft.clashsoftapi;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.ModLoader;

public class CSCrafting
{
	/**
	 * Adds a Furnace recipe.
	 * @param par1 Input
	 * @param par2ItemStack Output
	 * @param par3 Experience
	 */
	public static void addSmelting(ItemStack par1ItemStack, ItemStack par2ItemStack, float par3)
	{
		FurnaceRecipes.smelting().addSmelting(par1ItemStack.getItem().itemID, par1ItemStack.getItemDamage(), par2ItemStack, par3);
	}
	
	/**
	 * Adds an Armor-shaped recipe. (Parts: 0 = Helmet, 1 = Chestplate, 2 = Leggings, 3 = Boots.)
	 * @param out Output ItemStack
	 * @param input Main Crafting Material
	 * @param part Armor Type
	 */
	public static void addArmorRecipe(ItemStack out, ItemStack input, int part)
	{
		if (part == 0)
		{
			ModLoader.addRecipe(out, new Object[]{
				"XXX",
				"X X",
				Character.valueOf('X'), input
			});
		}
		else if (part == 1)
		{
			ModLoader.addRecipe(out, new Object[]{
				"X X",
				"XXX",
				"XXX",
				Character.valueOf('X'), input
			});
		}
		else if (part == 2)
		{
			ModLoader.addRecipe(out, new Object[]{
				"XXX",
				"X X",
				"X X",
				Character.valueOf('X'), input
			});
		}
		else if (part == 3)
		{
			ModLoader.addRecipe(out, new Object[]{
				"X X",
				"X X",
				Character.valueOf('X'), input
			});
		}
	}

	/**
	 * Adds a Tool-shaped recipe. (Parts: 0 = Sword, 1 = Spade, 2 = Pickaxe, 3 = Axe, 4 = Hoe)
	 * @param out Output ItemStack
	 * @param input Main crafting Material
	 * @param part Tool Type
	 */
	public static void addToolRecipe(ItemStack out, ItemStack input, int part)
	{
		if (part == 0)
		{
			ModLoader.addRecipe(out, new Object[]{
				"X",
				"X",
				"|",
				'X', input,
				'|', Item.stick
			});
		}
		if (part == 1)
		{
			ModLoader.addRecipe(out, new Object[]{
				"X",
				"|",
				"|",
				'X', input,
				'|', Item.stick
			});
		}
		if (part == 2)
		{
			ModLoader.addRecipe(out, new Object[]{
				"XXX",
				" | ",
				" | ",
				'X', input,
				'|', Item.stick
			});
		}
		if (part == 3)
		{
			ModLoader.addRecipe(out, new Object[]{
				"XX ",
				"X| ",
				" | ",
				'X', input,
				'|', Item.stick
			});
		}
		if (part == 4)
		{
			ModLoader.addRecipe(out, new Object[]{
				"XX",
				" |",
				" |",
				'X', input,
				'|', Item.stick
			});
		}
	}
	
	/**
	 * Removes a recipe from the game
	 * @param recipe Recipe to remove
	 */
	public static void removeRecipe(Object[] recipe)
	{
		ModLoader.addRecipe(new ItemStack(0, 0, 0), recipe);
	}
}

package clashsoft.clashsoftapi.util;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

/**
 * The Class CSCrafting.
 * <p>
 * This class adds several methods for adding crafting and furnace recipes and analyzing them.
 */
public class CSCrafting
{	
	/** The Constant FIRE. */
	private static final ItemStack	FIRE	= new ItemStack(Block.fire, 1, 0);
	
	/** The Constant COAL. */
	private static final ItemStack	COAL = new ItemStack(Item.coal, 1, 0);
	
	/**
	 * Adds a new shaped crafting recipe to the game.
	 * 
	 * @param stack
	 *            the output
	 * @param recipe
	 *            the recipe
	 * @see GameRegistry#addRecipe(ItemStack, Object...)
	 */
	public static void addCrafting(ItemStack stack, Object... recipe)
	{
		GameRegistry.addRecipe(stack, recipe);
	}
	
	/**
	 * Adds a new shapeless crafting recipe to the game.
	 * 
	 * @param stack
	 *            the output
	 * @param recipe
	 *            the recipe
	 * @see GameRegistry#addShapelessRecipe(ItemStack, Object...)
	 */
	public static void addShapelessCrafting(ItemStack stack, Object... recipe)
	{
		GameRegistry.addShapelessRecipe(stack, recipe);
	}
	
	/**
	 * Adds a new crafting recipe to the game.
	 * 
	 * @param shapeless
	 *            shapeless or shaped
	 * @param stack
	 *            the stack
	 * @param recipe
	 *            the recipe
	 * @see GameRegistry#addRecipe(ItemStack, Object...)
	 * @see GameRegistry#addShapelessRecipe(ItemStack, Object...)
	 */
	@Deprecated
	public static void addCrafting(boolean shapeless, ItemStack stack, Object... recipe)
	{
		if (shapeless)
			GameRegistry.addShapelessRecipe(stack, recipe);
		else
			GameRegistry.addRecipe(stack, recipe);
	}
	
	/**
	 * Adds a new furnace recipe to the game.
	 * 
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @param experience
	 *            the experience
	 * @see FurnaceRecipes.addSmelting(int, int ItemStack, float)
	 */
	public static void addSmelting(ItemStack input, ItemStack output, float experience)
	{
		FurnaceRecipes.smelting().addSmelting(input.getItem().itemID, input.getItemDamage(), output, experience);
	}
	
	/**
	 * Adds a new armor-shaped recipe to the game. (Type 0 = Helmet, Type 1 =
	 * Chestplate, Type 2 = Leggings, Type 3 = Boots)
	 * 
	 * @param output
	 *            the output
	 * @param input
	 *            the material
	 * @param type
	 *            the armor type
	 */
	public static void addArmorRecipe(ItemStack output, ItemStack input, int type)
	{
		if (type == 0)
			addCrafting(output, new Object[] { "XXX", "X X", Character.valueOf('X'), input });
		else if (type == 1)
			addCrafting(output, new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), input });
		else if (type == 2)
			addCrafting(output, new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), input });
		else if (type == 3)
			addCrafting(output, new Object[] { "X X", "X X", Character.valueOf('X'), input });
	}
	
	/**
	 * Adds a new tool-shaped recipe to the game. (Type 0 = Sword, Type 1 =
	 * Spade, Type 2 = Pickaxe, Type 3 = Axe, Type 4 = Hoe)
	 * 
	 * @param output
	 *            the output
	 * @param input
	 *            the material
	 * @param type
	 *            the tool type
	 */
	public static void addToolRecipe(ItemStack output, ItemStack input, int type)
	{
		if (type == 0)
			addCrafting(output, new Object[] { "X", "X", "|", 'X', input, '|', Item.stick });
		else if (type == 1)
			addCrafting(output, "X", "|", "|", 'X', input, '|', Item.stick);
		else if (type == 2)
			addCrafting(output, "XXX", " | ", " | ", 'X', input, '|', Item.stick);
		else if (type == 3)
			addCrafting(output, "XX ", "X| ", " | ", 'X', input, '|', Item.stick);
		else if (type == 4)
			addCrafting(output, "XX", " |", " |", 'X', input, '|', Item.stick);
	}
	
	/**
	 * Removes a recipe from the game.
	 * 
	 * @param recipe
	 *            Recipe to remove
	 */
	public static void removeRecipe(Object... recipe)
	{
		addCrafting(new ItemStack(0, 0, 0), recipe);
	}
	
	/**
	 * Registers an ore to the ore dictionary.
	 * 
	 * @see OreDictionary
	 * @see OreDictionary#registerOre(String, ItemStack)
	 * 
	 * @param name
	 *            the name
	 * @param ore
	 *            the ore
	 * @return the item stack
	 */
	public static ItemStack registerOre(String name, ItemStack ore)
	{
		OreDictionary.registerOre(name, ore);
		return ore;
	}
	
	/**
	 * Analyzes a crafting recipe, mainly used for recipe displays.
	 * 
	 * Depending on the recipe type, the output is either
	 * <p>
	 * [1][2][ ] Any shape possible<p>
	 * [3][4][ ] # = how you need to put the items in the crafting table<p>
	 * [ ][ ][5]<p>
	 * <p>
	 * for shaped recipes,<p>
	 * <p>
	 * [1][2][3] # = objects in shaped recipe list<p>
	 * [4][5][ ]<p>
	 * [ ][ ][ ]<p>
	 * <p>
	 * for shapeless recipes or<p>
	 * <p>
	 * [ ][o][ ] o = output<p>
	 * [ ][f][ ] f = fire<p>
	 * [ ][c][ ] c = coal<p>
	 * <p>
	 * for furnace recipes.
	 * 
	 * @param recipe
	 *            the recipe
	 * @return the item stack[][]
	 */
	public static ItemStack[][] analyseCrafting(IItemMetadataRecipe recipe)
	{
		try
		{
			if (recipe.getCraftingType() == IItemMetadataRecipe.FURNACE)
			{
				return new ItemStack[][] { { null, (ItemStack) recipe.getData()[0], null }, { null, FIRE, null }, { null, COAL, null } };
			}
			else if (recipe.getCraftingType() == IItemMetadataRecipe.CRAFTING_SHAPELESS)
			{
				ItemStack[][] ret = new ItemStack[3][3];
				
				for (int i = 0; i < recipe.getData().length; i++)
				{
					int x = (i / 3) % 3;
					int y = i % 3;
					ret[x][y] = (ItemStack) recipe.getData()[i];
				}
				
				return ret;
			}
			else if (recipe.getCraftingType() == IItemMetadataRecipe.CRAFTING)
			{
				return analyseCraftingShaped(recipe.getData());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new ItemStack[][] { { null, null, null }, { null, null, null }, { null, null, null } };
	}
	
	/**
	 * Analyzes a shaped crafting recipe.
	 * 
	 * @param recipe
	 *            the recipe
	 * @return the item stack[][]
	 */
	public static ItemStack[][] analyseCraftingShaped(Object... recipe)
	{
		String s = "";
		int i = 0;
		int j = 0; // Width
		int k = 0; // Height
		
		if (recipe[i] instanceof String[])
		{
			String[] astring = ((String[]) recipe[i++]);
			
			k = astring.length;
			for (int l = 0; l < astring.length; ++l)
			{
				String s1 = astring[l];
				j = s1.length();
				s = s + s1;
			}
		}
		else
		{
			while (recipe[i] instanceof String)
			{
				String s2 = (String) recipe[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}
		
		Map<Character, ItemStack> hashmap = new HashMap<Character, ItemStack>();
		
		for (; i < recipe.length; i += 2)
		{
			Character character = (Character) recipe[i];
			ItemStack itemstack1 = null;
			
			if (recipe[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item) recipe[i + 1]);
			}
			else if (recipe[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block) recipe[i + 1], 1, OreDictionary.WILDCARD_VALUE);
			}
			else if (recipe[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack) recipe[i + 1];
			}
			
			hashmap.put(character, itemstack1);
		}
		
		ItemStack[][] ret = new ItemStack[3][3];
		
		for (int j1 = 0; j1 < j; ++j1)
		{
			for (int k1 = 0; k1 < k; ++k1)
			{
				int i1 = (k1 * j) + j1;
				char c0 = s.charAt(i1 % s.length());
				
				if (hashmap.containsKey(c0))
					ret[k1 % 3][j1 % 3] = hashmap.get(c0).copy();
				else
					ret[k1 % 3][j1 % 3] = null;
			}
		}
		return ret;
	}
}

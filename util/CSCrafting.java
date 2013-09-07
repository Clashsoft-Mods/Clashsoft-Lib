package clashsoft.clashsoftapi.util;

import java.util.HashMap;
import java.util.Map;

import clashsoft.mods.morefood.food.FoodRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.src.ModLoader;
import net.minecraftforge.oredict.OreDictionary;

public class CSCrafting
{
	public static void addCrafting(ItemStack par1ItemStack, Object... par2Objects)
	{
		addCrafting(false, par1ItemStack, par2Objects);
	}
	
	public static void addCrafting(boolean flag, ItemStack par1ItemStack, Object... par2Objects)
	{
		if (flag)
			GameRegistry.addShapelessRecipe(par1ItemStack, par2Objects);
		else
			GameRegistry.addRecipe(par1ItemStack, par2Objects);
	}
	
	/**
	 * Adds a Furnace recipe.
	 * 
	 * @param par1
	 *            Input
	 * @param par2ItemStack
	 *            Output
	 * @param par3
	 *            Experience
	 */
	public static void addSmelting(ItemStack par1ItemStack, ItemStack par2ItemStack, float par3)
	{
		FurnaceRecipes.smelting().addSmelting(par1ItemStack.getItem().itemID, par1ItemStack.getItemDamage(), par2ItemStack, par3);
	}
	
	/**
	 * Adds an Armor-shaped recipe. (Parts: 0 = Helmet, 1 = Chestplate, 2 =
	 * Leggings, 3 = Boots.)
	 * 
	 * @param out
	 *            Output ItemStack
	 * @param input
	 *            Main Crafting Material
	 * @param part
	 *            Armor Type
	 */
	public static void addArmorRecipe(ItemStack out, ItemStack input, int part)
	{
		if (part == 0)
		{
			ModLoader.addRecipe(out, new Object[] { "XXX", "X X", Character.valueOf('X'), input });
		}
		else if (part == 1)
		{
			ModLoader.addRecipe(out, new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), input });
		}
		else if (part == 2)
		{
			ModLoader.addRecipe(out, new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), input });
		}
		else if (part == 3)
		{
			ModLoader.addRecipe(out, new Object[] { "X X", "X X", Character.valueOf('X'), input });
		}
	}
	
	/**
	 * Adds a Tool-shaped recipe. (Parts: 0 = Sword, 1 = Spade, 2 = Pickaxe, 3 =
	 * Axe, 4 = Hoe)
	 * 
	 * @param out
	 *            Output ItemStack
	 * @param input
	 *            Main crafting Material
	 * @param part
	 *            Tool Type
	 */
	public static void addToolRecipe(ItemStack out, ItemStack input, int part)
	{
		if (part == 0)
		{
			ModLoader.addRecipe(out, new Object[] { "X", "X", "|", 'X', input, '|', Item.stick });
		}
		if (part == 1)
		{
			ModLoader.addRecipe(out, new Object[] { "X", "|", "|", 'X', input, '|', Item.stick });
		}
		if (part == 2)
		{
			ModLoader.addRecipe(out, new Object[] { "XXX", " | ", " | ", 'X', input, '|', Item.stick });
		}
		if (part == 3)
		{
			ModLoader.addRecipe(out, new Object[] { "XX ", "X| ", " | ", 'X', input, '|', Item.stick });
		}
		if (part == 4)
		{
			ModLoader.addRecipe(out, new Object[] { "XX", " |", " |", 'X', input, '|', Item.stick });
		}
	}
	
	/**
	 * Removes a recipe from the game
	 * 
	 * @param recipe
	 *            Recipe to remove
	 */
	public static void removeRecipe(Object... recipe)
	{
		addCrafting(new ItemStack(0, 0, 0), recipe);
	}
	
	public static ItemStack registerOre(String name, ItemStack ore)
	{
		OreDictionary.registerOre(name, ore);
		return ore;
	}
	
	private static final ItemStack	FIRE	= new ItemStack(Block.fire, 1, 0), COAL = new ItemStack(Item.coal, 1, 0);
	
	public static ItemStack[][] analyseCrafting(IItemMetadataRecipe r)
	{
		try
		{
			if (r.getCraftingType() == FoodRecipe.FURNACE)
			{
				return new ItemStack[][] { { null, (ItemStack) r.getData()[0], null }, { null, FIRE, null }, { null, COAL, null } };
			}
			else if (r.getCraftingType() == FoodRecipe.CRAFTING_SHAPELESS)
			{
				ItemStack[][] ret = new ItemStack[3][3];
				
				for (int i = 0; i < r.getData().length; i++)
				{
					int x = (i / 3) % 3;
					int y = i % 3;
					ret[x][y] = (ItemStack) r.getData()[i];
				}
				
				return ret;
			}
			else if (r.getCraftingType() == FoodRecipe.CRAFTING)
			{
				return analyseCraftingShaped(r.getData());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new ItemStack[][] { { null, null, null }, { null, null, null }, { null, null, null } };
	}
	
	public static ItemStack[][] analyseCraftingShaped(Object... objects)
	{
		String s = "";
		int i = 0;
		int j = 0; // Width
		int k = 0; // Height
		
		if (objects[i] instanceof String[])
		{
			String[] astring = ((String[]) objects[i++]);
			
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
			while (objects[i] instanceof String)
			{
				String s2 = (String) objects[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}
		
		Map<Character, ItemStack> hashmap = new HashMap<>();
		
		for (; i < objects.length; i += 2)
		{
			Character character = (Character) objects[i];
			ItemStack itemstack1 = null;
			
			if (objects[i + 1] instanceof Item)
			{
				itemstack1 = new ItemStack((Item) objects[i + 1]);
			}
			else if (objects[i + 1] instanceof Block)
			{
				itemstack1 = new ItemStack((Block) objects[i + 1], 1, OreDictionary.WILDCARD_VALUE);
			}
			else if (objects[i + 1] instanceof ItemStack)
			{
				itemstack1 = (ItemStack) objects[i + 1];
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
					ret[k1 % 3][j1 % 3] = ((ItemStack) hashmap.get(c0)).copy();
				else
					ret[k1 % 3][j1 % 3] = null;
			}
		}
		return ret;
	}
}

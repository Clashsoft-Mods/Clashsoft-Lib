package clashsoft.cslib.minecraft.item;

import static clashsoft.cslib.minecraft.stack.CSStacks.stick;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.minecraft.crafting.CSCrafting;
import clashsoft.cslib.minecraft.item.datatools.DataToolSet;
import clashsoft.cslib.minecraft.util.Constants;
import clashsoft.cslib.reflect.CSReflection;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.util.EnumHelper;

/**
 * The Class CSItems.
 * <p>
 * This class adds several utils for adding items.
 * 
 * @author Clashsoft
 */
public class CSItems
{
	public static Map<Item, Item>	replacements	= new HashMap();
	
	/**
	 * Creates a new {@link Item} of the {@link Class Type} {@code type} and the
	 * {@link Object Object[]} {@code args} and then registers it under the name
	 * {@code name}.
	 * 
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param args
	 *            the constructor arguments
	 * @return the item instance
	 */
	public static <T extends Item> T createItem(Class<T> type, String name, Object... args)
	{
		Class[] classes = new Class[args.length + 1];
		
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null)
			{
				classes[i] = args[i].getClass();
			}
			else
			{
				classes[i] = Object.class;
			}
		}
		return createItem(type, name, classes, args);
	}
	
	/**
	 * Creates a new {@link Item} of the {@link Class Type} {@code type} and the
	 * {@link Object Object[]} {@code args} of {@link Class Types}
	 * {@code argsTypes} and then registers it under the name {@code name}.
	 * 
	 * @param type
	 *            the type
	 * @param name
	 *            the name
	 * @param argsTypes
	 *            the types of the arguments
	 * @param args
	 *            the constructor arguments
	 * @return the item instance
	 */
	public static <T extends Item> T createItem(Class<T> type, String name, Class[] argsTypes, Object... args)
	{
		T item = null;
		
		try
		{
			Constructor<T> c = type.getConstructor(argsTypes);
			if (c != null)
			{
				item = c.newInstance(args);
			}
		}
		catch (Throwable ex)
		{
			throw new RuntimeException(ex);
		}
		
		return item;
	}
	
	/**
	 * Registers all {@link Item}s from the given {@link Class} {@code mod}.
	 * 
	 * @param mod
	 *            the mod class
	 */
	public static void addAllItems(Class mod)
	{
		Item[] items = CSReflection.getStaticObjects(mod, Item.class, true);
		
		for (Item item : items)
		{
			addItem(item);
		}
	}
	
	/**
	 * Registers the given {@link Item} {@code item}. This uses the unlocalized
	 * name of the item to compute a registry name and then calls
	 * {@link CSItem#addItem(Item, String)}.
	 * 
	 * @param item
	 *            the item
	 */
	public static void addItem(Item item)
	{
		String name = item.getUnlocalizedName();
		if (name.startsWith("item."))
		{
			name = name.substring(5);
		}
		addItem(item, name);
	}
	
	/**
	 * Adds an Item without a recipe. Internally calls
	 * {@link CSItems#addItem(Item, String, String)} and uses the current mod id
	 * as the {@code modid} parameter. The current mod id is received using
	 * {@link Loader#activeModContainer()}.{@link ModContainer#getModId()}.
	 * 
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 */
	public static void addItem(Item item, String name)
	{
		String activeModID = Loader.instance().activeModContainer().getModId();
		addItem(item, name, activeModID.toLowerCase());
	}
	
	/**
	 * Adds an Item without a recipe. The item is registered with the specified
	 * modid as a domain.
	 * 
	 * @param item
	 * @param name
	 * @param modid
	 */
	public static void addItem(Item item, String name, String modid)
	{
		name = clashsoft.cslib.util.CSString.identifier(name);
		item.setUnlocalizedName(name);
		GameRegistry.registerItem(item, name, modid);
	}
	
	/**
	 * Adds an Item with a shaped recipe.
	 * 
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 * @param craftingAmount
	 *            the crafting amount
	 * @param recipe
	 *            the recipe
	 */
	public static void addItemWithRecipe(Item item, String name, int craftingAmount, Object... recipe)
	{
		addItem(item, name);
		CSCrafting.addRecipe(new ItemStack(item, craftingAmount), recipe);
	}
	
	/**
	 * Adds an Item with a shapeless recipe.
	 * 
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 * @param craftingAmount
	 *            the crafting amount
	 * @param recipe
	 *            the recipe
	 */
	public static void addItemWithShapelessRecipe(Item item, String name, int craftingAmount, Object... recipe)
	{
		addItem(item, name);
		CSCrafting.addShapelessRecipe(new ItemStack(item, craftingAmount), recipe);
	}
	
	/**
	 * Adds an armor item with a shaped recipe.
	 * 
	 * @see CSCrafting#addArmorRecipe(ItemStack, ItemStack, int)
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 * @param material
	 *            the crafting material
	 * @param stick
	 *            the stick
	 * @param type
	 *            the type
	 */
	public static void addArmor(Item item, String name, ItemStack material, int type)
	{
		addItem(item, name);
		CSCrafting.addArmorRecipe(new ItemStack(item), material, type);
	}
	
	/**
	 * Adds a tool with automatic shaped recipe.
	 * 
	 * @see CSItems#addTool(Item, String, ItemStack, ItemStack, int)
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 * @param material
	 *            the crafting material
	 * @param type
	 *            the type
	 */
	public static void addTool(Item item, String name, ItemStack material, int type)
	{
		addTool(item, name, material, stick, type);
	}
	
	/**
	 * Adds a tool with automatic shaped recipe.
	 * 
	 * @see CSCrafting#addToolRecipe(ItemStack, ItemStack, int)
	 * @param item
	 *            the item
	 * @param name
	 *            the name
	 * @param material
	 *            the crafting material
	 * @param stick
	 *            the stick
	 * @param type
	 *            the type
	 */
	public static void addTool(Item item, String name, ItemStack material, ItemStack stick, int type)
	{
		addItem(item, name);
		CSCrafting.addToolRecipe(new ItemStack(item), material, stick, type);
	}
	
	/**
	 * Adds a tool material, can also be applied to a tool set.
	 * 
	 * @param name
	 *            the name
	 * @param harvestLevel
	 *            the harvest level
	 * @param maxUses
	 *            the max uses
	 * @param efficiency
	 *            the efficiency
	 * @param damage
	 *            the damage
	 * @param enchantability
	 *            the enchantability
	 * @param color
	 *            the color
	 * @param recipe
	 *            the recipe
	 * @param dataToolSet
	 *            the data tool set
	 * @return the enum tool material
	 */
	public static ToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, int color, ItemStack recipe, DataToolSet dataToolSet)
	{
		ToolMaterial toolMaterial = EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability);
		if (dataToolSet != null)
		{
			dataToolSet.registerToolMaterial(toolMaterial, name);
		}
		return toolMaterial;
	}
	
	/**
	 * Replaces the given vanilla {@link Item} {@code item} with the
	 * {@code newItem}.
	 * 
	 * @param item
	 *            the item to replace
	 * @param newItem
	 *            the new item
	 * @return true, if successful
	 */
	public static boolean replaceItem(Item item, Item newItem)
	{
		long now = System.currentTimeMillis();
		try
		{
			for (Field field : Items.class.getDeclaredFields())
			{
				if (Item.class.isAssignableFrom(field.getType()))
				{
					Item item1 = (Item) field.get(null);
					if (item1 == item)
					{
						FMLControlledNamespacedRegistry<Item> registry = GameData.getItemRegistry();
						String registryName = registry.getNameForObject(item1);
						int id = Item.getIdFromItem(item1);
						
						// Set field
						CSReflection.setModifier(field, Modifier.FINAL, false);
						field.set(null, newItem);
						
						// Replace registry entry
						CSReflection.invoke(FMLControlledNamespacedRegistry.class, registry, new Object[] { id, registryName, newItem }, "addObjectRaw");
						
						// Replace stat list entries
						StatCrafting stat = (StatCrafting) StatList.objectBreakStats[id];
						if (stat != null)
						{
							CSReflection.setValue(StatCrafting.class, stat, newItem, Constants.STATCRAFTING_ITEM_FIELD);
						}
						stat = (StatCrafting) StatList.objectCraftStats[id];
						if (stat != null)
						{
							CSReflection.setValue(StatCrafting.class, stat, newItem, Constants.STATCRAFTING_ITEM_FIELD);
						}
						stat = (StatCrafting) StatList.objectUseStats[id];
						if (stat != null)
						{
							CSReflection.setValue(StatCrafting.class, stat, newItem, Constants.STATCRAFTING_ITEM_FIELD);
						}
						
						// Replace Crafting Recipes
						replacements.put(item, newItem);
						
						now = System.currentTimeMillis() - now;
						CSLog.info("Replace Item : %s (%s) with %s, took %d ms", new Object[] { field.getName(), item1.getClass().getSimpleName(), newItem.getClass().getSimpleName(), now });
						
						return true;
					}
				}
			}
		}
		catch (Exception e)
		{
			CSLog.error(e);
		}
		return false;
	}
	
	public static void setItem(ItemStack stack, Item item)
	{
		CSReflection.setValue(ItemStack.class, stack, item, Constants.ITEMSTACK_ITEM_FIELD);
	}
	
	private static boolean applyReplacement(ItemStack stack)
	{
		if (stack != null)
		{
			Item item = stack.getItem();
			Item newItem = replacements.get(item);
			if (item != newItem)
			{
				setItem(stack, newItem);
				return true;
			}
		}
		return false;
	}
	
	public static void replaceCraftingRecipes()
	{
		long now = System.currentTimeMillis();
		int count = 0;
		
		for (IRecipe recipe : CSCrafting.RECIPES)
		{
			ItemStack output = recipe.getRecipeOutput();
			if (applyReplacement(output))
			{
				count++;
			}
			
			if (recipe instanceof ShapedRecipes)
			{
				ItemStack[] recipeItems = ((ShapedRecipes) recipe).recipeItems;
				for (ItemStack stack : recipeItems)
				{
					if (applyReplacement(stack))
					{
						count++;
					}
				}
			}
			else if (recipe instanceof ShapelessRecipes)
			{
				List<ItemStack> recipeItems = ((ShapelessRecipes) recipe).recipeItems;
				for (ItemStack stack : recipeItems)
				{
					if (applyReplacement(stack))
					{
						count++;
					}
				}
			}
		}
		
		now = System.currentTimeMillis() - now;
		CSLog.info("Replaced " + count + " item references from " + CSCrafting.RECIPES.size() + " recipes, took " + now + "ms");
	}
}

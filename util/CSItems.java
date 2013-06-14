package clashsoft.clashsoftapi.util;

//import clashsoft.mods.combinationcraft.EnumToolMaterial2;
import clashsoft.clashsoftapi.ItemDataTool;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;
import net.minecraftforge.common.EnumHelper;

public class CSItems
{
	/**
	 * Adds an Item without recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 */
	public static void addItem(Item par1Item, String par2)
	{
		ModLoader.addName(par1Item, par2);
	}
	
	/**
	 * Adds an Item with recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5ArrayOfObj
	 */
	public static void addItemWithRecipe(Item par1Item, int par2, String par3, int par4, Object[] par5ArrayOfObj)
	{
		addItem(par1Item, par3);
		ModLoader.addRecipe(new ItemStack(par1Item, par4), par5ArrayOfObj);
	}
	
	/**
	 * Adds an Item with shapeless recipe.
	 * @param par1Item
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param par5ArrayOfObj
	 */
	public static void addItemWithShapelessRecipe(Item par1Item, int par2, String par3, int par4, Object[] par5ArrayOfObj)
	{
		addItem(par1Item, par3);
		ModLoader.addShapelessRecipe(new ItemStack(par1Item, par4), par5ArrayOfObj);
	}
	
	public static void addTool(Item par1Item, String par2, ItemStack par3ItemStack, int par4)
	{
		addItem(par1Item, par2);
		CSCrafting.addToolRecipe(new ItemStack(par1Item), par3ItemStack, par4);
	}
	
	public static EnumToolMaterial addToolMaterial(String name, int harvestLevel, int maxUses, float efficiency, int damage, int enchantability, int color, ItemStack recipe, boolean dataTool)
	{
		try
		{
			//EnumToolMaterial2 material2 = new EnumToolMaterial2(name, harvestLevel, maxUses, efficiency, damage, enchantability, color, recipe).register();
		}
		catch (Exception ex)
		{
			System.out.println("CombinationCraft not installed");
		}
		EnumToolMaterial var1 = EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, damage, enchantability);
		if (dataTool)
			ItemDataTool.registerMaterial(var1, name);
		return var1;
	}
}

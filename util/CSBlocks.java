package clashsoft.clashsoftapi.util;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * The Class CSBlocks.
 * <p>
 * This class adds several methods for block adding.
 */
public class CSBlocks
{
	
	/**
	 * Registers a Block and its name.
	 * 
	 * @param par1Block
	 *            the par1 block
	 * @param par2
	 *            the par2
	 */
	public static void addBlock(Block par1Block, String par2)
	{
		GameRegistry.registerBlock(par1Block, par2);
		LanguageRegistry.addName(par1Block, par2);
	}
	
	/**
	 * Registers a Block and its name to a block item.
	 * 
	 * @param block
	 *            the block
	 * @param itemClass
	 *            the item class
	 * @param name
	 *            the name
	 */
	public static void addBlock(Block block, Class<? extends ItemBlock> itemClass, String name)
	{
		GameRegistry.registerBlock(block, itemClass, name);
		LanguageRegistry.addName(block, name);
	}
	
	/**
	 * Register a block with its name and an recipe for it.
	 * 
	 * @param block
	 *            the block
	 * @param name
	 *            the name
	 * @param craftingAmount
	 *            the crafting amount
	 * @param recipe
	 *            the recipe
	 */
	public static void addBlockWithRecipe(Block block, String name, int craftingAmount, Object... recipe)
	{
		addBlock(block, name);
		CSCrafting.addCrafting(new ItemStack(block, craftingAmount), recipe);
	}
}

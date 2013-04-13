package clashsoft.clashsoftapi;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.src.*;

public class ClashsoftBlocks
{	
	/**
	 * Registers a Block and its name
	 * @param par1Block
	 * @param par2
	 */
	public static void addBlock(Block par1Block, String par2)
	{
		ModLoader.registerBlock(par1Block);
		ModLoader.addName(par1Block, par2);
	}

	/**
	 * Register a block with its name and an recipe for it
	 * @param par1Block
	 * @param par2
	 * @param par3
	 * @param par4ArrayOfObj
	 */
	public static void addBlockWithRecipe(Block par1Block, String par2, int par3, Object[] par4ArrayOfObj)
	{
		addBlock(par1Block, par2);
		ModLoader.addRecipe(new ItemStack(par1Block, par3), par4ArrayOfObj);
	}
}

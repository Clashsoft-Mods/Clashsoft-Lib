package clashsoft.cslib.crafting.manager;

import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.custom_hash.TObjectFloatCustomHashMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import clashsoft.cslib.stack.ItemStackHash;

/**
 * A crafting recipe manager. This manager functions like the furnace crafting
 * manager for one-to-one recipes and does support the experience output.
 * 
 * @author Clashsoft
 */
public class ExperienceRecipeManager extends SimpleRecipeManager
{
	private TObjectFloatMap<ItemStack>	experienceMap	= new TObjectFloatCustomHashMap(ItemStackHash.itemInstance);
	
	@Override
	public void addRecipe(ItemStack input, ItemStack output)
	{
		this.addRecipe(input, output, 0F);
	}
	
	public void addRecipe(Block block, ItemStack output, float exp)
	{
		this.addRecipe(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE), output, exp);
	}
	
	public void addRecipe(Item item, ItemStack output, float exp)
	{
		this.addRecipe(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), output, exp);
	}
	
	public void addRecipe(ItemStack input, ItemStack output, float exp)
	{
		super.addRecipe(input, output);
		this.experienceMap.put(output.copy(), exp);
	}
	
	public float getExp(ItemStack input)
	{
		float ret = input.getItem().getSmeltingExperience(input);
		if (ret != -1.0F)
			return ret;
		
		return this.experienceMap.get(input);
	}
}

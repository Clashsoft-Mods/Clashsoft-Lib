package clashsoft.cslib.item.datatools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import dyvil.reflect.ReflectUtils;

public class ItemDataSpade extends ItemDataTool
{
	public static final float	baseDamage				= 1F;
	public static final Set		blocksEffectiveAgainst	= ReflectUtils.getStaticValue(ItemSpade.class, 0);
	
	public ItemDataSpade(ToolMaterial toolMaterial)
	{
		super(baseDamage, toolMaterial, blocksEffectiveAgainst, "Shovel");
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack)
	{
		return block == Blocks.snow || block == Blocks.snow_layer;
	}
}

package clashsoft.cslib.item.datatools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import dyvil.reflect.ReflectUtils;

public class ItemDataAxe extends ItemDataTool
{
	public static final float		baseDamage				= 3F;
	public static final Set<Block>	blocksEffectiveAgainst	= ReflectUtils.getStaticValue(ItemAxe.class, 0);
	
	public ItemDataAxe(ToolMaterial toolMaterial)
	{
		super(baseDamage, toolMaterial, blocksEffectiveAgainst, "Axe");
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		Material material = state.getBlock().getMaterial();
		if (isEfficientOnMaterial(material))
			return this.efficiencyOnProperMaterial;
		return super.getDigSpeed(stack, state);
	}
	
	public static boolean isEfficientOnMaterial(Material material)
	{
		return material == Material.wood || material == Material.plants || material == Material.vine;
	}
}

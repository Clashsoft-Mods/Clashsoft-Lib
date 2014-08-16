package clashsoft.cslib.minecraft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import clashsoft.cslib.reflect.CSReflection;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatCrafting;

public class Constants
{
	public static final int		COLOR_RED						= 0xFF0000;
	public static final int		COLOR_YELLOW					= 0xFFDF00;
	public static final int		COLOR_GREEN						= 0x00DF00;
	public static final int		COLOR_BLUE						= 0x007FFF;
	public static final int		COLOR_LIGHT_BLUE				= 0x00DFFF;
	
	public static final Field	FIELD_ITEMSTACK_ITEM			= CSReflection.getField(ItemStack.class, 3);
	public static final Field	FIELD_ITEMBLOCK_BLOCK			= CSReflection.getField(ItemBlock.class, 0);
	public static final Field	FIELD_STATCRAFTING_ITEM			= CSReflection.getField(StatCrafting.class, 0);
	public static final Field	FIELD_ITEMARMOR_ARMORTYPE		= CSReflection.getField(ItemArmor.class, 4);
	public static final Field	FIELD_ITEMARMOR_DAMAGEREDUCTION	= CSReflection.getField(ItemArmor.class, 5);
	
	public static final Method	METHOD_REGISTRY_ADDOBJECTRAW	= CSReflection.getMethod(FMLControlledNamespacedRegistry.class, "addObjectRaw", new Class[] { int.class, String.class, Object.class });
}

package clashsoft.cslib.minecraft.enchantment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import clashsoft.cslib.minecraft.util.CSRegistry;
import clashsoft.cslib.reflect.CSReflection;
import clashsoft.cslib.util.CSLog;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Potion;

public class EnchantmentRegistry extends CSRegistry
{
	public static EnchantmentRegistry instance = new EnchantmentRegistry();
	
	static
	{
		for (Enchantment ench : Enchantment.enchantmentsList)
		{
			if (ench != null)
				add(ench);
		}
	}
	
	@Override
	public int getFreeID()
	{
		int id = super.getFreeID();
		int len = Potion.potionTypes.length;
		
		if (id > len)
		{
			expandEnchantmentList(len << 1);
			return len;
		}
		return id;
	}
	
	public static int getID(String name)
	{
		return instance.getIDByName(name);
	}
	
	public static void add(Enchantment ench)
	{
		add(ench, ench.effectId, ench.getName());
	}
	
	public static void add(Enchantment ench, int id, String name)
	{
		instance.addObject(id, name, ench);
	}
	
	public static void expandEnchantmentList(int size)
	{
		Enchantment[] list = Enchantment.enchantmentsList;
		if (list.length < size)
		{
			try
			{
				Field f = CSReflection.getField(Enchantment.class, 0);
				Enchantment[] enchantmentTypes = new Enchantment[size];
				
				System.arraycopy(list, 0, enchantmentTypes, 0, list.length);
				CSReflection.setModifier(f, Modifier.FINAL, false);
				f.set(null, enchantmentTypes);
			}
			catch (Exception e)
			{
				CSLog.error(e);
			}
		}
	}
}

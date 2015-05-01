package clashsoft.cslib.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.util.CSRegistry;
import dyvil.reflect.ReflectUtils;

public class PotionRegistry extends CSRegistry
{
	public static PotionRegistry	instance	= new PotionRegistry();
	
	static
	{
		for (Potion potion : Potion.potionTypes)
		{
			if (potion != null)
			{
				add(potion);
			}
		}
		
		expandPotionList(64);
	}
	
	@Override
	public int getFreeID()
	{
		int id = super.getFreeID();
		int len = Potion.potionTypes.length;
		
		if (id > len)
		{
			expandPotionList(len << 1);
			return len;
		}
		return id;
	}
	
	public static int getID(String name)
	{
		return instance.getIDByName(name);
	}
	
	public static void add(Potion potion)
	{
		add(potion, potion.id, potion.getName());
	}
	
	public static void add(Potion potion, int id, String name)
	{
		instance.register(id, name, potion);
	}
	
	public static void expandPotionList(int size)
	{
		Potion[] list = Potion.potionTypes;
		if (list.length < size)
		{
			try
			{
				Field f = ReflectUtils.getField(Potion.class, 0);
				Potion[] potionTypes = new Potion[size];
				
				System.arraycopy(list, 0, potionTypes, 0, list.length);
				ReflectUtils.setModifier(f, Modifier.FINAL, false);
				f.set(null, potionTypes);
			}
			catch (Exception e)
			{
				CSLog.error(e);
			}
		}
	}
}

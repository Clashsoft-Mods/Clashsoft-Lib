package clashsoft.clashsoftapi.util;

import java.lang.reflect.Field;

@Deprecated
public class CSReflection
{
	public static Object getValue(Class clazz, String... fieldNames)
	{
		Field f = getField(clazz, fieldNames);
		
		if (f == null) throw new NullPointerException("Field not found");
		
		return null;
	}
	
	public static Field getField(Class clazz, String... fieldNames)
	{
		Field[] fields = clazz.getFields();
		
		for (Field f : fields)
		{
			for (String s : fieldNames)
			{
				if (f.getName().equals(s))
					return f;
			}
		}
		
		return null;
	}
}

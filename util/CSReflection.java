package clashsoft.cslib.util;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CSReflection
{
	public static <T> int getFieldID(Class<? super T> clazz, String... fieldNames) throws NoSuchFieldException
	{
		Field[] fields = clazz.getFields();
		for (String fieldName : fieldNames)
		{
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				if (fieldName.equals(field.getName()))
					return i;
			}
		}
		new NoSuchFieldException("Field not found! (Class: " + clazz + "; Expected field names: " + Arrays.toString(fieldNames)).printStackTrace();
		return -1;
	}
	
	public static <T, R> R getStaticValue(Class<? super T> clazz, String... fieldNames)
	{
		return getValue(clazz, null, fieldNames);
	}
	
	public static <T, R> R getValue(T instance, String... fieldNames)
	{
		return (R) getValue((Class<T>) instance.getClass(), instance, fieldNames);
	}
	
	public static <T, R> R getValue(Class<? super T> clazz, T instance, String... fieldNames)
	{
		try
		{
			Field f = getField(clazz, fieldNames);
			f.setAccessible(true);
			return (R) f.get(instance);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static <T, V> void setStaticValue(Class<? super T> clazz, String... fieldNames)
	{
		setValue(clazz, null, fieldNames);
	}
	
	public static <T, V> void setValue(T instance, V value, String... fieldNames)
	{
		setValue(instance.getClass(), value, fieldNames);
	}
	
	public static <T, V> void setValue(Class<? super T> clazz, T instance, V value, String... fieldNames)
	{
		try
		{
			Field f = getField(clazz, fieldNames);
			f.setAccessible(true);
			f.set(instance, value);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static Field getField(Class clazz, String... fieldNames) throws NoSuchFieldException
	{
		return clazz.getDeclaredFields()[getFieldID(clazz, fieldNames)];
	}
}

package clashsoft.cslib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clashsoft.cslib.logging.CSLog;

/**
 * The class CSReflection.
 * <p>
 * This class adds several utils for "hacking" into the JVM, also known as
 * Reflection.
 * 
 * @author Clashsoft
 */
public class CSReflection
{
	private static Field	modifiersField;
	
	static
	{
		try
		{
			modifiersField = Field.class.getDeclaredField("modifiers");
			// Makes the 'modifiers' field of the class Field accessible
			modifiersField.setAccessible(true);
		}
		catch (ReflectiveOperationException ignored)
		{
		}
	}
	
	public static void setModifier(Field field, int mod, boolean flag)
	{
		try
		{
			field.setAccessible(true);
			int modifiers = modifiersField.getInt(field);
			if (flag)
			{
				modifiers |= mod;
			}
			else
			{
				modifiers &= ~mod;
			}
			modifiersField.setInt(field, modifiers);
		}
		catch (ReflectiveOperationException ex)
		{
			CSLog.error(ex);
		}
	}
	
	// Caller-sensitive
	
	public static Class getCallerClass()
	{
		try
		{
			return Class.forName(getCallerClassName());
		}
		catch (ClassNotFoundException ex)
		{
			CSLog.error(ex);
			return null;
		}
	}
	
	public static String getCallerClassName()
	{
		return getCaller().getClassName();
	}
	
	public static StackTraceElement getCaller()
	{
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		String callerClassName = null;
		
		for (int i = 1; i < stElements.length; i++)
		{
			StackTraceElement ste = stElements[i];
			String className = ste.getClassName();
			
			if (!CSReflection.class.getName().equals(className) && !className.startsWith("java.lang.Thread"))
			{
				if (callerClassName == null)
				{
					callerClassName = className;
				}
				else if (!callerClassName.equals(className))
				{
					return ste;
				}
			}
		}
		
		return null;
	}
	
	// Methods
	
	public static Method getMethod(Class clazz, Object object)
	{
		if (object == null)
		{
			throw new NullPointerException("Cannot get null method!");
		}
		Class c = object.getClass();
		if (c == Method.class)
		{
			return (Method) object;
		}
		else if (c == int.class)
		{
			return getMethod(clazz, (int) object);
		}
		else if (c == Object[].class)
		{
			Object[] aobject = (Object[]) object;
			if (aobject.length == 2)
			{
				if (aobject[0] instanceof String)
				{
					return getMethod(clazz, (String) aobject[0], (Class[]) aobject[1]);
				}
				else if (aobject[0] instanceof String[])
				{
					return getMethod(clazz, (String[]) aobject[0], (Class[]) aobject[1]);
				}
			}
		}
		CSLog.error("Unable to get method specified with " + object);
		return null;
	}
	
	public static Method getMethod(Class clazz, String methodName, Class[] parameterTypes)
	{
		try
		{
			Method m = clazz.getDeclaredMethod(methodName, parameterTypes);
			if (m != null)
			{
				return m;
			}
		}
		catch (NoSuchMethodException ex)
		{
		}
		catch (SecurityException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public static Method getMethod(Class clazz, String[] methodNames, Class[] parameterTypes)
	{
		for (String methodName : methodNames)
		{
			Method m = getMethod(clazz, methodName, parameterTypes);
			if (m != null)
			{
				return m;
			}
		}
		CSLog.error(new NoSuchMethodException("Method not found! (Class: " + clazz + "; Expected field names: " + Arrays.toString(methodNames)));
		return null;
	}
	
	public static Method getMethod(Class clazz, int methodID)
	{
		return clazz.getDeclaredMethods()[methodID];
	}
	
	// Method invocation
	
	// Reference
	
	public static <T, R> R invokeStatic(Class<? super T> clazz, Object[] args, Object method)
	{
		return invoke(clazz, null, args, method);
	}
	
	public static <T, R> R invoke(T instance, Object[] args, Object method)
	{
		return invoke((Class<T>) instance.getClass(), instance, args, method);
	}
	
	public static <T, R> R invoke(Class<? super T> clazz, T instance, Object[] args, Object method)
	{
		Method m = getMethod(clazz, method);
		return invoke(m, instance, args);
	}
	
	// Method ID
	
	public static <T, R> R invokeStatic(Class<? super T> clazz, Object[] args, int methodID)
	{
		return invoke(clazz, null, args, methodID);
	}
	
	public static <T, R> R invoke(T instance, Object[] args, int methodID)
	{
		return invoke((Class<T>) instance.getClass(), instance, args, methodID);
	}
	
	public static <T, R> R invoke(Class<? super T> clazz, T instance, Object[] args, int methodID)
	{
		Method m = getMethod(clazz, methodID);
		return invoke(m, instance, args);
	}
	
	// Raw Invokation
	
	public static <T, R> R invoke(Method method, Object instance, Object[] args)
	{
		try
		{
			method.setAccessible(true);
			return (R) method.invoke(instance, args);
		}
		catch (Exception ex)
		{
			CSLog.error(ex);
			return null;
		}
	}
	
	// Fields
	
	public static <T> T[] getStaticObjects(Class clazz, Class<T> fieldType, boolean subtypes)
	{
		return getObjects(clazz, null, fieldType, subtypes);
	}
	
	public static <T> T[] getObjects(Class clazz, Object instance, Class<T> fieldType, boolean subtypes)
	{
		List list = new ArrayList();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields)
		{
			try
			{
				Class c = field.getType();
				Object o = field.get(instance);
				if (c == fieldType || subtypes && fieldType.isAssignableFrom(c))
				{
					list.add(o);
				}
			}
			catch (Exception ex)
			{
			}
		}
		
		return (T[]) list.toArray();
	}
	
	public static Field getField(Class clazz, String... fieldNames)
	{
		Field[] fields = clazz.getDeclaredFields();
		for (String fieldName : fieldNames)
		{
			for (Field field : fields)
			{
				if (fieldName.equals(field.getName()))
				{
					return field;
				}
			}
		}
		CSLog.error(new NoSuchFieldException("Field not found! (Class: " + clazz + "; Expected field names: " + Arrays.toString(fieldNames)));
		return null;
	}
	
	public static int getFieldID(Class clazz, String... fieldNames)
	{
		Field[] fields = clazz.getDeclaredFields();
		for (String fieldName : fieldNames)
		{
			for (int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				if (fieldName.equals(field.getName()))
				{
					return i;
				}
			}
		}
		CSLog.error(new NoSuchFieldException("Field not found! (Class: " + clazz + "; Expected field names: " + Arrays.toString(fieldNames)));
		return -1;
	}
	
	public static String getFieldName(Class clazz, int fieldID)
	{
		return getField(clazz, fieldID).getName();
	}
	
	public static Field getField(Class clazz, int fieldID)
	{
		return clazz.getDeclaredFields()[fieldID];
	}
	
	// Field getters
	
	// Reference
	
	public static <T, R> R getStaticValue(Class<? super T> clazz, String... fieldNames)
	{
		return getValue(clazz, null, fieldNames);
	}
	
	public static <T, R> R getValue(T instance, String... fieldNames)
	{
		return getValue((Class<T>) instance.getClass(), instance, fieldNames);
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
			CSLog.error(ex);
			return null;
		}
	}
	
	// Field ID
	
	public static <T, R> R getStaticValue(Class<? super T> clazz, int fieldID)
	{
		return getValue(clazz, null, fieldID);
	}
	
	public static <T, R> R getValue(T instance, int fieldID)
	{
		return getValue((Class<? super T>) instance.getClass(), instance, fieldID);
	}
	
	public static <T, R> R getValue(Class<? super T> clazz, T instance, int fieldID)
	{
		Field f = getField(clazz, fieldID);
		return getValue(f, instance);
	}
	
	// Raw Getter
	
	public static <T, R> R getValue(Field field, Object instance)
	{
		try
		{
			field.setAccessible(true);
			return (R) field.get(instance);
		}
		catch (Exception ex)
		{
			CSLog.error(ex);
			return null;
		}
	}
	
	// Field setters
	
	// Reference
	
	public static <T, V> void setStaticValue(Class<? super T> clazz, V value, String... fieldNames)
	{
		setValue(clazz, null, value, fieldNames);
	}
	
	public static <T, V> void setValue(T instance, V value, String... fieldNames)
	{
		setValue((Class<? super T>) instance.getClass(), instance, value, fieldNames);
	}
	
	public static <T, V> void setValue(Class<? super T> clazz, T instance, V value, String... fieldNames)
	{
		Field f = getField(clazz, fieldNames);
		setValue(f, instance, value);
	}
	
	// Field ID
	
	public static <T, V> void setStaticValue(Class<? super T> clazz, V value, int fieldID)
	{
		setValue(clazz, null, value, fieldID);
	}
	
	public static <T, V> void setValue(T instance, V value, int fieldID)
	{
		setValue((Class<? super T>) instance.getClass(), instance, value, fieldID);
	}
	
	public static <T, V> void setValue(Class<? super T> clazz, T instance, V value, int fieldID)
	{
		Field f = getField(clazz, fieldID);
		setValue(f, instance, value);
	}
	
	// Raw Setter
	
	public static <T, V> void setValue(Field field, T instance, V value)
	{
		try
		{
			field.setAccessible(true);
			field.set(instance, value);
		}
		catch (Exception ex)
		{
			CSLog.error(ex);
		}
	}
	
	// Instances
	
	public static <T> T createInstance(String className)
	{
		try
		{
			Class c = Class.forName(className);
			return (T) c.newInstance();
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	public static <T> T createInstance(Class<T> c)
	{
		try
		{
			return c.newInstance();
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	public static <T> T createInstance(Class<T> c, Object... parameters)
	{
		Class[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i] != null)
			{
				parameterTypes[i] = parameters[i].getClass();
			}
		}
		
		return createInstance(c, parameterTypes, parameters);
	}
	
	public static <T> T createInstance(Class<T> c, Class[] parameterTypes, Object... parameters)
	{
		try
		{
			Constructor<T> constructor = c.getConstructor(parameterTypes);
			return constructor.newInstance(parameters);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
}

package clashsoft.clashsoftapi.util.reflect;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class ImmutableObjectFactory
{
	public static Map<Class, Map<Object[], Object>>	objects	= new HashMap<>();
	
	public static <T> T createObject(Class<T> clazz, Object... args)
	{
		Class[] classes = new Class[args.length];
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null)
				classes[i] = args[i].getClass();
			else
				classes[i] = Object.class;
		}
		return createObject(clazz, classes, args);
	}
	
	public static <T> T createObject(Class<T> clazz, Class[] argsTypes, Object... args)
	{
		Map<Object[], Object> map = objects.get(clazz);
		if (map != null)
		{
			Object obj = map.get(args);
			if (obj != null)
				return cast(clazz, obj);
		}
		else
		{
			try
			{
				Constructor c = clazz.getConstructor(argsTypes);
				Object obj = c.newInstance(args);
				addToMap(clazz, args, obj);
				return cast(clazz, obj);
			}
			catch (Exception ex)
			{
			}
		}
		return null;
	}
	
	public static <T> T cast(Class<T> clazz, Object object)
	{
		return (T) object;
	}
	
	public static final void checkImmutable(Class clazz)
	{
		if (!isImmutable(clazz))
			throw new IllegalArgumentException(clazz + " is not immutable! Sign with @Immutable annotation");
	}
	
	public static boolean isImmutable(Class clazz)
	{
		if (clazz == null)
			return false;
		if (clazz.isPrimitive())
			return true;
		if (clazz.isAssignableFrom(Number.class) || clazz.isAssignableFrom(String.class))
			return true;
		if (clazz.isAnnotationPresent(Immutable.class))
			return true;
		if (clazz.getSimpleName().toLowerCase().startsWith("immutable"))
			return true;
		return false;
	}

	public static void addToMap(Class clazz, Object[] args, Object object)
	{
		Map<Object[], Object> map = objects.get(clazz);
		if (map == null)
			map = new HashMap<>();
		map.put(args, object);
		objects.put(clazz, map);
	}
}

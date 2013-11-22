package clashsoft.cslib.util;

import java.lang.reflect.Array;
import java.util.*;

import net.minecraft.util.MathHelper;

/**
 * The Class CSArrays.
 * <p>
 * This class adds several array util methods
 */
public class CSArrays
{
	public static <T> T[] create(T... array)
	{
		return array;
	}
	
	public static <T> T[] create(int size, Class<T> type)
	{
		return (T[]) Array.newInstance(type, size);
	}
	
	public static <T> T[] create(int size, T... array)
	{
		T[] array1 = (T[]) create(array.getClass().getComponentType(), size);
		System.arraycopy(array, 0, array1, 0, array.length);
		return array1;
	}
	
	public static <T> Class<T> getComponentType(T[] array)
	{
		return (Class<T>) array.getClass().getComponentType();
	}
	
	public static <T> Class<T> getComponentTypeSave(T[] array)
	{
		Class<T> ret = getComponentType(array);
		while(ret != ret.getComponentType())
			ret = (Class<T>) ret.getComponentType();
		return ret;
	}
	
	public static <T> T[][] split(T[] array, int maxLength)
	{
		Class clazz = array.getClass().getComponentType();
		int arrays = MathHelper.ceiling_float_int(((float) array.length) / ((float) maxLength));
		T[][] ret = (T[][]) Array.newInstance(clazz, arrays, maxLength);
		
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = (T[]) Array.newInstance(clazz, maxLength);
			for (int j = 0; j < maxLength && (j + (i * maxLength)) < array.length; j++)
				ret[i][j] = array[j + (i * maxLength)];
		}
		return ret;
	}
	
	@Deprecated
	public static String[] addToAll(String[] array, String prefix, String postfix)
	{
		return concatAll(array, prefix, postfix);
	}
	
	public static String[] concatAll(String[] array, String prefix, String postfix)
	{
		String[] ret = new String[array.length];
		for (int i = 0; i < ret.length; i++)
		{
			if (array[i] != null)
				ret[i] = prefix + array[i] + postfix;
			else
				ret[i] = prefix + postfix;
		}
		return ret;
	}
	
	public static String[] caseAll(String[] stringArray, int mode)
	{
		String[] result = new String[stringArray.length];
		for (int i = 0; i < stringArray.length; i++)
		{
			if (stringArray[i] != null)
				result[i] = CSString.caseString(stringArray[i], mode);
			else
				result[i] = "";
		}
		return result;
	}
	
	@Deprecated
	public static <T> T[] combine(T[] array1, T[] array2)
	{
		return concat(array1, array2);
	}
	
	public static <T> T[] concat(T[] array1, T... array2)
	{
		return concat(array1, array2);
	}
	
	public static <T> T[] concat(T[]... arrays)
	{
		List<T> list = asList(arrays[0]);
		for (int i = 1; i < arrays.length; i++)
			list.addAll(asList(arrays[i]));
		return fromList(getComponentType(arrays), list);
	}
	
	public static <T> T[] removeDuplicates(T... array)
	{
		if (array != null && array.length > 0)
		{
			Collection<T> result = new ArrayList<T>(array.length);
			for (T t1 : array)
			{
				boolean duplicate = false;
				for (T t2 : result)
				{
					if (Objects.equals(t1, t2))
						duplicate = true;
					break;
				}
				if (!duplicate)
					result.add(t1);
			}
			return fromList(array.getClass().getComponentType(), result);
		}
		return array;
		
	}
	
	public static <T> List<T> asList(T... array)
	{
		return Arrays.asList(array);
	}
	
	public static <T> T[] fromList(Class type, Collection<T> collection)
	{
		T[] array = (T[]) Array.newInstance(type, collection.size());
		collection.toArray(array);
		return array;
	}
	
	public static <T> int indexOf(T[] array, T object)
	{
		return indexOf(array, 0, object);
	}
	
	public static <T> int indexOf(T[] array, int start, T object)
	{
		for (int i = start; i < array.length; i++)
		{
			if (Objects.equals(object, array[i]))
				return i;
		}
		return -1;
	}
	
	public static <T> int lastIndexOf(T[] array, T object)
	{
		return lastIndexOf(array, array.length - 1, object);
	}
	
	public static <T> int lastIndexOf(T[] array, int start, T object)
	{
		for (int i = start; i >= 0; i--)
		{
			if (Objects.equals(object, array[i]))
				return i;
		}
		return -1;
	}
	
	public static <T> int indexOfAny(T[] array, T... objects)
	{
		for (Object object : objects)
		{
			int index = indexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	public static <T> int lastIndexOfAny(T[] array, T... objects)
	{
		for (Object object : objects)
		{
			int index = lastIndexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	public static <T> boolean contains(T[] array, T object)
	{
		return indexOf(array, object) != -1;
	}
	
	public static <T> boolean containsAny(T[] array, T... objects)
	{
		for (Object object : objects)
			if (contains(array, object))
				return true;
		return false;
	}
	
	public static <T> boolean containsAll(T[] array, T... objects)
	{
		for (Object object : objects)
			if (!contains(array, object))
				return false;
		return true;
	}
	
	public static Object[] primitiveTransform(Object array)
	{
		Object[] result = (Object[]) array;
		if (array instanceof boolean[])
		{
			boolean[] array1 = (boolean[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Boolean.valueOf(array1[i]);
		}
		else if (array instanceof byte[])
		{
			byte[] array1 = (byte[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Byte.valueOf(array1[i]);
		}
		else if (array instanceof short[])
		{
			short[] array1 = (short[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Short.valueOf(array1[i]);
		}
		else if (array instanceof int[])
		{
			int[] array1 = (int[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Integer.valueOf(array1[i]);
		}
		else if (array instanceof float[])
		{
			float[] array1 = (float[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Float.valueOf(array1[i]);
		}
		else if (array instanceof double[])
		{
			double[] array1 = (double[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Double.valueOf(array1[i]);
		}
		else if (array instanceof long[])
		{
			long[] array1 = (long[]) array;
			result = new Object[array1.length];
			for (int i = 0; i < array1.length; i++)
				result[i] = Long.valueOf(array1[i]);
		}
		return result;
	}
	
	public static int indexOf(int[] array, int integer)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == (integer))
				return i;
		}
		return -1;
	}
	
	public static boolean contains(int[] intArray, int integer)
	{
		return indexOf(intArray, integer) != -1;
	}
}

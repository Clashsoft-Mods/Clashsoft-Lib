package clashsoft.clashsoftapi.util;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Objects;

import net.minecraft.util.MathHelper;

public class CSCollections
{
	public static Collection<?>	tempCollection;
	
	public static Class getComponentType(Collection list)
	{
		tempCollection = list;
		try
		{
			Field tempCollectionField = CSCollections.class.getDeclaredField("tempCollection");
			ParameterizedType tempCollectionType = (ParameterizedType) tempCollectionField.getGenericType();
			Class tempCollectionClass = (Class) tempCollectionType.getActualTypeArguments()[0];
			return tempCollectionClass;
		}
		catch (NoSuchFieldException ex)
		{
			ex.printStackTrace();
		}
		catch (SecurityException ex)
		{
			ex.printStackTrace();
		}
		return Object.class;
	}
	
	public static <T> List<List<T>> split(List<T> list, int maxLength)
	{
		Class clazz = list.getClass().getComponentType();
		int arrays = MathHelper.ceiling_float_int(((float) list.size()) / ((float) maxLength));
		List<List<T>> ret = new ArrayList();
		
		for (int i = 0; i < ret.size(); i++)
		{
			List<T> reti = new ArrayList();
			for (int j = 0; j < maxLength && (j + (i * maxLength)) < list.size(); j++)
				reti.add(list.get(j + (i * maxLength)));
			ret.add(reti);
		}
		return ret;
	}
	
	public static List<String> concatAll(List<String> list, String prefix, String postfix)
	{
		List<String> ret = new ArrayList<String>(list.size());
		for (int i = 0; i < ret.size(); i++)
		{
			String string = list.get(i);
			if (string != null)
				string = prefix + string + postfix;
			else
				string = prefix + postfix;
			ret.add(string);
		}
		return ret;
	}
	
	public static List<String> caseAll(List<String> stringArray, int mode)
	{
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < stringArray.size(); i++)
		{
			if (stringArray.get(i) != null)
				result.add(CSString.caseString(stringArray.get(i), mode));
			else
				result.add("");
		}
		return result;
	}
	
	public static <T> List<T> concat(List<T> array1, List<T> array2)
	{
		List<T> ret = new ArrayList<T>(array1.size() + array2.size());
		ret.addAll(array1);
		ret.addAll(array2);
		return ret;
	}
	
	public static <T> Collection<T> removeDuplicates(Collection<T> collection)
	{
		if (collection != null && collection.size() > 0)
		{
			Collection<T> result = new ArrayList<T>();
			for (T t1 : collection)
			{
				boolean duplicate = false;
				for (T t2 : result)
				{
					if (Objects.equal(t1, t2))
						duplicate = true;
					break;
				}
				if (!duplicate)
					result.add(t1);
			}
			return result;
		}
		return collection;
	}
	
	public static <T> List<T> fromArray(T[] array)
	{
		return Arrays.asList(array);
	}
	
	public static <T> T[] toArray(Class type, Collection<T> collection)
	{
		T[] array = (T[]) Array.newInstance(type, collection.size());
		collection.toArray(array);
		return array;
	}
	
	public static <T> int indexOf(List<T> array, T object)
	{
		return indexOf(array, 0, object);
	}
	
	public static <T> int indexOf(List<T> array, int start, T object)
	{
		for (int i = start; i < array.size(); i++)
		{
			if (Objects.equal(object, array.get(i)))
				return i;
		}
		return -1;
	}
	
	public static <T> int lastIndexOf(List<T> array, T object)
	{
		return lastIndexOf(array, array.size() - 1, object);
	}
	
	public static <T> int lastIndexOf(List<T> array, int start, T object)
	{
		for (int i = start; i >= 0; i--)
		{
			if (Objects.equal(object, array.get(i)))
				return i;
		}
		return -1;
	}
	
	public static <T> int indexOfAny(List<T> array, T... objects)
	{
		for (T object : objects)
		{
			int index = indexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	public static <T> int lastIndexOfAny(List<T> array, T... objects)
	{
		for (T object : objects)
		{
			int index = lastIndexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	public static <T> boolean contains(List<T> array, T object)
	{
		return indexOf(array, object) != -1;
	}
	
	public static <T> boolean containsAny(List<T> array, T... objects)
	{
		for (T object : objects)
			if (contains(array, object))
				return true;
		return false;
	}
	
	public static <T> boolean containsAll(List<T> array, T... objects)
	{
		for (T object : objects)
			if (!contains(array, object))
				return false;
		return true;
	}
}

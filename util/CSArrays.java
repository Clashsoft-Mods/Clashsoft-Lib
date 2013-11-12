package clashsoft.clashsoftapi.util;

import java.lang.reflect.Array;

import com.google.common.base.Objects;

import net.minecraft.util.MathHelper;

/**
 * The Class CSArrays.
 * <p>
 * This class adds several array util methods
 */
public class CSArrays
{
	/**
	 * Splits an array into sub-arrays of the length "maxLenght"
	 * 
	 * @param array
	 *            the array to split
	 * @param maxLength
	 *            the max length
	 * @return the two-dimensional array of strings
	 */
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
	
	/**
	 * Adds the prefix and the postfix to all objects in the string array
	 * 
	 * @param stringArray
	 *            the string array
	 * @param prefix
	 *            the prefix
	 * @param postfix
	 *            the postfix
	 * @return the string[]
	 */
	public static String[] addToAll(String[] stringArray, String prefix, String postfix)
	{
		String[] ret = new String[stringArray.length];
		for (int i = 0; i < ret.length; i++)
		{
			if (stringArray[i] != null)
				ret[i] = prefix + stringArray[i] + postfix;
			else
				ret[i] = prefix + postfix;
		}
		return ret;
	}
	
	/**
	 * Combines two arrays of Type "T"
	 * 
	 * @param <T>
	 *            the generic type
	 * @param stringArray1
	 *            the string array1
	 * @param stringArray2
	 *            the string array2
	 * @return the a new array with the size of array1 plus the size of array2
	 */
	public static <T> T[] combine(T[] stringArray1, T[] stringArray2)
	{
		T[] ret = (T[]) Array.newInstance(stringArray1.getClass().getComponentType(), stringArray1.length + stringArray2.length);
		for (int i = 0; i < stringArray1.length; i++)
		{
			ret[i] = stringArray1[i];
		}
		for (int i = 0; i < stringArray2.length; i++)
		{
			ret[i + stringArray1.length] = stringArray2[i];
		}
		return ret;
	}
	
	/**
	 * Cases all objects int the string array
	 * 
	 * @see CSString#caseString(String string, int mode)
	 * 
	 * @param stringArray
	 *            the string array
	 * @param mode
	 *            the mode
	 * @return the string[]
	 */
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
	
	public static int indexOf(Object[] array, Object object)
	{
		return indexOf(array, 0, object);
	}
	
	/**
	 * Index of the object in the object array
	 * 
	 * @param array
	 *            the object array
	 * @param object
	 *            the object
	 * @return the index
	 */
	public static int indexOf(Object[] array, int start, Object object)
	{
		for (int i = start; i < array.length; i++)
		{
			if (Objects.equal(object, array[i]))
				return i;
		}
		return -1;
	}
	
	public static int lastIndexOf(Object[] array, Object object)
	{
		return lastIndexOf(array, array.length - 1, object);
	}
	
	public static int lastIndexOf(Object[] array, int start, Object object)
	{
		for (int i = start; i >= 0; i--)
		{
			if (Objects.equal(object, array[i]))
				return i;
		}
		return -1;
	}
	
	public static int indexOfAny(Object[] array, Object... objects)
	{
		for (Object object : objects)
		{
			int index = indexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	public static int lastIndexOfAny(Object[] array, Object... objects)
	{
		for (Object object : objects)
		{
			int index = lastIndexOf(array, object);
			if (index != -1)
				return index;
		}
		return -1;
	}
	
	/**
	 * Checks if the object array contains the object
	 * 
	 * @param objectArray
	 *            the object array
	 * @param object
	 *            the object
	 * @return true, if object found
	 */
	public static boolean contains(Object[] array, Object object)
	{
		return indexOf(array, object) != -1;
	}
	
	public static boolean containsAny(Object[] array, Object... objects)
	{
		for (Object object : objects)
			if (contains(array, object))
				return true;
		return false;
	}
	
	public static boolean containsAll(Object[] array, Object... objects)
	{
		for (Object object : objects)
			if (!contains(array, object))
				return false;
		return true;
	}
	
	/**
	 * Index of the integer in the integer array
	 * 
	 * @param objectArray
	 *            the object array
	 * @param object
	 *            the object
	 * @return the index
	 */
	public static int indexOf(int[] array, int integer)
	{
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == (integer))
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Checks if the integer array contains the integer
	 * 
	 * @param intArray
	 *            the int array
	 * @param integer
	 *            the integer
	 * @return true, if integer found
	 */
	public static boolean contains(int[] intArray, int integer)
	{
		return indexOf(intArray, integer) != -1;
	}
}

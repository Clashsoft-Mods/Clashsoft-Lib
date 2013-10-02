package clashsoft.clashsoftapi.util;

import java.lang.reflect.Array;

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
	 * @param stringArray
	 *            the string array to split
	 * @param maxLength
	 *            the max length
	 * @return the two-dimensional array of strings
	 */
	public static String[][] split(String[] stringArray, int maxLength)
	{
		int arrays = MathHelper.ceiling_float_int(((float) stringArray.length) / ((float) maxLength));
		String[][] ret = new String[arrays][];
		
		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = new String[maxLength];
			for (int j = 0; j < maxLength && (j + (i * maxLength)) < stringArray.length; j++)
			{
				ret[i][j] = stringArray[j + (i * maxLength)];
			}
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
			{
				ret[i] = prefix + stringArray[i] + postfix;
			}
			else
			{
				ret[i] = "";
			}
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
	
	/**
	 * Index of the object in the object array
	 * 
	 * @param objectArray
	 *            the object array
	 * @param object
	 *            the object
	 * @return the index
	 */
	@Deprecated
	public static int valueOf(Object[] objectArray, Object object)
	{
		return indexOf(objectArray, object);
	}
	
	/**
	 * Index of the object in the object array
	 * 
	 * @param objectArray
	 *            the object array
	 * @param object
	 *            the object
	 * @return the index
	 */
	public static int indexOf(Object[] objectArray, Object object)
	{
		for (int i = 0; i < objectArray.length; i++)
		{
			if (object == null && objectArray[i] == null)
				return i;
			if (object.equals(objectArray[i]))
				return i;
		}
		return -1;
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
	@Deprecated
	public static int valueOf(int[] intArray, int integer)
	{
		return indexOf(intArray, integer);
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
	public static int indexOf(int[] intArray, int integer)
	{
		for (int i = 0; i < intArray.length; i++)
		{
			if (intArray[i] == (integer))
			{
				return i;
			}
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
	public static boolean contains(Object[] objectArray, Object object)
	{
		return indexOf(objectArray, object) != -1;
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

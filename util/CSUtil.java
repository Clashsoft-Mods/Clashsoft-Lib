package clashsoft.cslib.util;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.util.*;

import clashsoft.cslib.reflect.ImmutableObjectFactory;

/**
 * The Class CSUtil.
 * This class adds several util methods.
 */
public class CSUtil
{	
	/**
	 * Log an object.
	 * 
	 * @param o
	 *            the o
	 */
	public static void log(Object o)
	{
		System.out.println(o);
	}
	
	/**
	 * First algorythm to sort a List using HashSets.
	 * 
	 * @param list
	 *            the list
	 * @return the list
	 */
	public static List removeDuplicates(List list)
	{
		if (list != null && list.size() > 0)
		{
			Set set = new HashSet(list);
			list.clear();
			list.addAll(set);
			return list;
		}
		return list;
	}
	
	/**
	 * Gets the combined color from an array of colors.
	 * 
	 * @param colors
	 *            the colors
	 * @return the int
	 */
	public static int combineColors(int... colors)
	{
		int r = 0;
		int g = 0;
		int b = 0;
		for (int i : colors)
		{
			Color c = new Color(i);
			r += c.getRed();
			g += c.getGreen();
			b += c.getBlue();
		}
		r /= colors.length;
		g /= colors.length;
		b /= colors.length;
		
		return (b + (g * 256) + (r * 65536));
	}
	
	/**
	 * Checks if bit 'pos' in 'n' is {@code 0b1}.
	 * 
	 * @param n
	 *            the n
	 * @param pos
	 *            the pos
	 * @return true, if successful
	 */
	public static boolean checkBit(int n, int pos)
	{
		return (n & 1 << pos) != 0;
	}
	
	/**
	 * Sets bit 'pos' in Integer 'n' to 'value'.
	 * 
	 * @param n
	 *            the n
	 * @param pos
	 *            the pos
	 * @param value
	 *            the value
	 * @return the int
	 */
	public static int setBit(int n, int pos, boolean value)
	{
		int bitToSet = 1 << pos;
		return value ? (n | bitToSet) : ((n | bitToSet) ^ bitToSet);
	}
	
	/**
	 * Creates the color code for a color.
	 * 
	 * @param light
	 *            the light
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 * @return the color code
	 */
	public static String fontColor(int light, int r, int g, int b)
	{
		return "\u00a7" + Integer.toHexString(fontColorInt(light, r, g, b));
	}
	
	/**
	 * Creates the color code integer for a color.
	 * 
	 * @param light
	 *            the light
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 * @return the color code integer
	 */
	public static int fontColorInt(int light, int r, int g, int b)
	{
		int i = b > 0 ? 1 : 0;
		if (g > 0)
			i |= 2;
		if (r > 0)
			i |= 4;
		if (light > 0)
			i |= 8;
		return i;
	}
	
	/**
	 * Converts a color name to a color code.
	 * 
	 * @param fontColor
	 *            the font color
	 * @return the color code
	 */
	public static String fontColor(EnumFontColor fontColor)
	{
		return fontColor(fontColor.getLight(), fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());
	}
	
	/**
	 * Converts a color name to a color code integer.
	 * 
	 * @param fontColor
	 *            the font color
	 * @return the color code integer
	 */
	public static int fontColorInt(EnumFontColor fontColor)
	{
		return fontColorInt(fontColor.getLight(), fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue());
	}
	
	/* (non-Javadoc)
	 * @see CSString#convertToRoman(int)
	 */
	@Deprecated
	public static String convertToRoman(int number)
	{
		return CSString.convertToRoman(number);
	}
	
	/* (non-Javadoc)
	 * @see CSString#convertString(String, int)
	 */
	@Deprecated
	public static String cutString(String string, int maxLineLength)
	{
		return CSString.cutString(string, maxLineLength);
	}
	
	/* (non-Javadoc)
	 * @see CSString#makeLineList(String)
	 */
	@Deprecated
	public static String[] makeLineList(String string)
	{
		return CSString.makeLineList(string);
	}
	
	/**
	 * Creates a new instance of T using the parameters.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param c
	 *            the c
	 * @param parameters
	 *            the parameters
	 * @return the t
	 * @see ImmutableObjectFactory#createObject(Class, Object...)
	 */
	public static <T> T createInstance(Class<T> c, Object... parameters)
	{
		Class[] parameterTypes = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++)
		{
			if (parameters[i] != null)
				parameterTypes[i] = parameters[i].getClass();
		}
		
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

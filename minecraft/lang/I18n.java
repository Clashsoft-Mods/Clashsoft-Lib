package clashsoft.cslib.minecraft.lang;

import net.minecraft.util.StatCollector;

/**
 * A utility class for translation.
 * 
 * @author Clashsoft
 */
public class I18n
{
	/**
	 * Returns a translation string.
	 * 
	 * @see StatCollector#translateToLocal(String)
	 * @param key
	 *            the translation key
	 * @return the translated string
	 */
	public static String getString(String key)
	{
		return StatCollector.translateToLocal(key);
	}
	
	/**
	 * Returns a formatted translation string.
	 * 
	 * @see StatCollector#translateToLocalFormatted(String, Object...)
	 * @param key
	 *            the translation key
	 * @param params
	 *            the parameters
	 * @return the formatted translated string
	 */
	public static String getString(String key, Object... params)
	{
		return StatCollector.translateToLocalFormatted(key, params);
	}
	
	/**
	 * Returns a formatted translation string.
	 * 
	 * @see StatCollector#translateToLocalFormatted(String, Object...)
	 * @param key
	 *            the translation key
	 * @param params
	 *            the parameters
	 * @return the formatted translated string
	 */
	public static String getStringParams(String key, Object... params)
	{
		return StatCollector.translateToLocalFormatted(key, params);
	}
}

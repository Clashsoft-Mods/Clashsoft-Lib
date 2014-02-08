package clashsoft.cslib.minecraft;

import net.minecraft.util.StatCollector;

public class I18n
{
	public static String getString(String key)
	{
		return StatCollector.translateToLocal(key);
	}
	
	public static String getStringParams(String key, Object... params)
	{
		return StatCollector.translateToLocalFormatted(key, params);
	}
}

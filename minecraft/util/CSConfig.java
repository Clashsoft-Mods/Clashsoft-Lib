package clashsoft.cslib.minecraft.util;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class CSConfig
{
	public static String		configName		= null;
	public static Configuration	config			= null;
	public static boolean		enableComments	= true;
	
	public static void loadConfig(File configFile, String configName)
	{
		if (config != null)
		{
			saveConfig();
		}
		
		System.out.println("[CSCONFIG] Loading configuration file " + configName);
		config = new Configuration(configFile);
	}
	
	public static void loadConfig(File configFile)
	{
		loadConfig(configFile, configFile.getName());
	}
	
	public static void saveConfig()
	{
		config.save();
		config = null;
		
		System.out.println("[CSCONFIG] Saving configuration file " + configName);
	}
	
	public static String getDefaultDesc(String key, Object _default)
	{
		return enableComments ? (key + ". Default: " + _default) : null;
	}
	
	public static void checkConfig()
	{
		if (config == null)
		{
			throw new IllegalStateException("No config loaded!");
		}
	}
	
	// Default getters
	
	public static int getInt(String category, String key, int _default)
	{
		return getInt(category, key, getDefaultDesc(key, _default), _default);
	}
	
	public static float getFloat(String category, String key, float _default)
	{
		return getFloat(category, key, getDefaultDesc(key, _default), _default);
	}
	
	public static double getDouble(String category, String key, double _default)
	{
		return getDouble(category, key, getDefaultDesc(key, _default), _default);
	}
	
	public static boolean getBool(String category, String key, boolean _default)
	{
		return getBool(category, key, getDefaultDesc(key, _default), _default);
	}
	
	public static String getString(String category, String key, String _default)
	{
		return getString(category, key, getDefaultDesc(key, _default), _default);
	}
	
	public static String getString(String category, String key, Object _default)
	{
		return getString(category, key, _default.toString());
	}
	
	// Description getters
	
	public static int getInt(String category, String key, String desc, int _default)
	{
		checkConfig();
		
		return config.get(category, key, _default, desc).getInt(_default);
	}
	
	public static float getFloat(String category, String key, String desc, float _default)
	{
		return (float) getDouble(category, key, desc, (double) _default);
	}
	
	public static double getDouble(String category, String key, String desc, double _default)
	{
		checkConfig();
		
		return config.get(category, key, _default, desc).getDouble(_default);
	}
	
	public static boolean getBool(String category, String key, String desc, boolean _default)
	{
		checkConfig();
		
		return config.get(category, key, _default, desc).getBoolean(_default);
	}
	
	public static String getString(String category, String key, String desc, String _default)
	{
		checkConfig();
		
		return config.get(category, key, _default, desc).getString();
	}
	
	public static int getItem(String key, int _default)
	{
		checkConfig();
		
		if (!key.contains("Item ID"))
		{
			key += " Item ID";
		}
		return config.getItem(key, _default, getDefaultDesc(key, _default)).getInt(_default);
	}
	
	public static int getTerrainBlock(String key, int _default)
	{
		checkConfig();
		
		if (!key.contains("Block ID"))
		{
			key += " Block ID";
		}
		return config.getTerrainBlock(Configuration.CATEGORY_BLOCK, key, _default, getDefaultDesc(key, _default)).getInt(_default);
	}
	
	public static int getBlock(String key, int _default)
	{
		checkConfig();
		
		if (!key.contains("Block ID"))
		{
			key += " Block ID";
		}
		return config.getBlock(key, _default, getDefaultDesc(key, _default)).getInt(_default);
	}
	
	public static int getDimension(String key, int _default)
	{
		checkConfig();
		
		if (!key.contains("Dimension ID"))
		{
			key += " Dimension ID";
		}
		return config.get("dimension", key, _default, getDefaultDesc(key, _default)).getInt(_default);
	}
	
	public static int getBiome(String key, int _default)
	{
		checkConfig();
		
		if (!key.contains("Biome ID"))
		{
			key += " Biome ID";
		}
		return config.get("biome", key, _default, getDefaultDesc(key, _default)).getInt(_default);
	}
}

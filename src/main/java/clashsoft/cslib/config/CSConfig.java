package clashsoft.cslib.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.util.CSString;
import clashsoft.cslib.util.IParsable;

/**
 * The global class for loading configuration files.
 * 
 * @author Clashsoft
 */
public class CSConfig
{
	public static File				configFile;
	public static String			configName;
	public static boolean			enableComments	= true;
	
	private static Configuration	config;
	
	public static void loadConfig(File configFile, String configName)
	{
		CSLog.info("[Config] Loading config file " + configName + "...");
		
		CSConfig.configFile = configFile;
		CSConfig.configName = configName;
		config = new Configuration(configFile);
		
		CSLog.info("[Config] Loaded config file " + configName);
	}
	
	public static void loadConfig(File configFile)
	{
		loadConfig(configFile, configFile.getName());
	}
	
	public static void saveConfig()
	{
		CSLog.info("[Config] Saving config file " + configName + "...");
		config.save();
		CSLog.info("[Config] Saved config file " + configName);
	}
	
	public static String getDefaultDesc(String key, Object _default)
	{
		if (enableComments)
		{
			return key + ". Default: " + _default;
		}
		return null;
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
		return getString(category, key, String.valueOf(_default));
	}
	
	public static <T extends IParsable> T getObject(String category, String key, T _default)
	{
		return getObject(category, key, getDefaultDesc(key, _default), _default);
	}
	
	// Description getters
	
	public static int getInt(String category, String key, String desc, int _default)
	{
		checkConfig();
		return config.get(CSString.identifier(key), category, _default, desc).getInt(_default);
	}
	
	public static float getFloat(String category, String key, String desc, float _default)
	{
		checkConfig();
		return (float) config.get(CSString.identifier(key), category, _default, desc).getDouble(_default);
	}
	
	public static double getDouble(String category, String key, String desc, double _default)
	{
		checkConfig();
		return config.get(CSString.identifier(key), category, _default, desc).getDouble(_default);
	}
	
	public static boolean getBool(String category, String key, String desc, boolean _default)
	{
		checkConfig();
		return config.get(CSString.identifier(key), category, _default, desc).getBoolean(_default);
	}
	
	public static String getString(String category, String key, String desc, String _default)
	{
		checkConfig();
		return config.get(CSString.identifier(key), category, _default, desc).getString();
	}
	
	public static <T extends IParsable> T getObject(String category, String key, String desc, T _default)
	{
		return (T) _default.parse(getString(category, key, desc, _default.toString()));
	}
	
	@Deprecated
	public static int getItem(String key, int _default)
	{
		if (!key.contains("Item ID"))
		{
			key += " Item ID";
		}
		return getInt("item", key, _default);
	}
	
	@Deprecated
	public static int getTerrainBlock(String key, int _default)
	{
		if (!key.contains("Block ID"))
		{
			key += " Block ID";
		}
		return getInt("terrainblock", key, _default);
	}
	
	@Deprecated
	public static int getBlock(String key, int _default)
	{
		if (!key.contains("Block ID"))
		{
			key += " Block ID";
		}
		return getInt("block", key, _default);
	}
	
	public static int getTileEntity(String key, int _default)
	{
		if (!key.contains("TileEntity ID"))
		{
			key += " TileEntity ID";
		}
		return getInt("tileentity", key, _default);
	}
	
	public static int getDimension(String key, int _default)
	{
		if (!key.contains("Dimension ID"))
		{
			key += " Dimension ID";
		}
		return getInt("dim", key, _default);
	}
	
	public static int getBiome(String key, int _default)
	{
		if (!key.contains("Biome ID"))
		{
			key += " Biome ID";
		}
		return getInt("biome", key, _default);
	}
}

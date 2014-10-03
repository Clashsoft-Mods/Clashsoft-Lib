package clashsoft.cslib.config;

import java.io.File;

import clashsoft.cslib.minecraft.world.gen.OreGen;
import clashsoft.cslib.util.CSString;
import clashsoft.cslib.util.IParsable;

import net.minecraftforge.common.config.Configuration;

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
	
	/**
	 * Loads a config from the given {@link File} {@code file} and names it with
	 * the given {@link String} {@code configName}.
	 * 
	 * @param configFile
	 *            the config file
	 * @param configName
	 *            the name
	 */
	public static void loadConfig(File configFile, String configName)
	{
		CSConfig.configFile = configFile;
		CSConfig.configName = configName;
		config = new Configuration(configFile);
	}
	
	/**
	 * Loads a config from the given {@link File} {@code file}.
	 * 
	 * @param configFile
	 *            the config file
	 */
	public static void loadConfig(File configFile)
	{
		loadConfig(configFile, configFile.getName());
	}
	
	/**
	 * Saves the current config to the disk.
	 */
	public static void saveConfig()
	{
		config.save();
	}
	
	/**
	 * Returns the default description for the given {@link String} {@code key}
	 * and the given {@link Object} {@code _default}.<br>
	 * The description always has the format
	 * <p>
	 * [key]. Default: [_default]
	 * 
	 * @param key
	 *            the key
	 * @param _default
	 *            the default value
	 * @return the description
	 */
	public static String getDefaultDesc(String key, Object _default)
	{
		if (enableComments)
		{
			return key + ". Default: " + _default;
		}
		return null;
	}
	
	/**
	 * Checks if a config is currently loaded. If not, an
	 * {@link IllegalStateException} is thrown.
	 */
	private static void checkConfig()
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
		return config.get(category, CSString.identifier(key), _default, desc).getInt(_default);
	}
	
	public static float getFloat(String category, String key, String desc, float _default)
	{
		checkConfig();
		return (float) config.get(category, CSString.identifier(key), _default, desc).getDouble(_default);
	}
	
	public static double getDouble(String category, String key, String desc, double _default)
	{
		checkConfig();
		return config.get(category, CSString.identifier(key), _default, desc).getDouble(_default);
	}
	
	public static boolean getBool(String category, String key, String desc, boolean _default)
	{
		checkConfig();
		return config.get(category, CSString.identifier(key), _default, desc).getBoolean(_default);
	}
	
	public static String getString(String category, String key, String desc, String _default)
	{
		checkConfig();
		return config.get(category, CSString.identifier(key), _default, desc).getString();
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
	
	public static OreGen getOreGen(String key, OreGen _default)
	{
		if (_default == null)
		{
			_default = new OreGen();
		}
		
		_default.amount = getInt(key, "Number Of Blocks", null, _default.amount);
		_default.veigns = getInt(key, "Veigns Per Chunk", null, _default.veigns);
		_default.minY = getInt(key, "Min Y", null, _default.minY);
		_default.maxY = getInt(key, "Max Y", null, _default.minY);
		
		return _default;
	}
}

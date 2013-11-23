package clashsoft.cslib.addon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * The class CSAddons.
 * <p>
 * Loads and stores loaded {@link Addon}.
 * 
 * @author Clashsoft
 */
public class CSAddons
{
	public static Multimap<String, String> loadedAddons = HashMultimap.create();
	
	/**
	 * Loads all {@link Addon}s for the mod {@code modName}
	 * 
	 * @param modName the name of the mod
	 */
	public static void loadAddons(String modName)
	{
		
	}
}

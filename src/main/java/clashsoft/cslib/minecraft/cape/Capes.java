package clashsoft.cslib.minecraft.cape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.minecraft.CSLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public final class Capes
{
	protected static HashMap<String, String>	usernameToCapeName	= new HashMap();
	protected static HashMap<String, Cape>		capeNameToCape		= new HashMap();
	protected static int						uniqueID			= 0;
	
	public static Object						noCape;
	public static Object						defaultCape;
	
	private Capes()
	{
	}
	
	/**
	 * Sets the cape of the given {@link EntityPlayer} {@code player} to the
	 * default cape and syncs it.
	 * 
	 * @param player
	 *            the player
	 */
	public static void clearCape(EntityPlayer player)
	{
		CSLib.proxy.clearCape(player);
	}
	
	/**
	 * Updates the cape of the given {@link EntityPlayer} {@code player} and
	 * syncs it.
	 * 
	 * @param player
	 *            the player
	 */
	public static void updateCape(EntityPlayer player)
	{
		CSLib.proxy.updateCape(player);
	}
	
	/**
	 * Sets the cape of the given {@link EntityPlayer} {@code player} to the
	 * cape with the given {@link String} {@code capeName} and syncs it.
	 *
	 * @param player
	 * @param capeName
	 */
	public static void setCape(EntityPlayer player, String capeName)
	{
		CSLib.proxy.setCape(player, capeName);
	}
	
	private static String uniqueName()
	{
		String capeName = "cape_" + uniqueID;
		uniqueID++;
		return capeName;
	}
	
	/**
	 * Returns the {@link Cape} with the given {@link String} {@code capeName}.
	 * 
	 * @param capeName
	 *            the cape's name
	 * @return the cape
	 */
	public static Cape getCape(String capeName)
	{
		Cape cape = capeNameToCape.get(capeName);
		return cape;
	}
	
	/**
	 * Registers the given {@link Cape} {@code cape}.
	 * 
	 * @param cape
	 *            the cape
	 * @return the cape
	 */
	public static Cape addCape(Cape cape)
	{
		capeNameToCape.put(cape.getName(), cape);
		return cape;
	}
	
	public static LocalCape addCape(String capeName, ResourceLocation location)
	{
		LocalCape cape = new LocalCape(capeName, location);
		capeNameToCape.put(capeName, cape);
		return cape;
	}
	
	public static URLCape addCape(String capeName, String url)
	{
		URLCape cape = new URLCape(capeName, url);
		capeNameToCape.put(capeName, cape);
		return cape;
	}
	
	/**
	 * Assigns the cape with the given {@link String} {@code capeName} to the
	 * given {@link String} {@code username}.
	 * 
	 * @param username
	 *            the username
	 * @param capeName
	 *            the name of the cape
	 */
	public static void setCapeName(String username, String capeName)
	{
		usernameToCapeName.put(username, capeName);
	}
	
	public static void setLocalCape(String username, ResourceLocation location)
	{
		String capename = uniqueName();
		addCape(capename, location);
		setCapeName(username, capename);
	}
	
	public static void setURLCape(String username, String url)
	{
		String capename = uniqueName();
		addCape(capename, url);
		setCapeName(username, capename);
	}
	
	/**
	 * Loads a cape file at the given {@link String} {@code fileUrl}. The file
	 * should have the following format:
	 * 
	 * <PRE>
	 * [capename]=http://[URL]
	 * test_cape=http://test.com/image.png
	 * 
	 * [capename]=https://[URL]
	 * test_cape=https://secure.test.com/image.png
	 * 
	 * [capename]=local:[resource location]
	 * local_cape=local:textures/misc/cape.png
	 * 
	 * [capename]=local:[domain]:[resource location]
	 * local_cape=local:cslib:textures/misc/cape.png
	 * 
	 * [username]=[capename]
	 * Clashsoft=local_cape
	 * </PRE>
	 * 
	 * @param fileUrl
	 *            the url
	 */
	public static void loadCapeFile(String fileUrl)
	{
		try
		{
			URL url = new URL(fileUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			
			while ((line = reader.readLine()) != null)
			{
				if (!line.isEmpty() && line.charAt(0) != '#')
				{
					processLine(line);
				}
			}
		}
		catch (IOException ex)
		{
			CSLog.error(ex);
		}
	}
	
	private static void processLine(String line)
	{
		int i = line.indexOf('=');
		if (i == -1)
		{
			return;
		}
		
		String key = line.substring(0, i);
		String value = line.substring(i + 1);
		
		// Capename=URL
		if (value.startsWith("http://") || value.startsWith("https://"))
		{
			Cape cape = new URLCape(key, value);
			capeNameToCape.put(key, cape);
		}
		// Capename=ResourceLocation
		else if (value.startsWith("local:"))
		{
			Cape cape = new LocalCape(key, value.substring(6));
			capeNameToCape.put(key, cape);
		}
		// Username=Cape
		else
		{
			usernameToCapeName.put(key, value);
		}
	}
}

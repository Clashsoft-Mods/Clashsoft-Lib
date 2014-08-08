package clashsoft.cslib.minecraft.cape;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.minecraft.init.CSLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public final class Capes
{
	protected static HashMap<String, String>	usernameToCapeName	= new HashMap();
	protected static HashMap<String, Cape>		capeNameToCape		= new HashMap();
	protected static int						uniqueID			= 0;
	
	public static Cape							noCape				= new EmptyCape("no_cape");
	
	public static Cape							defaultCape			= new DefaultCape("default_cape");
	
	static
	{
		addCape(noCape);
		addCape(defaultCape);
	}
	
	private Capes()
	{
	}
	
	public static void clearCape(EntityPlayer player)
	{
		CSLib.proxy.clearCape(player);
	}
	
	public static void updateCape(EntityPlayer player)
	{
		CSLib.proxy.updateCape(player);
	}
	
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
	
	public static Cape getCape(String capeName)
	{
		return capeNameToCape.get(capeName);
	}
	
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
	
	public static void addFileUrl(String fileUrl)
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
		if (value.startsWith("http:") || value.startsWith("https:"))
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

package clashsoft.cslib.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;

/**
 * The Class CSResourceHelper.
 * <p>
 * This class is a factory for {@link ResourceLocation}s.
 * 
 * @author Clashsoft
 */
public class CSResourceHelper
{
	/** The resources. */
	public static Map<String, ResourceLocation>	resources	= new HashMap<String, ResourceLocation>();
	
	/**
	 * Returns a resource, may already be stored.
	 * 
	 * @param resource
	 *            the resource
	 * @return the resource
	 */
	public static ResourceLocation getResource(String resource)
	{
		ResourceLocation rl;
		if (!resources.containsKey(resource))
		{
			rl = new ResourceLocation(resource);
			resources.put(resource, rl);
		}
		else
		{
			rl = resources.get(resource);
		}
		return rl;
	}
	
	public static ResourceLocation getResource(String domain, String resource)
	{
		if (domain != null && !domain.isEmpty())
		{
			resource = domain + ":" + resource;
		}
		return getResource(resource);
	}
	
	/**
	 * Reads all lines from the given {@link ResourceLocation} {@code rl} and
	 * returns a list containing the results.
	 * 
	 * @param rl
	 *            the resource location
	 * @return the list of lines
	 */
	public static List<String> readAllLines(ResourceLocation rl)
	{
		BufferedReader bufferedreader = null;
		try
		{
			bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream(), Charsets.UTF_8));
			ArrayList arraylist = new ArrayList();
			String s;
			
			while ((s = bufferedreader.readLine()) != null)
			{
				arraylist.add(s);
			}
			
			return arraylist;
			
		}
		catch (IOException ioexception)
		{
		}
		finally
		{
			if (bufferedreader != null)
			{
				try
				{
					bufferedreader.close();
				}
				catch (IOException ioexception)
				{
				}
			}
		}
		return Collections.EMPTY_LIST;
	}
}

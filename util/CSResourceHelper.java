package clashsoft.clashsoftapi.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.resources.ResourceLocation;

public class CSResourceHelper
{
	public static Map<String, ResourceLocation> resources = new HashMap<String, ResourceLocation>();
	
	public static ResourceLocation getResource(String resource)
	{
		ResourceLocation rl;
		if (!resources.containsKey(resource))
		{
			rl = new ResourceLocation(resource);
			resources.put(resource, rl);
		}
		else
			rl = resources.get(resource);
		return rl;
	}
}

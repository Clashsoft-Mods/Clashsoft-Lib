package clashsoft.clashsoftapi.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class CSResourceHelper
{
	public static Map<String, ResourceLocation> resources = new HashMap<String, ResourceLocation>();
	public static Map<String, Icon> icons = new HashMap<String, Icon>();
	
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
	
	public static Icon getIcon(IconRegister par1IconRegister, String iconName)
	{
		Icon ic;
		if (!icons.containsKey(iconName))
		{
			ic = par1IconRegister.registerIcon(iconName);
			icons.put(iconName, ic);
		}
		else
			ic = getIcon(iconName);
		return ic;
	}
	
	public static Icon getIcon(String iconName)
	{
		return icons.get(iconName);
	}
}

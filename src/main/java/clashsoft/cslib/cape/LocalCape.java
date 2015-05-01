package clashsoft.cslib.cape;

import net.minecraft.util.ResourceLocation;

public class LocalCape extends Cape
{
	public LocalCape(String name, String location)
	{
		this(name, new ResourceLocation(location));
	}
	
	public LocalCape(String name, ResourceLocation location)
	{
		super(name);
		this.location = location;
	}
}

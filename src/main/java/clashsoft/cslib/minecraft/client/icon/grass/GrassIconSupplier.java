package clashsoft.cslib.minecraft.client.icon.grass;

import clashsoft.cslib.minecraft.client.icon.DomainIconSupplier;
import clashsoft.cslib.minecraft.client.icon.IIconSupplier;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GrassIconSupplier implements IIconSupplier
{
	public String	topName;
	public String	sideName;
	public String	bottomName;
	
	public IIcon	topIcon;
	public IIcon	sideIcon;
	public IIcon	bottomIcon;
	
	public static IIconSupplier create(Object icons)
	{
		if (icons == null)
		{
			return null;
		}
		if (icons instanceof IIconSupplier)
		{
			return (IIconSupplier) icons;
		}
		Class c = icons.getClass();
		if (c == String.class)
		{
			return new GrassIconSupplier((String) icons);
		}
		else if (c == String[].class)
		{
			return new GrassMultiIconSupplier((String[]) icons);
		}
		else if (c == Object[].class)
		{
			Object[] aobject = (Object[]) icons;
			if (aobject.length == 2 && aobject[0].getClass() == String.class)
			{
				return new DomainIconSupplier((String) aobject[0], create(aobject[1]));
			}
		}
		else if (c == String[][].class)
		{
			String[][] aastring = (String[][]) icons;
			if (aastring.length == 3)
			{
				return new GrassMultiIconSupplier(aastring[0], aastring[1], aastring[2]);
			}
		}
		throw new IllegalArgumentException("The argument must be either a String, String[] or String[][] !");
	}
	
	public GrassIconSupplier(String name)
	{
		this.topName = name + "_top";
		this.sideName = name + "_side";
		this.bottomName = name + "_bottom";
	}
	
	public GrassIconSupplier(String topName, String sideName, String bottomName)
	{
		this.topName = topName;
		this.sideName = sideName;
		this.bottomName = bottomName;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon(this.topName);
		this.sideIcon = iconRegister.registerIcon(this.sideName);
		this.bottomIcon = iconRegister.registerIcon(this.bottomName);
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		return this.sideIcon;
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		if (side == 0)
		{
			return this.bottomIcon;
		}
		else if (side == 1)
		{
			return this.topIcon;
		}
		return this.sideIcon;
	}
}

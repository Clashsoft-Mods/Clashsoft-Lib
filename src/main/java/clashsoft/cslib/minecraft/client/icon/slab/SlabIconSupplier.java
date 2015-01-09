package clashsoft.cslib.minecraft.client.icon.slab;

import clashsoft.cslib.minecraft.client.icon.DomainIconSupplier;
import clashsoft.cslib.minecraft.client.icon.IIconSupplier;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class SlabIconSupplier implements IIconSupplier
{
	public String	topName;
	public String	sideName;
	
	public IIcon	topIcon;
	public IIcon	sideIcon;
	
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
			return new SlabIconSupplier((String) icons);
		}
		else if (c == String[].class)
		{
			return new SlabMultiIconSupplier((String[]) icons);
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
				return new SlabMultiIconSupplier(aastring[0], aastring[1], aastring[2]);
			}
		}
		throw new IllegalArgumentException("The argument must be either a String, String[] or String[][] !");
	}
	
	public SlabIconSupplier(String name)
	{
		this.topName = name + "_top";
		this.sideName = name + "_side";
	}
	
	public SlabIconSupplier(String topName, String sideName)
	{
		this.topName = topName;
		this.sideName = sideName;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.topIcon = iconRegister.registerIcon(this.topName);
		this.sideIcon = iconRegister.registerIcon(this.sideName);
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		return this.sideIcon;
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		if (side == 0 || side == 1)
		{
			return this.topIcon;
		}
		return this.sideIcon;
	}
}

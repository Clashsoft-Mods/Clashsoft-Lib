package clashsoft.cslib.minecraft.client.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconSupplier implements IIconSupplier
{
	public String	iconName;
	public IIcon	icon;
	
	public IconSupplier(String iconName)
	{
		this.iconName = iconName;
	}
	
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
			return new IconSupplier((String) icons);
		}
		else if (c == String[].class)
		{
			return new MultiIconSupplier((String[]) icons);
		}
		else if (c == Object[].class)
		{
			Object[] aobject = (Object[]) icons;
			if (aobject.length == 2 && aobject[0].getClass() == String.class)
			{
				return new DomainIconSupplier((String) aobject[0], create(aobject[1]));
			}
		}
		else if (c == Object[][].class)
		{
			return new SidedIconSupplier((Object[]) icons);
		}
		throw new IllegalArgumentException("The argument must be either a String, String[], Object[] or Object[][] !");
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.icon = iconRegister.registerIcon(this.iconName);
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		return this.icon;
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		return this.icon;
	}
}

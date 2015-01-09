package clashsoft.cslib.minecraft.client.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class MultiIconSupplier implements IIconSupplier
{
	public String[]	iconNames;
	public IIcon[]	icons;
	
	public MultiIconSupplier(String[] iconNames)
	{
		this.iconNames = iconNames;
		this.icons = new IIcon[iconNames.length];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < this.iconNames.length; i++)
		{
			this.icons[i] = iconRegister.registerIcon(this.iconNames[i]);
		}
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		if (metadata < 0 || metadata >= this.iconNames.length)
		{
			metadata = 0;
		}
		return this.icons[metadata];
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		if (metadata < 0 || metadata >= this.iconNames.length)
		{
			metadata = 0;
		}
		return this.icons[metadata];
	}
}

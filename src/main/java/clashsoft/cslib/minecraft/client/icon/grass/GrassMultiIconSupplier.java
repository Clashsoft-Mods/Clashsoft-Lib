package clashsoft.cslib.minecraft.client.icon.grass;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import clashsoft.cslib.minecraft.client.icon.IIconSupplier;

public class GrassMultiIconSupplier implements IIconSupplier
{
	private int		count;
	
	public String[]	topIconNames;
	public String[]	sideIconNames;
	public String[]	bottomIconNames;
	
	public IIcon[]	topIcons;
	public IIcon[]	sideIcons;
	public IIcon[]	bottomIcons;
	
	public GrassMultiIconSupplier(String[] iconNames)
	{
		this.count = iconNames.length;
		
		this.topIconNames = new String[this.count];
		this.sideIconNames = new String[this.count];
		this.bottomIconNames = new String[this.count];
		this.topIcons = new IIcon[this.count];
		this.sideIcons = new IIcon[this.count];
		this.bottomIcons = new IIcon[this.count];
		
		for (int i = 0; i < this.count; i++)
		{
			this.topIconNames[i] = iconNames[i] + "_top";
			this.sideIconNames[i] = iconNames[i] + "_side";
			this.bottomIconNames[i] = iconNames[i] + "_bottom";
		}
	}
	
	public GrassMultiIconSupplier(String[] topIconNames, String[] sideIconNames, String[] bottomIconNames)
	{
		this.count = topIconNames.length;
		this.topIconNames = topIconNames;
		this.sideIconNames = sideIconNames;
		this.bottomIconNames = bottomIconNames;
		this.topIcons = new IIcon[this.count];
		this.sideIcons = new IIcon[this.count];
		this.bottomIcons = new IIcon[this.count];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < this.count; i++)
		{
			this.topIcons[i] = iconRegister.registerIcon(this.topIconNames[i]);
			this.sideIcons[i] = iconRegister.registerIcon(this.sideIconNames[i]);
			this.bottomIcons[i] = iconRegister.registerIcon(this.bottomIconNames[i]);
		}
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		if (metadata < 0 || metadata >= this.count)
		{
			metadata = 0;
		}
		return this.sideIcons[metadata];
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		if (metadata < 0 || metadata >= this.count)
		{
			metadata = 0;
		}
		if (side == 0)
		{
			return this.bottomIcons[metadata];
		}
		else if (side == 1)
		{
			return this.topIcons[metadata];
		}
		return this.sideIcons[metadata];
	}
}

package clashsoft.cslib.minecraft.client.icon.slab;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import clashsoft.cslib.minecraft.client.icon.IIconSupplier;

public class SlabMultiIconSupplier implements IIconSupplier
{
	private int		count;
	
	public String[]	topIconNames;
	public String[]	sideIconNames;
	
	public IIcon[]	topIcons;
	public IIcon[]	sideIcons;
	
	public SlabMultiIconSupplier(String[] iconNames)
	{
		this.count = iconNames.length;
		
		this.topIconNames = new String[this.count];
		this.sideIconNames = new String[this.count];
		this.topIcons = new IIcon[this.count];
		this.sideIcons = new IIcon[this.count];
		
		for (int i = 0; i < this.count; i++)
		{
			this.topIconNames[i] = iconNames[i] + "_top";
			this.sideIconNames[i] = iconNames[i] + "_side";
		}
	}
	
	public SlabMultiIconSupplier(String[] topIconNames, String[] sideIconNames, String[] bottomIconNames)
	{
		this.count = topIconNames.length;
		this.topIconNames = topIconNames;
		this.sideIconNames = sideIconNames;
		this.topIcons = new IIcon[this.count];
		this.sideIcons = new IIcon[this.count];
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (int i = 0; i < this.count; i++)
		{
			this.topIcons[i] = iconRegister.registerIcon(this.topIconNames[i]);
			this.sideIcons[i] = iconRegister.registerIcon(this.sideIconNames[i]);
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
		if (side == 0 || side == 1)
		{
			return this.topIcons[metadata];
		}
		return this.sideIcons[metadata];
	}
}

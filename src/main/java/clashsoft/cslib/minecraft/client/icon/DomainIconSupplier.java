package clashsoft.cslib.minecraft.client.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class DomainIconSupplier implements IIconSupplier, IIconRegister
{
	public IIconSupplier	parent;
	public String			domain;
	
	private IIconRegister	iconRegister;
	
	public DomainIconSupplier(String domain, IIconSupplier parent)
	{
		this.parent = parent;
		this.domain = domain + ":";
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		this.iconRegister = iconRegister;
		this.parent.registerIcons(this);
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		return this.parent.getIcon(metadata);
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		return this.parent.getIcon(metadata, side);
	}
	
	@Override
	public IIcon registerIcon(String name)
	{
		return this.iconRegister.registerIcon(this.domain + name);
	}
}

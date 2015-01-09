package clashsoft.cslib.minecraft.client.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class SidedIconSupplier implements IIconSupplier
{
	public IIconSupplier[]	iconSuppliers;
	
	public SidedIconSupplier(Object[] icons)
	{
		this.iconSuppliers = new IIconSupplier[icons.length];
		for (int i = 0; i < icons.length; i++)
		{
			this.iconSuppliers[i] = IconSupplier.create(icons);
		}
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		for (IIconSupplier supplier : this.iconSuppliers)
		{
			supplier.registerIcons(iconRegister);
		}
	}
	
	@Override
	public IIcon getIcon(int metadata)
	{
		return this.iconSuppliers[0].getIcon(metadata);
	}
	
	@Override
	public IIcon getIcon(int metadata, int side)
	{
		return this.iconSuppliers[side].getIcon(metadata);
	}
}

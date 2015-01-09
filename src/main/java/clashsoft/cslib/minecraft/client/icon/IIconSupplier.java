package clashsoft.cslib.minecraft.client.icon;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IIconSupplier
{
	public void registerIcons(IIconRegister iconRegister);
	
	public IIcon getIcon(int metadata);
	
	public IIcon getIcon(int metadata, int side);
}

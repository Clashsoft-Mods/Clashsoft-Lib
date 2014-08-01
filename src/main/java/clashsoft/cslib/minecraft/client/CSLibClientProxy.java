package clashsoft.cslib.minecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import clashsoft.cslib.minecraft.client.gui.GuiModUpdates;
import clashsoft.cslib.minecraft.common.CSLibProxy;

public class CSLibClientProxy extends CSLibProxy
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == 0)
		{
			return new GuiModUpdates(Minecraft.getMinecraft().currentScreen);
		}
		return null;
	}
	
	@Override
	public boolean isClient()
	{
		return true;
	}
}

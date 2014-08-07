package clashsoft.cslib.minecraft.client;

import clashsoft.cslib.minecraft.cape.CapeHelper;
import clashsoft.cslib.minecraft.cape.Capes;
import clashsoft.cslib.minecraft.client.gui.GuiModUpdates;
import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CSLibClientProxy extends CSLibProxy
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public void openMUScreen()
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GuiModUpdates(mc.currentScreen));
	}
	
	@Override
	public void openGUI(Object gui)
	{
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen) gui);
	}
	
	@Override
	public void setCape(EntityPlayer player, String capeName)
	{
		CapeHelper.updateCape(player, Capes.getCape(capeName));
	}
	
	@Override
	public boolean isClient()
	{
		return true;
	}
}

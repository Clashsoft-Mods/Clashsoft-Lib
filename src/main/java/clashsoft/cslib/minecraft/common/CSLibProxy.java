package clashsoft.cslib.minecraft.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CSLibProxy extends BaseProxy
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	public void openMUScreen()
	{
	}
	
	public void openGUI(Object gui)
	{
	}
}

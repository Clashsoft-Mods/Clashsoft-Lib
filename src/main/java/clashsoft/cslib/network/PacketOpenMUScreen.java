package clashsoft.cslib.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import clashsoft.cslib.CSLib;

public class PacketOpenMUScreen extends CSPacket
{
	@Override
	public void write(PacketBuffer buf)
	{
	}
	
	@Override
	public void read(PacketBuffer buf)
	{
	}
	
	@Override
	public void handleClient(EntityPlayer player)
	{
		CSLib.proxy.openMUScreen();
	}
	
	@Override
	public void handleServer(EntityPlayerMP player)
	{
	}
}

package clashsoft.cslib.minecraft.network;

import java.io.IOException;

import clashsoft.cslib.minecraft.init.CSLib;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

public class CapePacket extends CSPacket
{
	public String	username;
	public String	cape;
	
	public CapePacket()
	{
	}
	
	public CapePacket(EntityPlayer player, String cape)
	{
		this.username = player.getCommandSenderName();
		this.cape = cape;
	}
	
	@Override
	public void write(PacketBuffer buf)
	{
		try
		{
			buf.writeStringToBuffer(this.username);
			buf.writeStringToBuffer(this.cape);
		}
		catch (IOException ex)
		{
		}
	}
	
	@Override
	public void read(PacketBuffer buf)
	{
		try
		{
			this.username = buf.readStringFromBuffer(0xFFFF);
			this.cape = buf.readStringFromBuffer(0xFFFF);
		}
		catch (IOException ex)
		{
		}
	}
	
	@Override
	public void handleClient(EntityPlayer player)
	{
		EntityPlayer player1 = CSLib.proxy.findPlayer(Minecraft.getMinecraft().theWorld, this.username);
		CSLib.proxy.setCape(player1, this.cape);
	}
	
	@Override
	public void handleServer(EntityPlayerMP player)
	{
	}
}

package clashsoft.cslib.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CSLibNetHandler extends CSNetHandler
{
	public CSLibNetHandler()
	{
		super("CSLIB");
		
		this.registerPacket(PacketSendTileEntity.class);
		this.registerPacket(PacketRequestTileEntity.class);
		this.registerPacket(PacketOpenMUScreen.class);
		this.registerPacket(CapePacket.class);
	}
	
	public void requestTEData(World world, int x, int y, int z)
	{
		this.sendToServer(new PacketRequestTileEntity(world, x, y, z));
	}
	
	public void sendTEData(World world, int x, int y, int z, EntityPlayerMP player)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			NBTTagCompound data = new NBTTagCompound();
			te.writeToNBT(data);
			this.sendTo(new PacketSendTileEntity(world, x, y, z, data), player);
		}
	}

	public void sendOpenMUScreen(EntityPlayerMP sender)
	{
		this.sendTo(new PacketOpenMUScreen(), sender);
	}
	
	public void sendCapePacket(EntityPlayer player, String capeName)
	{
		this.sendToAll(new CapePacket(player, capeName));
	}
}

package clashsoft.cslib.minecraft.network;

import clashsoft.cslib.minecraft.CSLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class PacketRequestTileEntity extends CSPacket
{
	public World	world;
	public int		x;
	public int		y;
	public int		z;
	
	public PacketRequestTileEntity()
	{
	}
	
	public PacketRequestTileEntity(World world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void write(PacketBuffer buf)
	{
		writeWorld(buf, this.world);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
	}
	
	@Override
	public void read(PacketBuffer buf)
	{
		this.world = readWorld(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}
	
	@Override
	public void handleClient(EntityPlayer player)
	{
	}
	
	@Override
	public void handleServer(EntityPlayerMP player)
	{
		CSLib.getNetHandler().sendTEData(this.world, this.x, this.y, this.z, player);
	}
}
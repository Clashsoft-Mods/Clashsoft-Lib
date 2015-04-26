package clashsoft.cslib.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class PacketSendTileEntity extends CSPacket
{
	public World			world;
	public int				x;
	public int				y;
	public int				z;
	
	public NBTTagCompound	data;
	
	public PacketSendTileEntity()
	{
	}
	
	public PacketSendTileEntity(World world, int x, int y, int z, NBTTagCompound data)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
	}
	
	@Override
	public void write(PacketBuffer buf)
	{
		writeWorld(buf, this.world);
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		
		try
		{
			buf.writeNBTTagCompoundToBuffer(this.data);
		}
		catch (Exception ioex)
		{
		}
	}
	
	@Override
	public void read(PacketBuffer buf)
	{
		this.world = readWorld(buf);
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		
		try
		{
			this.data = buf.readNBTTagCompoundFromBuffer();
		}
		catch (Exception ioex)
		{
		}
	}
	
	@Override
	public void handleClient(EntityPlayer player)
	{
		TileEntity tileEntity = player.worldObj.getTileEntity(new BlockPos(this.x, this.y, this.z));
		if (tileEntity != null)
		{
			tileEntity.readFromNBT(this.data);
		}
	}
	
	@Override
	public void handleServer(EntityPlayerMP player)
	{
	}
}

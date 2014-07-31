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
	}
	
	public void requestTEData(World world, int x, int y, int z)
	{
		this.sendToServer(new PacketRequestTileEntity(world, x, y, z));
	}
	
	public void sendTEData(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			NBTTagCompound data = new NBTTagCompound();
			te.writeToNBT(data);
			this.sendTo(new PacketSendTileEntity(world, x, y, z, data), (EntityPlayerMP) player);
		}
	}
}

package clashsoft.cslib.minecraft.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
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
	
	public void requestTEData(World world, BlockPos pos)
	{
		this.sendToServer(new PacketRequestTileEntity(world, pos.getX(), pos.getY(), pos.getZ()));
	}
	
	public void sendTEData(World world, BlockPos pos, EntityPlayerMP player)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te != null)
		{
			NBTTagCompound data = new NBTTagCompound();
			te.writeToNBT(data);
			this.sendTo(new PacketSendTileEntity(world, pos.getX(), pos.getY(), pos.getZ(), data), player);
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

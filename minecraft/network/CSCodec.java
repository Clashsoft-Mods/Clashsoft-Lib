package clashsoft.cslib.minecraft.network;

import net.minecraft.network.PacketBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public abstract class CSCodec<T extends CSPacket> extends FMLIndexedMessageToMessageCodec<T>
{
	public CSCodec()
	{
		this.addDiscriminators();
	}
	
	public abstract void addDiscriminators();
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, T msg, ByteBuf target) throws Exception
	{
		msg.write(new PacketBuffer(target));
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, T msg)
	{
		msg.read(new PacketBuffer(data));
	}
}
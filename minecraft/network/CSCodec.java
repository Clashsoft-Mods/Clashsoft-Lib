package clashsoft.cslib.minecraft.network;

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
		msg.write(target);
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf dat, T msg)
	{
		msg.read(dat);
	}
}
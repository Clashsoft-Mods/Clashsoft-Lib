package clashsoft.cslib.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class CSMessageHandler<T extends CSPacket> extends SimpleChannelInboundHandler<T>
{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception
	{
		this.process(msg);
	}
	
	public abstract void process(T msg);
}
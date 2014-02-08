package clashsoft.cslib.minecraft.network;

import io.netty.buffer.ByteBuf;

public interface CSPacket
{
	public void write(ByteBuf buf);
	public void read(ByteBuf buf);
}

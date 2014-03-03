package clashsoft.cslib.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.*;

import clashsoft.cslib.util.CSLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;

@ChannelHandler.Sharable
public class CSNetHandler extends MessageToMessageCodec<FMLProxyPacket, CSPacket>
{
	public final String							name;
	
	protected EnumMap<Side, FMLEmbeddedChannel>	channels;
	protected List<Class<? extends CSPacket>>	packets				= new ArrayList();
	protected boolean							isPostInitialised	= false;
	
	public CSNetHandler(String name)
	{
		this.name = name;
	}
	
	/**
	 * Register your packet with the pipeline. Discriminators are automatically set.
	 * 
	 * @param clazz
	 *            the class to register
	 * @return whether registration was successful. Failure may occur if 256 packets have been
	 *         registered or if the registry already contains this packet
	 */
	public boolean registerPacket(Class<? extends CSPacket> clazz)
	{
		if (this.packets.size() > 256)
		{
			return false;
		}
		
		if (this.packets.contains(clazz))
		{
			return false;
		}
		
		if (this.isPostInitialised)
		{
			return false;
		}
		
		this.packets.add(clazz);
		return true;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, CSPacket msg, List<Object> out) throws Exception
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		Class<? extends CSPacket> clazz = msg.getClass();
		
		if (!this.packets.contains(msg.getClass()))
		{
			throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
		}
		
		byte discriminator = (byte) this.packets.indexOf(clazz);
		buffer.writeByte(discriminator);
		msg.write(buffer);
		FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		out.add(proxyPacket);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception
	{
		ByteBuf payload = msg.payload();
		byte discriminator = payload.readByte();
		Class<? extends CSPacket> clazz = this.packets.get(discriminator);
		
		if (clazz == null)
		{
			throw new NullPointerException("No packet registered for discriminator: " + discriminator);
		}
		
		PacketBuffer buffer = new PacketBuffer(payload.slice());
		
		CSPacket pkt = clazz.newInstance();
		pkt.read(buffer);
		
		EntityPlayer player;
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
				player = this.getClientPlayer();
				pkt.handleClient(player);
				break;
			
			case SERVER:
				INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
				player = ((NetHandlerPlayServer) netHandler).playerEntity;
				pkt.handleServer(player);
				break;
			
			default:
		}
		
		out.add(pkt);
	}
	
	public void init()
	{
		this.channels = NetworkRegistry.INSTANCE.newChannel(this.name, this);
	}
	
	/**
	 * Ensures that packet discriminators are common between server and client by using logical
	 * sorting
	 */
	public void postInit()
	{
		if (this.isPostInitialised)
		{
			return;
		}
		
		this.isPostInitialised = true;
		Collections.sort(this.packets, new Comparator<Class>()
		{
			@Override
			public int compare(Class clazz1, Class clazz2)
			{
				String canon1 = clazz1.getCanonicalName();
				String canon2 = clazz2.getCanonicalName();
				int com = String.CASE_INSENSITIVE_ORDER.compare(canon1, canon2);
				
				if (com == 0)
				{
					com = canon1.compareTo(canon2);
				}
				
				return com;
			}
		});
	}
	
	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
	
	/**
	 * Send this message to everyone.
	 * <p>
	 * Adapted from CPW's code in {@link SimpleNetworkWrapper}
	 * 
	 * @param message
	 *            The message to send
	 */
	public void sendToAll(CSPacket message)
	{
		FMLEmbeddedChannel channel = this.channels.get(Side.SERVER);
		if (channel != null)
		{
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channel.writeAndFlush(message);
		}
		else
		{
			CSLog.error("Unable to send packet to players: No Server Channel!");
		}
	}
	
	/**
	 * Send this message to the specified player.
	 * <p>
	 * Adapted from CPW's code in {@link SimpleNetworkWrapper}
	 * 
	 * @param message
	 *            The message to send
	 * @param player
	 *            The player to send it to
	 */
	public void sendTo(CSPacket message, EntityPlayerMP player)
	{
		FMLEmbeddedChannel channel = this.channels.get(Side.SERVER);
		if (channel != null)
		{
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
			channel.writeAndFlush(message);
		}
		else
		{
			CSLog.error("Unable to send packet to player: No Server Channel!");
		}
	}
	
	/**
	 * Send this message to everyone within a certain range of a point.
	 * <p>
	 * Adapted from CPW's code in {@link SimpleNetworkWrapper}
	 * 
	 * @param message
	 *            The message to send
	 * @param point
	 *            The {@link cpw.mods.fml.common.network.NetworkRegistry.TargetPoint} around which
	 *            to send
	 */
	public void sendToAllAround(CSPacket message, NetworkRegistry.TargetPoint point)
	{
		FMLEmbeddedChannel channel = this.channels.get(Side.SERVER);
		if (channel != null)
		{
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
			channel.writeAndFlush(message);
		}
		else
		{
			CSLog.error("Unable to send packet to players around point: No Server Channel!");
		}
	}
	
	/**
	 * Send this message to everyone within the supplied dimension.
	 * <p>
	 * Adapted from CPW's code in {@link SimpleNetworkWrapper}
	 * 
	 * @param message
	 *            The message to send
	 * @param dimensionId
	 *            The dimension id to target
	 */
	public void sendToDimension(CSPacket message, int dimensionId)
	{
		FMLEmbeddedChannel channel = this.channels.get(Side.SERVER);
		if (channel != null)
		{
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
			channel.writeAndFlush(message);
		}
		else
		{
			CSLog.error("Unable to send packet to players in dimension: No Server Channel!");
		}
	}
	
	/**
	 * Send this message to the server.
	 * <p/>
	 * Adapted from CPW's code in cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 * 
	 * @param message
	 *            The message to send
	 */
	public void sendToServer(CSPacket message)
	{
		FMLEmbeddedChannel channel = this.channels.get(Side.CLIENT);
		if (channel != null)
		{
			channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
			channel.writeAndFlush(message);
		}
		else
		{
			CSLog.error("Unable to send packet to server: No Client Channel!");
		}
	}
}
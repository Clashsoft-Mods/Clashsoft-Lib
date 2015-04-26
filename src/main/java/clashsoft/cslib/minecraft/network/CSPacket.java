package clashsoft.cslib.minecraft.network;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public abstract class CSPacket
{
	/**
	 * Writes this packet to the {@link PacketBuffer} {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 */
	public abstract void write(PacketBuffer buf) throws IOException;
	
	/**
	 * Reads this packet from the {@link PacketBuffer} {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 */
	public abstract void read(PacketBuffer buf) throws IOException;
	
	/**
	 * Handles this packet client-side. The default implementation delegates to
	 * {@link #handle(EntityPlayer)}.
	 * 
	 * @param player
	 *            the player
	 */
	public void handleClient(EntityPlayer player)
	{
		this.handle(player);
	}
	
	/**
	 * Handles this packet server-side. The default implementation delegates to
	 * {@link #handle(EntityPlayer)}.
	 * 
	 * @param player
	 *            the player
	 */
	public void handleServer(EntityPlayerMP player)
	{
		this.handle(player);
	}
	
	/**
	 * Handles this packet on both sides. {@link #handleClient(EntityPlayer)}
	 * and {@link #handleServer(EntityPlayerMP)} delegate here. Note that one
	 * either has to override both of these methods or this method, otherwise
	 * and {@link UnsupportedOperationException} will be thrown.
	 * 
	 * @param player
	 *            the player
	 */
	public void handle(EntityPlayer player)
	{
		// Not implemented
		throw new UnsupportedOperationException();
	}
	
	// Helper methods
	
	/**
	 * Writes the given {@link World} to the given {@link PacketBuffer}
	 * {@code buf}. This method only writes the dimension ID of the world to the
	 * buffer.
	 * 
	 * @param buf
	 *            the buffer
	 * @param world
	 *            the world
	 */
	public static void writeWorld(PacketBuffer buf, World world)
	{
		buf.writeInt(world.provider.getDimensionId());
	}
	
	/**
	 * Reads a world object from the given {@link PacketBuffer} {@code buf}.
	 * This method only reads the dimension ID of the world from the buffer, and
	 * then uses {@link DimensionManager#getWorld(int)} to get the actual world
	 * object.
	 * 
	 * @param buf
	 *            the buffer
	 * @return the world
	 */
	public static World readWorld(PacketBuffer buf)
	{
		int id = buf.readInt();
		return DimensionManager.getWorld(id);
	}
	
	/**
	 * Writes the given {@link ItemStack} {@code stack} to the given
	 * {@link PacketBuffer} {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @param stack
	 *            the stack
	 */
	public static void writeItemStack(PacketBuffer buf, ItemStack stack) throws IOException
	{
		buf.writeItemStackToBuffer(stack);
	}
	
	/**
	 * Reads an {@link ItemStack} from the given {@link PacketBuffer}
	 * {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @return the stack
	 */
	public static final ItemStack readItemStack(PacketBuffer buf) throws IOException
	{
		return buf.readItemStackFromBuffer();
	}
	
	/**
	 * Writes the given {@link PotionEffect} {@code effect} to the given
	 * {@link PacketBuffer} {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @param effect
	 *            the effect
	 */
	public static void writePotionEffect(PacketBuffer buf, PotionEffect effect) throws IOException
	{
		NBTTagCompound nbt1 = null;
		if (effect != null)
		{
			nbt1 = new NBTTagCompound();
			effect.writeCustomPotionEffectToNBT(nbt1);
		}
		buf.writeNBTTagCompoundToBuffer(nbt1);
	}
	
	/**
	 * Reads a {@link PotionEffect} from the given {@link PacketBuffer}
	 * {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @return the effect
	 */
	public static PotionEffect readPotionEffect(PacketBuffer buf) throws IOException
	{
		NBTTagCompound nbt1 = buf.readNBTTagCompoundFromBuffer();
		return nbt1 == null ? null : PotionEffect.readCustomPotionEffectFromNBT(nbt1);
	}
	
	/**
	 * Writes the given {@link TileEntity} {@code tileEntity} to the given
	 * {@link PacketBuffer} {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @param tileEntity
	 *            the tile entity
	 */
	public static void writeTileEntity(PacketBuffer buf, TileEntity tileEntity) throws IOException
	{
		NBTTagCompound nbt1 = null;
		if (tileEntity != null)
		{
			nbt1 = new NBTTagCompound();
			tileEntity.writeToNBT(nbt1);
		}
		buf.writeNBTTagCompoundToBuffer(nbt1);
	}
	
	/**
	 * Reads a {@link TileEntity} from the given {@link PacketBuffer}
	 * {@code buf}.
	 * 
	 * @param buf
	 *            the buffer
	 * @return the tile entity
	 */
	public static TileEntity readTileEntity(PacketBuffer buf) throws IOException
	{
		NBTTagCompound nbt1 = buf.readNBTTagCompoundFromBuffer();
		return nbt1 == null ? null : TileEntity.createAndLoadEntity(nbt1);
	}
}

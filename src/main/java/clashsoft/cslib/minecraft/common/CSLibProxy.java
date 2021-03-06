package clashsoft.cslib.minecraft.common;

import java.util.List;
import java.util.UUID;

import clashsoft.cslib.minecraft.CSLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CSLibProxy extends BaseProxy
{
	public static int	MULTI_RENDER_ID;
	public static int	BLOCK2D_RENDER_ID;
	public static int	CUSTOMBUSH_RENDER_ID;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	public void clearCape(EntityPlayer player)
	{
		this.setCape(player, "no_cape");
	}
	
	public void updateCape(EntityPlayer player)
	{
		this.setCape(player, "default_cape");
	}
	
	public void setCape(EntityPlayer player, String capeName)
	{
		CSLib.getNetHandler().sendCapePacket(player, capeName);
	}
	
	public void openMUScreen()
	{
	}
	
	public void openGUI(Object gui)
	{
	}
	
	public World getClientWorld()
	{
		return null;
	}
	
	public EntityPlayer getClientPlayer()
	{
		return null;
	}
	
	public EntityPlayer findPlayer(String username)
	{
		for (World world : DimensionManager.getWorlds())
		{
			EntityPlayer player = this.findPlayer(world, username);
			if (player != null)
			{
				return player;
			}
		}
		return null;
	}
	
	public EntityPlayer findPlayer(World world, String username)
	{
		List<EntityPlayer> players = world.playerEntities;
		EntityPlayer player = null;
		for (int i = 0; i < players.size(); i++)
		{
			player = players.get(i);
			if (player.getCommandSenderName().equals(username))
			{
				return player;
			}
		}
		return null;
	}
	
	public EntityPlayer findPlayer(UUID uuid)
	{
		for (World world : DimensionManager.getWorlds())
		{
			EntityPlayer player = this.findPlayer(world, uuid);
			if (player != null)
			{
				return player;
			}
		}
		return null;
	}
	
	public EntityPlayer findPlayer(World world, UUID uuid)
	{
		List<EntityPlayer> players = world.playerEntities;
		EntityPlayer player = null;
		for (int i = 0; i < players.size(); i++)
		{
			player = players.get(i);
			if (player.getUniqueID().equals(uuid))
			{
				return player;
			}
		}
		return null;
	}
}

package clashsoft.cslib.minecraft.cape;

import static clashsoft.cslib.minecraft.cape.Capes.capeNameToCape;
import static clashsoft.cslib.minecraft.cape.Capes.usernameToCapeName;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class CapeHelper
{
	private CapeHelper()
	{
	}
	
	public static Cape getCape(EntityPlayer player)
	{
		String group = usernameToCapeName.get(player.getCommandSenderName());
		return capeNameToCape.get(group);
	}
	
	public static void updateCape(EntityPlayer player, Cape cape)
	{
		if (player instanceof AbstractClientPlayer)
		{
			if (cape == null)
			{
				// Default if cape is null.
				cape = getCape(player);
			}
			
			if (cape == null)
			{
				// Cape is still null -> no default cape
				((AbstractClientPlayer) player).func_152121_a(MinecraftProfileTexture.Type.CAPE, null);
				
			}
			else
			{
				cape.loadTexture((AbstractClientPlayer) player);
			}
		}
	}
	
	public static void updateCape(EntityPlayer player, boolean override)
	{
		if (player instanceof AbstractClientPlayer)
		{
			AbstractClientPlayer player1 = (AbstractClientPlayer) player;
			if (override || player1.getLocationCape() == null)
			{
				Cape cape = getCape(player1);
				cape.loadTexture(player1);
			}
		}
	}
}
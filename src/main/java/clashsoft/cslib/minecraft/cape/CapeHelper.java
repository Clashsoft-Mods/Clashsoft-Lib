package clashsoft.cslib.minecraft.cape;

import static clashsoft.cslib.minecraft.cape.Capes.capeNameToCape;
import static clashsoft.cslib.minecraft.cape.Capes.usernameToCapeName;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

public class CapeHelper
{
	private CapeHelper()
	{
	}
	
	public static Cape getCape(EntityPlayer player)
	{
		String group = usernameToCapeName.get(player.getName());
		Cape cape = capeNameToCape.get(group);
		return cape == null ? (Cape) Capes.noCape : cape;
	}
	
	public static void updateCape(EntityPlayer player, Cape cape)
	{
		if (player instanceof AbstractClientPlayer)
		{
			if (cape == null)
			{
				cape = getCape(player);
			}
			
			AbstractClientPlayer player1 = (AbstractClientPlayer) player;
			cape.loadTexture(player1);
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

package clashsoft.clashsoftapi;

import clashsoft.clashsoftapi.util.CSUpdate;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = "ClashsoftAPI", name = "Clashsoft API", version = ClashsoftAPI.VERSION)
public class ClashsoftAPI
{
	public static final int		REVISION	= 7;
	public static final String	VERSION		= CSUpdate.CURRENT_VERSION + "-" + REVISION;
	
	@Instance("ClashsoftAPI")
	public static ClashsoftAPI	instance;
	
	public static boolean		updateCheck	= true;
	public static boolean		autoUpdate	= true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		autoUpdate = config.get("Updates", "Auto Updates", true, "Disables automatic updates").getBoolean(true);
		updateCheck = config.get("Updates", "Update Check", true, "Disables update checks for ALL mods").getBoolean(true);
		
		config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{	
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@ForgeSubscribe
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			CSUpdate.doClashsoftUpdateCheck((EntityPlayer) event.entity, "Clashsoft API", "csapi", ClashsoftAPI.VERSION);
		}
	}
	
	@ForgeSubscribe
	public void chatMessageSent(ServerChatEvent event)
	{
		String message = event.message;
		
		if (message.startsWith(">Update "))
		{
			String modName = message.substring(message.indexOf(' ') + 1);
			CSUpdate.update(event.player, modName);
			event.setCanceled(true);
		}
		if (message.startsWith(">Restart"))
		{
			try
			{
				Minecraft.getMinecraft().statFileWriter.readStat(StatList.leaveGameStat, 1);
				Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
				Minecraft.getMinecraft().loadWorld((WorldClient) null);
				
				Minecraft.getMinecraft().shutdown();
			}
			catch (Exception ex)
			{
			}
		}
	}
}

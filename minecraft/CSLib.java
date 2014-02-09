package clashsoft.cslib.minecraft;

import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.util.CSConfig;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = CSLib.MODID, name = CSLib.NAME, version = CSLib.VERSION)
public class CSLib
{
	public static final String	MODID		= "cslib";
	public static final String	NAME		= "Clashsoft Lib";
	public static final int		REVISION	= 0;
	public static final String	VERSION		= CSUpdate.CURRENT_VERSION + "-" + REVISION;
	
	@Instance(MODID)
	public static CSLib			instance;
	
	public static boolean		updateCheck	= true;
	public static boolean		autoUpdate	= true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CSConfig.loadConfig(event.getSuggestedConfigurationFile(), NAME);
		
		autoUpdate = CSConfig.getBool("Updates", "Auto Updates", "Disables automatic updates", true);
		updateCheck = CSConfig.getBool("Updates", "Update Check", "Disables update checks for ALL mods", true);
		
		CSConfig.saveConfig();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			CSUpdate.doClashsoftUpdateCheck((EntityPlayer) event.entity, "Clashsoft API", "csapi", CSLib.VERSION);
		}
	}
	
	@SubscribeEvent
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
			Convenience.shutdown();
		}
	}
}

package clashsoft.clashsoftapi;

import java.util.Random;

import clashsoft.clashsoftapi.util.CSRandom;
import clashsoft.clashsoftapi.util.CSUpdate;
import clashsoft.clashsoftapi.util.update.ModUpdate;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
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
	public static final int		REVISION	= 5;
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
		
		for (int i = 0; i < 10; i++)
		System.out.println(CSRandom.getNextRandomName(new Random(), 4 + (i / 2), 8 + (i / 3)));
	}
	
	@ForgeSubscribe
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			ModUpdate update = CSUpdate.checkForUpdate("Clashsoft API", "csapi", ClashsoftAPI.VERSION);
			CSUpdate.notifyUpdate((EntityPlayer) event.entity, "Clashsoft API", update);
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
				Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
				
				Minecraft.getMinecraft().shutdown();
			}
			catch (Exception ex)
			{
			}
		}
	}
}
